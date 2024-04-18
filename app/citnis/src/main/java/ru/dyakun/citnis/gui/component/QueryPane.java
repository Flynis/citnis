package ru.dyakun.citnis.gui.component;

import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import ru.dyakun.citnis.model.query.Query;

public class QueryPane extends BorderPane {

    public QueryPane(Query query) {
        Form form = query.getForm();
        FormRenderer renderer = new FormRenderer(form);
        renderer.getStyleClass().add("query-pane");
        renderer.setPrefWidth(900);
        renderer.getStyleClass().remove("formsfx-form");

        Button button = new Button("Ok");
        HBox hBox = new HBox(button);

        setCenter(renderer);
        setBottom(hBox);
    }

}
