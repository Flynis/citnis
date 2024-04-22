package ru.dyakun.citnis.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dyakun.citnis.gui.component.SideMenu;
import ru.dyakun.citnis.gui.page.PageBuilder;
import ru.dyakun.citnis.gui.page.QueryPageBuilder;
import ru.dyakun.citnis.gui.page.TableBuilder;
import ru.dyakun.citnis.gui.query.AtsSubscribersQuery;
import ru.dyakun.citnis.model.data.Subscriber;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainScene implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(MainScene.class);

    @FXML
    public VBox sidebar;

    @FXML
    public StackPane contentArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SideMenu sideMenu = new SideMenu(contentArea, sidebar);
        List<PageBuilder> queryPages = new ArrayList<>();

        var atsSubscribersPage = new QueryPageBuilder<>(new AtsSubscribersQuery())
                .tableview(new TableBuilder<Subscriber>()
                            .styleClass("query-table")
                            .stringCol("Фамилия", "lastname")
                            .stringCol("Имя", "firstname")
                            .stringCol("Отчество", "surname")
                            .intCol("Возраст", "age")
                            .stringCol("Пол", "gender")
                            .build())
                .title("Абоненты АТС");
        queryPages.add(atsSubscribersPage);

        sideMenu.addPages("Запросы", queryPages);
        sideMenu.setCurrent(0);
    }
}
