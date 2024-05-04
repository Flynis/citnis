package ru.dyakun.citnis.model.selection;

public enum Gender {
    Male("мужской", "m"),
    Female("женский", "f");

    private final String label;
    private final String firstLetter;

    public String getLabel() {
        return label;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public static Gender fromLabel(String label) {
        return switch (label) {
            case "мужской" -> Male;
            case "женский" -> Female;
            default -> throw new IllegalArgumentException("Unknown gender");
        };
    }

    public static Gender fromFirstLetter(String label) {
        return switch (label) {
            case "m" -> Male;
            case "f" -> Female;
            default -> throw new IllegalArgumentException("Unknown gender");
        };
    }

    Gender(String label, String firstLetter) {
        this.label = label;
        this.firstLetter = firstLetter;
    }

}
