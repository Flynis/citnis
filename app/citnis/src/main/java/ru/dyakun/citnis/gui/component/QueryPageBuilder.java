package ru.dyakun.citnis.gui.component;

import javafx.scene.control.TableView;
import ru.dyakun.citnis.model.query.Query;

public class QueryPageBuilder<T> {

    private String title = "";
    private final Query<T> query;
    private TableView<T> tableView = new TableView<>();

    public QueryPageBuilder(Query<T> query) {
        this.query = query;
    }

    public QueryPageBuilder<T> title(String title) {
        this.title = title;
        return this;
    }

    public QueryPageBuilder<T> tableview(TableView<T> tableView) {
        this.tableView = tableView;
        return this;
    }

    public Page build() {
        return new QueryPage<>(title, query, tableView);
    }

}
