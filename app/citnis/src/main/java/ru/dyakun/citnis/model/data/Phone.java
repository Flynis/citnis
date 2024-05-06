package ru.dyakun.citnis.model.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Phone {

    protected final IntegerProperty number = new SimpleIntegerProperty();
    protected final StringProperty street = new SimpleStringProperty();
    protected final IntegerProperty house = new SimpleIntegerProperty();

    public void setNumber(int number) {
        this.number.set(number);
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public void setHouse(int house) {
        this.house.set(house);
    }

    public int getNumber() {
        return number.get();
    }

    public IntegerProperty numberProperty() {
        return number;
    }

    public String getStreet() {
        return street.get();
    }

    public StringProperty streetProperty() {
        return street;
    }

    public int getHouse() {
        return house.get();
    }

    public IntegerProperty houseProperty() {
        return house;
    }

}
