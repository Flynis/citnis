package ru.dyakun.citnis.model;

import ru.dyakun.citnis.model.data.Ats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectionStorage {

    private static final SelectionStorage instance = new SelectionStorage();
    public static final String notChosen = "Не выбрано";

    private List<String> atsNames ;
    private Map<String, Ats> ats = new HashMap<>();
    private final List<String> stringSort = List.of("от А до Я", "от Я до А");
    private final List<String> numberSort = List.of("по возрастанию", "по убыванию");
    private final List<String> alphabet;

    private SelectionStorage() {
        alphabet = new ArrayList<>();
        alphabet.add(notChosen);
        for(char ch = 'А'; ch <= 'Я'; ch++) {
            alphabet.add(Character.toString(ch));
        }
    }

    public static SelectionStorage getInstance() {
        return instance;
    }

    public void init() {
        DatabaseManager db = DatabaseManager.getInstance();
        String atsQuery = "SELECT serial_no, org_name FROM ats_with_org\n"
                + "ORDER BY org_name;";
        Mapper<Ats> atsMapper = rs -> new Ats(
                rs.getString("serial_no"),
                rs.getString("org_name")
        );
        var atsWithOrgList = db.executeQuery(atsQuery, atsMapper);
        atsNames = new ArrayList<>();
        atsNames.add(notChosen);
        for(var a: atsWithOrgList) {
            var atsName = a.toString();
            atsNames.add(atsName);
            ats.put(atsName, a);
        }
    }

    public Ats getAtsByName(String name) {
        return ats.get(name);
    }

    public String sqlSortTypeFromStringSortType(String type) {
        return switch (type) {
            case "от А до Я" -> "ASC";
            case "от Я до А" -> "DESC";
            default -> throw new IllegalArgumentException("Unknown string sort type");
        };
    }

    public List<String> ats() {
        return atsNames;
    }

    public List<String> stringSort() {
        return stringSort;
    }

    public List<String> numberSort() {
        return numberSort;
    }

    public List<String> alphabet() {
        return alphabet;
    }

}
