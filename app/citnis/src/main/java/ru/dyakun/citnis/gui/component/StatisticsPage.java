package ru.dyakun.citnis.gui.component;

import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dyakun.citnis.model.data.Ats;
import ru.dyakun.citnis.model.data.Statistics;
import ru.dyakun.citnis.model.db.DatabaseManager;
import ru.dyakun.citnis.model.query.Mapper;
import ru.dyakun.citnis.model.selection.SelectionStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class StatisticsPage extends Page {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsPage.class);

    private final VBox pane;
    private final StringProperty maxDebtAts = new SimpleStringProperty();
    private final StringProperty maxDebt = new SimpleStringProperty();
    private final StringProperty maxDebtorAts = new SimpleStringProperty();
    private final StringProperty maxDebtor = new SimpleStringProperty();
    private final StringProperty minDebtorAts = new SimpleStringProperty();
    private final StringProperty minDebtor = new SimpleStringProperty();
    private final StringProperty intercityLeader = new SimpleStringProperty();

    public StatisticsPage(String title) {
        super(title);
        pane = new VBox();
        pane.setPrefWidth(Double.POSITIVE_INFINITY);
        pane.setPadding(new Insets(5, 5, 5, 5));
        pane.getStyleClass().add("content-area");
        pane.setSpacing(10);

        var box = new VBox();
        box.setSpacing(10);
        box.setPadding(new Insets(10, 10, 10, 10));
        box.getStyleClass().add("content");

        add(createLabel("Атс с наибольшим числом должников", maxDebtorAts));
        add(createLabel("Наибольшее число должников", maxDebtor));
        add(createLabel("Атс с наименьшим числом должников", minDebtorAts));
        add(createLabel("Наименьшее число должников", minDebtor));
        add(createLabel("Атс с наибольшей суммой задолжности", maxDebtAts));
        add(createLabel("Наибольшая сумма задолжности", maxDebt));
        add(createLabel("Город, с которым происходит большее количество междугородных переговоров", intercityLeader));
    }

    private void update() {
        logger.info("Updating selection data");
        DatabaseManager db = DatabaseManager.getInstance();

        String query = """
                SELECT serial_no, MAX(total_debt) AS max_total_debt FROM ats_debt_stat
                \tGROUP BY serial_no;
                
                SELECT serial_no, MAX(debtors_count) AS max_debtors_count FROM ats_debt_stat
                \tGROUP BY serial_no;
                
                SELECT serial_no, MIN(total_debt) AS min_total_debt FROM ats_debt_stat
                \tGROUP BY serial_no;
                
                SELECT city_name, MAX(calls_count) AS max_calls_count FROM intercity_calls_count
                \tGROUP BY city_name;
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

    private void add(Node node) {
        pane.getChildren().add(node);
    }

    private HBox createLabel(String text, StringProperty binding) {
        HBox box = new HBox();
        box.setSpacing(10);

        Label label = new Label(text);
        label.getStyleClass().add("result-label");

        Label value = new Label();
        value.textProperty().bind(binding);
        value.getStyleClass().add("result-label");

        box.getChildren().addAll(label, value);
        return box;
    }

    @Override
    public Node getNode() {
        return pane;
    }

}
