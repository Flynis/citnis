package ru.dyakun.citnis.model.data;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Debtor extends Subscriber {

    protected final DoubleProperty debt = new SimpleDoubleProperty();

    public void setDebt(double debt) {
        this.debt.set(debt);
    }

    public double getDebt() {
        return debt.get();
    }

    public DoubleProperty debtProperty() {
        return debt;
    }

}
