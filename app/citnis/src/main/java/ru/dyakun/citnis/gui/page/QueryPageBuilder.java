package ru.dyakun.citnis.gui.page;

import javafx.scene.Node;
import javafx.scene.control.TableView;
import ru.dyakun.citnis.model.Query;

public class QueryPageBuilder<T> extends PageBuilder {

    protected final Query<T> query;
    protected TableView<T> tableView = new TableView<>();

    public QueryPageBuilder(Query<T> query) {
        this.query = query;
    }

    public QueryPageBuilder<T> tableview(TableView<T> tableView) {
        this.tableView = tableView;
        return this;
    }

    @Override
    public Node build() {
        return new QueryPage<>(title, query, tableView);
    }

}
