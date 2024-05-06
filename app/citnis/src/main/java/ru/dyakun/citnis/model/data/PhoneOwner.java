package ru.dyakun.citnis.model.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PhoneOwner extends Subscriber {

    protected final IntegerProperty number = new SimpleIntegerProperty();

    public void setNumber(int number) {
        this.number.set(number);
    }

    public int getNumber() {
        return number.get();
    }

    public IntegerProperty numberProperty() {
        return number;
    }

}
