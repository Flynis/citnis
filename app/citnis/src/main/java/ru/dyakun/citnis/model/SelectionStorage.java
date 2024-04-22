package ru.dyakun.citnis.model;

import ru.dyakun.citnis.model.data.Ats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectionStorage {

    private static final SelectionStorage instance = new SelectionStorage();
    public static final String notChosen = "Не выбрано";

    private List<String> atsNames;
    private List<String> districts;
    private List<String> streets;
    private List<String> cityAtsNames;

    private Map<String, Ats> ats = new HashMap<>();

    private final List<String> stringSort = List.of("от А до Я", "от Я до А");
    private final List<String> numberSort = List.of("по возрастанию", "по убыванию");
    private final List<String> atsType = List.of(notChosen, "Городская", "Учрежденческая", "Ведомственная");
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

        String atsQuery = """
                SELECT serial_no, org_name FROM ats_with_org
                \tORDER BY org_name;
                """;
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

        String cityAtsQuery = """
                SELECT serial_no, org_name FROM ats_with_org
                \tJOIN city_ats USING(ats_id)
                \tORDER BY org_name;
                """;
        var cityAtsList = db.executeQuery(cityAtsQuery, atsMapper);
        cityAtsNames = new ArrayList<>();
        cityAtsNames.add(notChosen);
        for(var a: cityAtsList) {
            cityAtsNames.add(a.toString());
        }

        String districtsQuery = """
                SELECT district_name FROM current_city
                \tGROUP BY district_name;
                """;
        Mapper<String> districtNameMapper = rs -> rs.getString("district_name");
        districts = new ArrayList<>();
        districts.add(notChosen);
        districts.addAll(db.executeQuery(districtsQuery, districtNameMapper));

        String streetsQuery = """
                SELECT street_name FROM current_city
                \tGROUP BY street_name;
                """;
        Mapper<String> streetNameMapper = rs -> rs.getString("street_name");
        streets = new ArrayList<>();
        streets.add(notChosen);
        streets.addAll(db.executeQuery(streetsQuery, streetNameMapper));
    }

    public Ats getAtsByName(String name) {
        return ats.get(name);
    }

    public List<String> ats() {
        return atsNames;
    }

    public List<String> districts() {
        return districts;
    }

    public List<String> streets() {
        return streets;
    }

    public List<String> cityAts() {
        return cityAtsNames;
    }

    public List<String> stringSort() {
        return stringSort;
    }

    public List<String> numberSort() {
        return numberSort;
    }

    public List<String> atsType() {
        return atsType;
    }

    public List<String> alphabet() {
        return alphabet;
    }

}
