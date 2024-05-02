package ru.dyakun.citnis.model.data;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;

public class Subscriber {

    protected final StringProperty lastname = new SimpleStringProperty();
    protected final StringProperty firstname = new SimpleStringProperty();
    protected final StringProperty surname = new SimpleStringProperty();
    protected final IntegerProperty age = new SimpleIntegerProperty();
    protected final StringProperty gender = new SimpleStringProperty();
    protected final DoubleProperty benefit = new SimpleDoubleProperty();
    protected final BooleanProperty isBeneficiary = new SimpleBooleanProperty(false);

    public Subscriber() {
        isBeneficiary.bind(Bindings.greaterThan(0.5, benefit));
    }

    public void setLastname(String lastname) {
        this.lastname.set(lastname);
    }

    public void setFirstname(String firstname) {
        this.firstname.set(firstname);
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public void setBenefit(double benefit) {
        this.benefit.set(benefit);
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

    public double getBenefit() {
        return benefit.get();
    }

    public DoubleProperty benefitProperty() {
        return benefit;
    }

    public boolean isIsBeneficiary() {
        return isBeneficiary.get();
    }

    public BooleanProperty isBeneficiaryProperty() {
        return isBeneficiary;
    }

}
