package finalProject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Popups {
	static boolean answer;
	static String name, surname;
	
	public static String[] editPerson(Person p) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Edit person");
		window.setMinWidth(250);
		Label label = new Label();
		label.setText("Edit name or surname");


		Label editNameLabel = new Label("Name:");
		TextField editName = new TextField(p.getFirstName());
		Label editSurnameLabel = new Label("Surname");
		TextField editSurname = new TextField(p.getLastName());
		
		Button doneButton = new Button("Done");
		doneButton.setOnAction(e -> {
			name = editName.getText();
			surname = editSurname.getText();
			window.close();
		});
		
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(0, 10, 10, 10));
		layout.setSpacing(10);
		layout.getChildren().addAll(label, editNameLabel, editName, editSurnameLabel, editSurname, doneButton);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
		
		String returnArray[] = {name, surname};
		return returnArray;
	}
	
	
	public static Person addPerson() {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Add a person");
		window.setMinWidth(250);
		Label label = new Label();
		label.setText("All boxes must be filled out correctly");

		
		Label editNameLabel = new Label("Name:");
		TextField editName = new TextField();
		Label editSurnameLabel = new Label("Surname");
		TextField editSurname = new TextField();
		Label editPersonalNumberLabel = new Label("Personal number (YYYYMMDD-XXXX)");
		TextField editPersonalNumber = new TextField();
		String[] constructArr = new String[3];
		Button addButton = new Button("add");
		addButton.setOnAction(e -> {
			constructArr[0] = editName.getText();
			constructArr[1] = editSurname.getText();
			constructArr[2] = editPersonalNumber.getText();
			window.close();
		});
		
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(0, 10, 10, 10));
		layout.setSpacing(10);
		//Add hbox for cancel button
		layout.getChildren().addAll(label, editNameLabel, editName, editSurnameLabel, editSurname, editPersonalNumberLabel, editPersonalNumber, addButton);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
		Person newPerson = new Person(constructArr);
		return newPerson;
	}
	
	
	public static boolean closePopup() {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Close");
		window.setMinWidth(250);
		Label label = new Label();
		label.setText("Do you wish to save changes before closing?");
		
		Button saveButton = new Button("Save");
		Button closeButton = new Button("Close");
		
		saveButton.setOnAction(e -> {
			answer = true;
			window.close();
		});
		
		closeButton.setOnAction(e -> {
			answer = false;
			window.close();
		});
		
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(0, 10, 10, 10));
		layout.setSpacing(10);
		HBox hbox = new HBox(10);
		hbox.setPadding(new Insets(0, 10, 10, 10));
		hbox.setSpacing(10);
		hbox.setAlignment(Pos.CENTER);
		
		hbox.getChildren().addAll(saveButton, closeButton);
		layout.getChildren().addAll(label, hbox);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
			
		return answer;
	}
	
}
