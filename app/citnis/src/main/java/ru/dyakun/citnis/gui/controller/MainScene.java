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
import ru.dyakun.citnis.gui.query.*;
import ru.dyakun.citnis.model.data.Payphone;
import ru.dyakun.citnis.model.data.PhoneNumber;
import ru.dyakun.citnis.model.data.PhoneOwner;
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
                            .intCol("Возраст", "age")
                            .stringCol("Пол", "gender")
                            .build())
                .title("Абоненты АТС");
        queryPages.add(atsSubscribersPage);

        var freePhoneNumbersPage = new QueryPageBuilder<>(new FreePhoneNumbersQuery())
                .tableview(new TableBuilder<PhoneNumber>()
                        .styleClass("query-table")
                        .intCol("Номер", "number")
                        .stringCol("Улица", "street")
                        .intCol("Дом", "house")
                        .build())
                .title("Свободные номеры");
        queryPages.add(freePhoneNumbersPage);

        var payphonesPage = new QueryPageBuilder<>(new PayphonesQuery())
                .tableview(new TableBuilder<Payphone>()
                        .styleClass("query-table")
                        .intCol("Номер", "number")
                        .stringCol("Улица", "street")
                        .intCol("Дом", "house")
                        .build())
                .title("Таксофоны");
        queryPages.add(payphonesPage);

        var parallelPhonesPage = new QueryPageBuilder<>(new ParallelPhonesQuery())
                .tableview(new TableBuilder<PhoneOwner>()
                        .styleClass("query-table")
                        .stringCol("Фамилия", "lastname")
                        .stringCol("Имя", "firstname")
                        .intCol("Номер", "number")
                        .stringCol("Улица", "street")
                        .intCol("Дом", "house")
                        .build())
                .title("Параллельные телефоны");
        queryPages.add(parallelPhonesPage);

        var phoneNumberByAddressPage = new QueryPageBuilder<>(new PhoneByAddressQuery())
                .tableview(new TableBuilder<PhoneNumber>()
                        .styleClass("query-table")
                        .intCol("Номер", "number")
                        .stringCol("Улица", "street")
                        .intCol("Дом", "house")
                        .build())
                .title("Телефоны по адресу");
        queryPages.add(phoneNumberByAddressPage);

        var subscribersByPhonePage = new QueryPageBuilder<>(new SubscribersByPhoneQuery())
                .tableview(new TableBuilder<Subscriber>()
                        .styleClass("query-table")
                        .stringCol("Фамилия", "lastname")
                        .stringCol("Имя", "firstname")
                        .intCol("Возраст", "age")
                        .stringCol("Пол", "gender")
                        .build())
                .title("Абоненты по номеру");
        queryPages.add(subscribersByPhonePage);

        var pairedPhonesForReplacementPage = new QueryPageBuilder<>(new PairedPhonesForReplacementQuery())
                .tableview(new TableBuilder<PhoneNumber>()
                        .styleClass("query-table")
                        .intCol("Номер", "number")
                        .stringCol("Улица", "street")
                        .intCol("Дом", "house")
                        .build())
                .title("Спаренные телефоны под замену");
        queryPages.add(pairedPhonesForReplacementPage);

        sideMenu.addPages("Запросы", queryPages);
        sideMenu.setCurrent(0);
    }

}
