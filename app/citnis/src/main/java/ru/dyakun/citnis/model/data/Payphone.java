package ru.dyakun.citnis.model.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Payphone {

    private final IntegerProperty number;
    private final StringProperty street;
    private final IntegerProperty house;

    public Payphone(int number, String street, int house) {
        this.number = new SimpleIntegerProperty(number);
        this.street = new SimpleStringProperty(street);
        this.house = new SimpleIntegerProperty(house);
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
