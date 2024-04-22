package ru.dyakun.citnis.gui.page;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableBuilder<T> {

    protected final TableView<T> tableView = new TableView<>();

    public TableBuilder<T> styleClass(String styleClass) {
        tableView.getStyleClass().add(styleClass);
        return this;
    }

    private void initCol(TableColumn<T, ?> column) {
        column.setEditable(false);
        column.setSortable(false);
        column.setReorderable(false);
    }

    public TableBuilder<T> intCol(String name, String binding) {
        TableColumn<T, Integer> column = new TableColumn<>(name);
        column.setCellValueFactory(new PropertyValueFactory<>(binding));
        tableView.getColumns().add(column);
        initCol(column);
        return this;
    }

    public TableBuilder<T> stringCol(String name, String binding) {
        TableColumn<T, String> column = new TableColumn<>(name);
        column.setCellValueFactory(new PropertyValueFactory<>(binding));
        tableView.getColumns().add(column);
        initCol(column);
        return this;
    }

    public TableView<T> build() {
        return tableView;
    }

}
