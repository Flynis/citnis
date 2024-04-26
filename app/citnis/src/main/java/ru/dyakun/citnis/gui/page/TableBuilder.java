package ru.dyakun.citnis.gui.page;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableBuilder<T> {

    protected final TableView<T> tableView = new TableView<>();
    protected final Label placeholderLabel = new Label("");

    public TableBuilder() {
        tableView.setPlaceholder(placeholderLabel);
    }

    public TableBuilder<T> styleClass(String styleClass) {
        tableView.getStyleClass().add(styleClass);
        return this;
    }

    private void initCol(TableColumn<T, ?> column, int prefWidth) {
        column.setEditable(false);
        column.setSortable(false);
        column.setReorderable(false);
        column.setPrefWidth(prefWidth);
    }

    public TableBuilder<T> placeholder(String string) {
        placeholderLabel.setText(string);
        return this;
    }

    public TableBuilder<T> intCol(String name, String binding, int prefWidth) {
        TableColumn<T, Integer> column = new TableColumn<>(name);
        column.setCellValueFactory(new PropertyValueFactory<>(binding));
        tableView.getColumns().add(column);
        initCol(column, prefWidth);
        return this;
    }

    public TableBuilder<T> stringCol(String name, String binding, int prefWidth) {
        TableColumn<T, String> column = new TableColumn<>(name);
        column.setCellValueFactory(new PropertyValueFactory<>(binding));
        tableView.getColumns().add(column);
        initCol(column, prefWidth);
        return this;
    }

    public TableView<T> build() {
        tableView.setPrefHeight(1000);
        return tableView;
    }

}
