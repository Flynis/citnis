package ru.dyakun.citnis.gui.component;

import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import ru.dyakun.citnis.gui.query.Query;

public class QueryPane extends BorderPane {

    public QueryPane(Query query) {
        Form form = query.getForm();
        FormRenderer renderer = new FormRenderer(form);
        for(var child: renderer.getChildren()) {
            child.getStyleClass().remove("formsfx-group");
        }

        setCenter(renderer);
    }

}
