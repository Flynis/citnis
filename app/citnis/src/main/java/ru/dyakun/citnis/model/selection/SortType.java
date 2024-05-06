package ru.dyakun.citnis.model.selection;

public enum SortType {
    ASC("от А до Я", "по возрастанию"),
    DESC("от Я до А", "по убыванию");

    private final String stringSortLabel;
    private final String numberSortLabel;

    public String getStringSortLabel() {
        return stringSortLabel;
    }

    public String getSqlSortType() {
        return name();
    }

    public String getNumberSortLabel() {
        return numberSortLabel;
    }

    public static SortType fromStringSortType(String type) {
        return switch (type) {
            case "от А до Я" -> ASC;
            case "от Я до А" -> DESC;
            default -> throw new IllegalArgumentException("Unknown string sort type");
        };
    }

    public static SortType fromNumberSortType(String type) {
        return switch (type) {
            case "по возрастанию" -> ASC;
            case "по убыванию" -> DESC;
            default -> throw new IllegalArgumentException("Unknown number sort type");
        };
    }

    SortType(String stringSortLabel, String numberSortLabel) {
        this.stringSortLabel = stringSortLabel;
        this.numberSortLabel = numberSortLabel;
    }

}
