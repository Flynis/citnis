package ru.dyakun.citnis.model.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ratio {

    private final StringProperty serial;
    private final StringProperty district;
    private final IntegerProperty beneficiariesCount;
    private final IntegerProperty total;

    public Ratio(String serial, String district, int beneficiariesCount, int total) {
        this.serial = new SimpleStringProperty(serial);
        this.district = new SimpleStringProperty(district);
        this.beneficiariesCount = new SimpleIntegerProperty(beneficiariesCount);
        this.total = new SimpleIntegerProperty(total);
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
