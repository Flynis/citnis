package ru.dyakun.citnis.model.selection;

public enum AtsType {
    City("Городская", "city_ats_v"),
    Department("Ведомственная", "department_ats_v"),
    Institution("Учрежденческая", "institution_ats_v");

    private final String label;
    private final String viewName;

    public String getLabel() {
        return label;
    }

    public static AtsType fromLabel(String label) {
        return switch (label) {
            case "Городская" -> City;
            case "Учрежденческая" -> Department;
            case "Ведомственная" -> Institution;
            default -> throw new IllegalArgumentException("Unknown ats type");
        };
    }

    public String getViewName() {
        return viewName;
    }

    AtsType(String label, String viewName) {
        this.label = label;
        this.viewName = viewName;
    }

}
