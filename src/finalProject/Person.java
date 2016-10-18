package finalProject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
	String fullPersonalNumber;
	private int ageZZZ;
	private int yearBorn, monthBorn, dayBorn;
	private int yearToday = 2015, monthToday = 10, dayToday = 13;
	private boolean isOfLegalAge;

	//First Name
	private StringProperty firstName;

	public void setFirstName(String value) {
		firstNameProperty().set(value);
	}

	public String getFirstName() {
		return firstNameProperty().get();
	}

	public StringProperty firstNameProperty() {
		if (firstName == null)
			firstName = new SimpleStringProperty(this, "firstName");
		return firstName;
	}

	// Last Name
	private StringProperty lastName;

	public void setLastName(String value) {
		lastNameProperty().set(value);
	}

	public String getLastName() {
		return lastNameProperty().get();
	}

	public StringProperty lastNameProperty() {
		if (lastName == null)
			lastName = new SimpleStringProperty(this, "lastName");
		return lastName;
	}

	// Personal Number
	private StringProperty personalNumber;

	public void setPersonalNumber(String value) {
		personalNumberProperty().set(value);
	}

	public String getPersonalNumber() {
		return personalNumberProperty().get();
	}

	public StringProperty personalNumberProperty() {
		if (personalNumber == null)
			personalNumber = new SimpleStringProperty(this, "personalNumber");
		return personalNumber;
	}

	// Age
	private StringProperty age;

	public void setAge(String value) {
		ageProperty().set(value);
	}

	public String getAge() {
		return ageProperty().get();
	}

	public StringProperty ageProperty() {
		if (age == null)
			age = new SimpleStringProperty(this, "age");
		return age;
	}

	//Gender
	private StringProperty gender;
	
	public void setGender(String value) {
		genderProperty().set(value);
	}
	
	public String getGender() {
		return genderProperty().get();
	}
	
	public StringProperty genderProperty() {
		if (gender == null)
			gender = new SimpleStringProperty(this, "gender");
		return gender;
	}
	
	
	
	Person(String[] s) {
		firstNameProperty().set(s[0]);
		lastNameProperty().set(s[1]);
		fullPersonalNumber = s[2];
		personalNumberProperty().set(s[2].substring(2));
		
		String[] s2 = s[2].split("-");
		if (s2[1].charAt(2) % 2 == 0)
			genderProperty().set("female");
		else
			genderProperty().set("male");


		yearBorn = Integer.parseInt(s2[0].substring(0, 4));
		monthBorn = Integer.parseInt(s2[0].substring(4, 6));
		dayBorn = Integer.parseInt(s2[0].substring(6, 8));

		if (yearBorn < (yearToday - 18)) {
			isOfLegalAge = true;
		} else if (yearBorn == (yearToday - 18)) {
			if (monthBorn < monthToday)
				isOfLegalAge = true;
			else if (monthBorn == monthToday) {
				if (dayBorn < dayToday)
					isOfLegalAge = true;
				else if (dayBorn == dayToday)
					isOfLegalAge = true;
				else if (dayBorn > dayToday)
					isOfLegalAge = false;
			} else if (monthBorn > monthToday)
				isOfLegalAge = false;
		} else
			isOfLegalAge = false;
		ageZZZ = yearToday - yearBorn;// FIX TO ACTUALLY INCLUDE MONTH AND DAY
		String ageString = "" + ageZZZ;
		ageProperty().set(ageString);
	}

	public boolean ofLegalAge() {
		return isOfLegalAge;
	}
	
	public String getFullPersonalNumber() {
		return fullPersonalNumber;
	}

}
