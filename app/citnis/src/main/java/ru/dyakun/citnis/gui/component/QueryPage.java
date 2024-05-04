package ru.dyakun.citnis.gui.component;

import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.dyakun.citnis.model.db.DatabaseManager;
import ru.dyakun.citnis.model.query.Query;

public class QueryPage<T> extends Page {

    private final Query<T> query;
    private final Button okBtn;
    private final TableView<T> tableView;
    private final Label resultLabel;

    public QueryPage(String title, Query<T> query, TableView<T> tableView) {
        super(title);
        this.query = query;
        this.tableView = tableView;

        okBtn = new Button("Найти");
        okBtn.getStyleClass().add("form-btn");

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(0, 0, 30, 30));
        hBox.getChildren().addAll(okBtn);

        Form form = query.getForm();
        FormRenderer formPane = new FormRenderer(form);
        for(var child: formPane.getChildren()) {
            child.getStyleClass().remove("formsfx-group");
        }

        BorderPane querySettings = new BorderPane();
        querySettings.getStyleClass().add("content");
        querySettings.setCenter(formPane);
        querySettings.setBottom(hBox);

        resultLabel = new Label("Найдено 0 результатов");
        resultLabel.getStyleClass().add("result-label");

        BorderPane resultPane = new BorderPane();
        resultPane.getStyleClass().add("content");
        resultPane.setPadding(new Insets(5, 5, 5, 30));
        resultPane.setLeft(resultLabel);

        BorderPane tableBox = new BorderPane();
        tableBox.setPadding(new Insets(5, 5, 5, 5));
        tableBox.getStyleClass().add("content");
        tableBox.setCenter(tableView);

        VBox contentArea = new VBox();
        contentArea.getStyleClass().add("content-area");
        contentArea.setPadding(new Insets(5, 5, 5, 5));
        contentArea.setSpacing(5);
        contentArea.getChildren().addAll(querySettings, resultPane, tableBox);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(contentArea);

        pane.setCenter(scrollPane);
        setEventHandlers();
    }

    private void setEventHandlers() {
        okBtn.setOnAction(e -> onClick());
    }

    private void setResultLabelText(int count) {
        StringBuilder builder = new StringBuilder("Найдено ");
        builder.append(count);
        builder.append(' ');
        int last2 = count % 100;
        int last = (last2 < 11 || last2 > 14) ? count % 10 : 0;
        switch (last) {
            case 1 -> builder.append("результат");
            case 2, 3, 4 -> builder.append("результата");
            default -> builder.append("результатов");
        }
        resultLabel.setText(builder.toString());
    }

    public void onClick() {
        Form form = query.getForm();
        if(form.isValid()) {
            form.persist();
            DatabaseManager manager = DatabaseManager.getInstance();
            var data = manager.executeQuery(query.getQuery(), query.getMapper());
            setResultLabelText(data.size());
            tableView.setItems(FXCollections.observableList(data));
        }
    }

    @Override
    public void reset() {
        tableView.getItems().clear();
        setResultLabelText(0);
    }

}
