package ru.dyakun.citnis.model.selection;

public enum Duration {
    ANY("любая"),
    WEEK("неделя"),
    MONTH("месяц");

    private final String label;

    public String getLabel() {
        return label;
    }

    public static Duration fromLabel(String label) {
        return switch (label) {
            case "неделя" -> WEEK;
            case "месяц" -> MONTH;
            case "любая" -> ANY;
            default -> throw new IllegalArgumentException("Unknown duration label");
        };
    }

    Duration(String label) {
        this.label = label;
    }

}
