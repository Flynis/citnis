package ru.dyakun.citnis.model;

import java.util.ArrayList;
import java.util.List;

public class SelectionStorage {

    private static final SelectionStorage instance = new SelectionStorage();
    public static final String notChosen = "Не выбрано";

    private List<String> ats;
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

    private record AtsWithOrg(String serial, String org) {}

    public void init() {
        DatabaseManager db = DatabaseManager.getInstance();
        String atsQuery = "SELECT serial_no, org_name FROM ats_with_org\n"
                + "ORDER BY org_name;";
        Mapper<AtsWithOrg> atsWithOrgMapper = rs -> new AtsWithOrg(
                rs.getString("serial_no"),
                rs.getString("org_name"));
        var atsWithOrgList = db.executeQuery(atsQuery, atsWithOrgMapper);
        ats = new ArrayList<>();
        ats.add(notChosen);
        for(var a: atsWithOrgList) {
            ats.add(String.format("%s (%s)", a.org, a.serial));
        }
    }

    public List<String> ats() {
        return ats;
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
