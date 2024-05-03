package ru.dyakun.citnis.gui.component;

import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StatisticsPage extends Page {

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
