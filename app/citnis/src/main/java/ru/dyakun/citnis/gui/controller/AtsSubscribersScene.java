package ru.dyakun.citnis.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import ru.dyakun.citnis.gui.component.QueryPane;
import ru.dyakun.citnis.model.query.AtsSubscribersQuery;

import java.net.URL;
import java.util.ResourceBundle;

public class AtsSubscribersScene implements Initializable {

    @FXML
    public AnchorPane queryForm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        queryForm.getChildren().add(new QueryPane(new AtsSubscribersQuery()));
    }

}
