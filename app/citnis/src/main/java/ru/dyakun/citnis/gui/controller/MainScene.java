package ru.dyakun.citnis.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dyakun.citnis.gui.component.SideMenu;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScene implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(MainScene.class);

    @FXML
    public VBox sideMenu;

    @FXML
    public StackPane contentArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new SideMenu(contentArea, sideMenu);
    }
}
