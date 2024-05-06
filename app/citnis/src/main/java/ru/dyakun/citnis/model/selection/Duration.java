package ru.dyakun.citnis.model.selection;

import java.time.LocalDate;
import java.time.Month;

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

    public static int asInt(Duration duration) {
        return switch (duration) {
            case ANY -> -1;
            case WEEK -> 7;
            case MONTH -> {
                LocalDate date = LocalDate.now().minusMonths(2);
                Month month = date.getMonth();
                yield  month.length(date.isLeapYear());
            }
        };
    }

    Duration(String label) {
        this.label = label;
    }

}
