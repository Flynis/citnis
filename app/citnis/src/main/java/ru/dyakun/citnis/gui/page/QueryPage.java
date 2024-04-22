package ru.dyakun.citnis.gui.page;

import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.dyakun.citnis.model.DatabaseManager;
import ru.dyakun.citnis.model.Query;

public class QueryPage<T> extends BorderPane {

    private final Query<T> query;
    private final Button okBtn;
    private final TableView<T> tableView;

    public QueryPage(String title, Query<T> query, TableView<T> tableView) {
        this.query = query;
        this.tableView = tableView;

        setPrefHeight(Double.POSITIVE_INFINITY);
        setPrefWidth(Double.POSITIVE_INFINITY);

        Label pageTitle = new Label(title);
        pageTitle.getStyleClass().add("page-title");

        BorderPane pageHeader = new BorderPane();
        pageHeader.setPrefHeight(40.0);
        pageHeader.setPrefWidth(Double.POSITIVE_INFINITY);
        pageHeader.getStyleClass().add("page-header");
        pageHeader.setPadding(new Insets(5, 0, 0, 30));
        pageHeader.setLeft(pageTitle);

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

        BorderPane tableBox = new BorderPane();
        tableBox.setPadding(new Insets(5, 5, 5, 5));
        tableBox.getStyleClass().add("content");
        tableBox.setCenter(tableView);

        VBox contentArea = new VBox();
        contentArea.getStyleClass().add("content-area");
        contentArea.setPadding(new Insets(5, 5, 5, 5));
        contentArea.setSpacing(5);
        contentArea.getChildren().addAll(querySettings, tableBox);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(contentArea);

        setTop(pageHeader);
        setCenter(scrollPane);
        setEventHandlers();
    }

    private void setEventHandlers() {
        okBtn.setOnAction(e -> onClick());
    }

    public void onClick() {
        Form form = query.getForm();
        if(form.isValid()) {
            form.persist();
            DatabaseManager manager = DatabaseManager.getInstance();
            var subscribers = manager.executeQuery(query.getQuery(), query.getMapper());
            tableView.setItems(FXCollections.observableList(subscribers));
        }
    }

}
