package ru.dyakun.citnis.model.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PhoneOwner {

    private final StringProperty lastname;
    private final StringProperty firstname;
    private final IntegerProperty number;
    private final StringProperty street;
    private final IntegerProperty house;

    public PhoneOwner(String lastname, String firstname, Integer number, String street, Integer house) {
        this.lastname = new SimpleStringProperty(lastname);
        this.firstname = new SimpleStringProperty(firstname);
        this.number = new SimpleIntegerProperty(number);
        this.street = new SimpleStringProperty(street);
        this.house = new SimpleIntegerProperty(house);
    }

    public String getLastname() {
        return lastname.get();
    }

    public StringProperty lastnameProperty() {
        return lastname;
    }

    public String getFirstname() {
        return firstname.get();
    }

    public StringProperty firstnameProperty() {
        return firstname;
    }

    public int getNumber() {
        return number.get();
    }

    public IntegerProperty numberProperty() {
        return number;
    }

    public int getHouse() {
        return house.get();
    }

    public IntegerProperty houseProperty() {
        return house;
    }

    public String getStreet() {
        return street.get();
    }

    public StringProperty streetProperty() {
        return street;
    }

}
