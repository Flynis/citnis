package ru.dyakun.citnis.model.selection;

public enum PhoneType {
    Common("Обычный"),
    Paired("Спаренный"),
    Parallel("Параллельный");

    private final String label;

    public String getLabel() {
        return label;
    }

    public static PhoneType fromLabel(String label) {
        return switch (label) {
            case "Обычный" -> Common;
            case "Спаренный" -> Paired;
            case "Параллельный" -> Parallel;
            default -> throw new IllegalArgumentException("Unknown phone type");
        };
    }

    PhoneType(String label) {
        this.label = label;
    }

}
