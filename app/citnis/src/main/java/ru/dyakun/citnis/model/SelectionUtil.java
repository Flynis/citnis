package ru.dyakun.citnis.model;

public class SelectionUtil {

    private SelectionUtil() {
        throw new AssertionError();
    }

    public static String convertNumberSortType(String type) {
        return switch (type) {
            case "по возрастанию" -> "ASC";
            case "по убыванию" -> "DESC";
            default -> throw new IllegalArgumentException("Unknown number sort type");
        };
    }

    public static String convertStringSortType(String type) {
        return switch (type) {
            case "от А до Я" -> "ASC";
            case "от Я до А" -> "DESC";
            default -> throw new IllegalArgumentException("Unknown string sort type");
        };
    }

    public static String getViewNameByAtsType(String type) {
        return switch (type) {
            case "Городская" -> "city_ats_v";
            case "Учрежденческая" -> "institution_ats_v";
            case "Ведомственная" -> "department_ats_v";
            default -> throw new IllegalArgumentException("Unknown ats type");
        };
    }

}
