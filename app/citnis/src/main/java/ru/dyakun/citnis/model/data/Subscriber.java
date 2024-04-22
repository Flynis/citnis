package ru.dyakun.citnis.model.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Subscriber {

    private final StringProperty firstname;
    private final StringProperty lastname;
    private final StringProperty surname;
    private final IntegerProperty age;
    private final StringProperty gender;

    public Subscriber(String lastname, String firstname, String surname, int age, String gender) {
        this.firstname = new SimpleStringProperty(firstname);
        this.lastname = new SimpleStringProperty(lastname);
        this.surname = new SimpleStringProperty(surname);
        this.age = new SimpleIntegerProperty(age);
        this.gender = new SimpleStringProperty(gender);
    }

    public String getFirstname() {
        return firstname.get();
    }

    public StringProperty firstnameProperty() {
        return firstname;
    }

    public String getLastname() {
        return lastname.get();
    }

    public StringProperty lastnameProperty() {
        return lastname;
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public int getAge() {
        return age.get();
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public String getGender() {
        return gender.get();
    }

    public StringProperty genderProperty() {
        return gender;
    }

}
