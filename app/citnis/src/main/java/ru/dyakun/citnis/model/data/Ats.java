package ru.dyakun.citnis.model.data;

public class Ats {

    private final String serial;
    private final String organization;

    public Ats(String serial, String organization) {
        this.serial = serial;
        this.organization = organization;
    }

    public String getSerial() {
        return serial;
    }

    public String getOrganization() {
        return organization;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", organization, serial);
    }

}
