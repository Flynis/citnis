package ru.dyakun.citnis.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import ru.dyakun.citnis.form.structure.Form;
import ru.dyakun.citnis.model.query.AtsSubscribersQuery;

import java.net.URL;
import java.util.ResourceBundle;

public class AtsSubscribersScene implements Initializable {

    @FXML
    public AnchorPane queryPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Form form = new AtsSubscribersQuery().getForm();
//        FormRenderer renderer = new FormRenderer(form);
//        renderer.getStyleClass().add("query-form");
//        renderer.setPrefWidth(500);
//        queryPane.getChildren().add(renderer);
    }

}
