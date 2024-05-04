package ru.dyakun.citnis.gui.component;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dyakun.citnis.model.data.Statistics;
import ru.dyakun.citnis.model.db.DatabaseManager;
import ru.dyakun.citnis.model.db.Scheduler;
import ru.dyakun.citnis.model.db.Mapper;

import java.util.*;

public class StatisticsPage extends Page {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsPage.class);

    private final Statistics stat = new Statistics();
    private final Label maxDebtAtsLabel = new Label();
    private final Label minDebtorAtsLabel = new Label();
    private final Label maxDebtorAtsLabel = new Label();
    private final Label intercityLeaderLabel = new Label();

    public StatisticsPage(String title) {
        super(title);

        VBox contentArea = new VBox();
        contentArea.setPrefWidth(Double.POSITIVE_INFINITY);
        contentArea.setPadding(new Insets(5, 5, 5, 5));
        contentArea.getStyleClass().add("content-area");
        contentArea.setSpacing(10);

        maxDebtAtsLabel.getStyleClass().add("result-label");
        minDebtorAtsLabel.getStyleClass().add("result-label");
        maxDebtorAtsLabel.getStyleClass().add("result-label");
        intercityLeaderLabel.getStyleClass().add("result-label");

        var box = new VBox();
        box.setSpacing(10);
        box.setPadding(new Insets(10, 10, 10, 10));
        box.getStyleClass().add("content");
        box.getChildren().addAll(maxDebtAtsLabel, maxDebtorAtsLabel, minDebtorAtsLabel, intercityLeaderLabel);

        contentArea.getChildren().add(box);
        pane.setCenter(contentArea);

        update();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                update();
            }
        };
        Scheduler.getInstance().schedule(task, 5 * 60 * 1000);
    }

    private void update() {
        logger.info("Updating statistics data");
        DatabaseManager db = DatabaseManager.getInstance();

        String query = """
                SELECT serial_no, MAX(total_debt) AS max_total_debt
                \tFROM ats_debt_stat
                \tGROUP BY serial_no;
                
                SELECT serial_no, MAX(debtors_count) AS max_debtors_count
                \tFROM ats_debt_stat
                \tGROUP BY serial_no;
                
                SELECT serial_no, MIN(debtors_count) AS min_debtors_count
                \tFROM ats_debt_stat
                \tGROUP BY serial_no;
                
                SELECT city_name, MAX(calls_count) AS max_calls_count
                \tFROM intercity_calls_count
                \tGROUP BY city_name;
                """;

        Mapper<Statistics> maxDebtMapper = rs -> {
            stat.setMaxDebtAts(rs.getString("serial_no"));
            stat.setMaxDebt(rs.getDouble("max_total_debt"));
            return stat;
        };
        Mapper<Statistics> maxDebtorsMapper = rs -> {
            stat.setMaxDebtorAts(rs.getString("serial_no"));
            stat.setMaxDebtors(rs.getInt("max_debtors_count"));
            return stat;
        };
        Mapper<Statistics> minDebtorsMapper = rs -> {
            stat.setMinDebtorAts(rs.getString("serial_no"));
            stat.setMinDebtors(rs.getInt("min_debtors_count"));
            return stat;
        };
        Mapper<Statistics> intercityLeaderMapper = rs -> {
            stat.setIntercityLeader(rs.getString("city_name"));
            stat.setCallsCount(rs.getInt("max_calls_count"));
            return stat;
        };

        db.executeMultipleQueries(
                query,
                List.of(maxDebtMapper,
                        maxDebtorsMapper,
                        minDebtorsMapper,
                        intercityLeaderMapper)
        );

        Platform.runLater(this::updateLabels);
    }

    private void updateLabels() {
        maxDebtAtsLabel.setText(String.format(
                "Атс с наибольшей суммой задолжности - %s (задолжность - %.2f руб)",
                stat.getMaxDebtAts(),
                stat.getMaxDebt()));
        maxDebtorAtsLabel.setText(String.format(
                "Атс с наибольшим числом должников - %s (должников - %d чел)",
                stat.getMaxDebtorAts(),
                stat.getMaxDebtors()));
        minDebtorAtsLabel.setText(String.format(
                "Атс с наименьшим числом должников - %s (должников - %d чел)",
                stat.getMinDebtorAts(),
                stat.getMinDebtors()));
        intercityLeaderLabel.setText(String.format(
                "Город, с которым происходит большее количество междугородных переговоров - %s (переговоров - %d)",
                stat.getIntercityLeader(),
                stat.getCallsCount()));
    }

}
