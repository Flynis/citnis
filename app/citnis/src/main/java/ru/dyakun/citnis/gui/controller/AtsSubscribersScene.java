package ru.dyakun.citnis.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import ru.dyakun.citnis.gui.component.QueryPane;
import ru.dyakun.citnis.gui.query.AtsSubscribersQuery;

import java.net.URL;
import java.util.ResourceBundle;

public class AtsSubscribersScene implements Initializable {

    @FXML
    public BorderPane queryParent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        queryParent.setCenter(new QueryPane(new AtsSubscribersQuery()));
    }

}
