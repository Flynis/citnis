package ru.dyakun.citnis.model.data;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;

public class SubscribersStat {

    private final StringProperty serial = new SimpleStringProperty();
    private final StringProperty district = new SimpleStringProperty();
    private final DoubleProperty percent = new SimpleDoubleProperty();
    private final IntegerProperty beneficiariesCount = new SimpleIntegerProperty();
    private final IntegerProperty total = new SimpleIntegerProperty();

    public SubscribersStat() {
        percent.bind(Bindings.multiply(100, Bindings.divide(total, beneficiariesCount)));
    }

    public void setSerial(String serial) {
        this.serial.set(serial);
    }

    public void setDistrict(String district) {
        this.district.set(district);
    }

    public void setBeneficiariesCount(int beneficiariesCount) {
        this.beneficiariesCount.set(beneficiariesCount);
    }

    public void setTotal(int total) {
        this.total.set(total);
    }

    public String getSerial() {
        return serial.get();
    }

    public StringProperty serialProperty() {
        return serial;
    }

    public String getDistrict() {
        return district.get();
    }

    public StringProperty districtProperty() {
        return district;
    }

    public double getPercent() {
        return percent.get();
    }

    public DoubleProperty percentProperty() {
        return percent;
    }

    public int getBeneficiariesCount() {
        return beneficiariesCount.get();
    }

    public IntegerProperty beneficiariesCountProperty() {
        return beneficiariesCount;
    }

    public int getTotal() {
        return total.get();
    }

    public IntegerProperty totalProperty() {
        return total;
    }

}
