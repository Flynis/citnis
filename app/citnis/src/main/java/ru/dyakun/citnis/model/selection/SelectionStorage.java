package ru.dyakun.citnis.model.selection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dyakun.citnis.model.db.DatabaseManager;
import ru.dyakun.citnis.model.db.Scheduler;
import ru.dyakun.citnis.model.db.UpdateListener;
import ru.dyakun.citnis.model.query.Mapper;
import ru.dyakun.citnis.model.data.Ats;

import java.util.*;

public class SelectionStorage {

    private static final Logger logger = LoggerFactory.getLogger(SelectionStorage.class);
    private static final SelectionStorage instance = new SelectionStorage();
    public static final String notChosen = "Не выбрано";

    private List<String> atsNames;
    private List<String> districts;
    private List<String> streets;
    private List<String> cityAtsNames;

    private Map<String, Ats> ats = new HashMap<>();

    private final List<String> stringSort = Arrays.stream(SortType.values()).map(SortType::getStringSortLabel).toList();
    private final List<String> numberSort = Arrays.stream(SortType.values()).map(SortType::getNumberSortLabel).toList();
    private final List<String> atsType = Arrays.stream(AtsType.values()).map(AtsType::getLabel).toList();
    private final List<String> durations = Arrays.stream(Duration.values()).map(Duration::getLabel).toList();
    private final List<String> alphabet;

    private final List<UpdateListener> listeners = new ArrayList<>();

    private SelectionStorage() {
        alphabet = new ArrayList<>();
        alphabet.add(notChosen);
        for(char ch = 'А'; ch <= 'Я'; ch++) {
            alphabet.add(Character.toString(ch));
        }
    }

    public void addUpdateListener(UpdateListener listener) {
        listeners.add(listener);
    }

    public void notifyListeners() {
        for(var listener: listeners) {
            listener.onUpdate();
        }
    }

    public static SelectionStorage getInstance() {
        return instance;
    }

    public void init() {
        update();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                update();
            }
        };
        Scheduler.getInstance().schedule(task, 5 * 60 * 1000); // period 5 min
    }

    private void update() {
        logger.info("Updating selection data");
        DatabaseManager db = DatabaseManager.getInstance();

        String query = """
                SELECT serial_no, org_name FROM ats_owners
                \tORDER BY org_name;
                
                SELECT serial_no, org_name FROM ats_owners
                \tJOIN city_ats USING(ats_id)
                \tORDER BY org_name;
                
                SELECT district_name FROM current_city
                \tGROUP BY district_name;
                
                SELECT street_name FROM current_city
                \tGROUP BY street_name;
                """;

        Mapper<Ats> atsMapper = rs -> new Ats(
                rs.getString("serial_no"),
                rs.getString("org_name")
        );
        Mapper<Ats> cityAtsMapper = rs -> new Ats(
                rs.getString("serial_no"),
                rs.getString("org_name")
        );
        Mapper<String> districtNameMapper = rs -> rs.getString("district_name");
        Mapper<String> streetNameMapper = rs -> rs.getString("street_name");

        var res = db.executeMultipleQueries(query, List.of(atsMapper, cityAtsMapper, districtNameMapper, streetNameMapper));

        ats = new HashMap<>();
        atsNames = new ArrayList<>();
        atsNames.add(notChosen);
        for(var a: res.get(atsMapper)) {
            var atsName = a.toString();
            atsNames.add(atsName);
            ats.put(atsName, (Ats) a);
        }

        cityAtsNames = new ArrayList<>();
        cityAtsNames.add(notChosen);
        for(var a: res.get(cityAtsMapper)) {
            cityAtsNames.add(a.toString());
        }

        districts = new ArrayList<>();
        districts.add(notChosen);
        districts.addAll((Collection<? extends String>) res.get(districtNameMapper));

        streets = new ArrayList<>();
        streets.add(notChosen);
        streets.addAll((Collection<? extends String>) res.get(streetNameMapper));
        notifyListeners();
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

    public List<String> durations() {
        return durations;
    }

    public List<String> alphabet() {
        return alphabet;
    }

}
