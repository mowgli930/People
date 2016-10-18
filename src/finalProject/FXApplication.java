package finalProject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FXApplication extends Application {
	static boolean ifShow = true;
	public Stage window;
	public ArrayList<Person> peopleList = new ArrayList<Person>();
	public TableView<Person>  table;
	public ObservableList<Person> teamMembers;

	Scene myScene;
	BorderPane border;
	GridPane grid;
	public boolean filterToggle = true;


	public static void main(String[] args) {
		launch(args);
	}

	public void init() {
		System.out.println("Inside the init() method");

		try (BufferedReader file = new BufferedReader(new FileReader("people.csv"))) {
			String line;

			while ((line = file.readLine()) != null) {
				String[] splitArray = line.split(",");
				peopleList.add(new Person(splitArray));
			}
		} catch (FileNotFoundException e) {
			System.out.println("The file was not found");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void start(Stage myStage) {
		window = myStage;
		window.setTitle("People");
		window.setOnCloseRequest(e -> {
			e.consume();
			boolean answer = Popups.closePopup();
			if(answer){
				saveToFile();				
				window.close();
			}
			else
				window.close();
		});
		
		border = new BorderPane();
		border.setPrefSize(550, 350);

		{
			HBox hbox = new HBox();
			hbox.setPadding(new Insets(15, 12, 15, 12));
			hbox.setSpacing(10);
			hbox.setStyle("-fx-background-color: blue;");
			border.setTop(hbox);//setting top
		}

		makeWindow();

		
		// FILTERING BUTTONS
		Button genderFemale = new Button("Just female");
		genderFemale.setOnAction(e -> {
			ArrayList<Person> justFemale = new ArrayList<Person>();//Rewrite list, in case peopleList has been changed
			for(Person pers: peopleList) {
				if(pers.getGender().equals("female")) {
					justFemale.add(pers);
				}
			}

			if(filterToggle) {				
				teamMembers = FXCollections.observableList(justFemale);
			}
			else
				teamMembers = FXCollections.observableList(peopleList);
				
			filterToggle ^= true;
			table.setItems(teamMembers);
		});
		Button genderMale = new Button("Just male");
		genderMale.setOnAction(e -> {
			ArrayList<Person> justMale = new ArrayList<Person>();//Rewrite list, in case peopleList has been changed
			for(Person pers: peopleList) {
				if(pers.getGender().equals("male")) {
					justMale.add(pers);
				}
			}
			
			if(filterToggle) {				
				teamMembers = FXCollections.observableList(justMale);
			}
			else
				teamMembers = FXCollections.observableList(peopleList);
			
			filterToggle ^= true;
			table.setItems(teamMembers);
		});

		
		Button filterLegal = new Button("Of legal age");
		filterLegal.setOnAction(e -> {
			ArrayList<Person> justLegalAge = new ArrayList<Person>();
			for(Person pers: peopleList) {
				if(pers.ofLegalAge()) {
					justLegalAge.add(pers);
				}
			}
			if(filterToggle)
				teamMembers = FXCollections.observableList(justLegalAge);
			else
				teamMembers = FXCollections.observableList(peopleList);
			
			filterToggle ^= true;
			table.setItems(teamMembers);
		});

		
		// Editing, Adding and Removing Person
		Button editName = new Button("Edit name");
		editName.setOnAction(e -> {
			try {
				Person person = table.getSelectionModel().getSelectedItem();
				String[] returnArray = new String[3];
				returnArray = Popups.editPerson(person);
				person.setFirstName(returnArray[0]);
				person.setLastName(returnArray[1]);
			} catch(Throwable exc){};
		});

		Button addPerson = new Button("add...");
		addPerson.setOnAction(e -> {
			try{
				peopleList.add(Popups.addPerson());
				teamMembers = FXCollections.observableList(peopleList);
				table.setItems(teamMembers);
			} catch(Throwable exc) {
				System.out.println("Invalid input");
			}
		});
		Button rmPerson = new Button("remove...");
		rmPerson.setOnAction(e -> {
			Person person = table.getSelectionModel().getSelectedItem();
			peopleList.remove(person);
			teamMembers = FXCollections.observableList(peopleList);
			table.setItems(teamMembers);
		});
		
		Button saveButton = new Button("Save to file");
		saveButton.setOnAction(e -> {
			saveToFile();
		});
		
		Button closeButton = new Button("Close");
		closeButton.setOnAction(e -> {
			boolean answer = Popups.closePopup();
			if(answer){
				saveToFile();				
				window.close();
			}
			else
				window.close();
		});

		HBox buttonsBox = new HBox();
		buttonsBox.setPadding(new Insets(0, 10, 10, 10));
		buttonsBox.setSpacing(10);
		buttonsBox.getChildren().addAll(editName, addPerson, rmPerson, saveButton, closeButton);

		border.setBottom(buttonsBox);

		Label aboveRight = new Label("Filtering\noptions");
		aboveRight.setPrefSize(100, 40);
		aboveRight.setStyle("-fx-font-weight: bold;");

		VBox vbox = new VBox();
		vbox.setPadding(new Insets(0, 10, 10, 10));
		vbox.setSpacing(10);
		vbox.getChildren().addAll(aboveRight, genderFemale, genderMale, filterLegal);

		border.setRight(vbox);

		// Set scene and node
		myScene = new Scene(border);
		window.setScene(myScene);
		window.show();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void makeWindow() {
		table = new TableView<Person>();
		
		//Setting up TableView
		TableColumn<Person,String> firstNameCol = new TableColumn<Person,String>("First Name");
		firstNameCol.setCellValueFactory(new PropertyValueFactory("firstName"));
		TableColumn<Person,String> lastNameCol = new TableColumn<Person,String>("Last Name");
		lastNameCol.setCellValueFactory(new PropertyValueFactory("lastName"));
		TableColumn<Person,String> personalNumberCol = new TableColumn<Person,String>("Personal Number");
		personalNumberCol.setCellValueFactory(new PropertyValueFactory("personalNumber"));
		TableColumn<Person,String> ageCol = new TableColumn<Person,String>("Age");
		ageCol.setCellValueFactory(new PropertyValueFactory("age"));
		TableColumn<Person,String> genderCol = new TableColumn<Person,String>("Gender");
		genderCol.setCellValueFactory(new PropertyValueFactory("gender"));
		
		ObservableList<Person> teamMembers = FXCollections.observableList(peopleList);
		table.setItems(teamMembers);
		
		table.getColumns().setAll(firstNameCol, lastNameCol, personalNumberCol, ageCol, genderCol);
		border.setCenter(table);

		return;
	}
	
	public void saveToFile() {
		try(BufferedWriter fw = new BufferedWriter(new FileWriter("people.csv"))) {
			for(Person element: peopleList) {
				fw.write(element.getFirstName() + "," + element.getLastName() + "," + element.getFullPersonalNumber() + "\n");
			}
		}catch(IOException exc){
			exc.printStackTrace();
		}
		
		return;
	}
	
}
