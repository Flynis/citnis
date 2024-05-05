package ru.dyakun.citnis.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ru.dyakun.citnis.gui.component.*;
import ru.dyakun.citnis.model.data.*;
import ru.dyakun.citnis.model.operation.*;
import ru.dyakun.citnis.model.query.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainScene implements Initializable {

    @FXML
    public VBox sidebar;

    @FXML
    public StackPane contentArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SideMenu sideMenu = new SideMenu(contentArea, sidebar);
        sideMenu.addPages("Запросы", createQueryPages());
        sideMenu.addPages("Операции", createOperationPages());
        sideMenu.setCurrent(0);
    }

    private static List<Page> createQueryPages() {
        List<Page> queryPages = new ArrayList<>();

        var subscribersListPage = new QueryPageBuilder<>(new SubscribersListQuery())
                .tableview(new TableBuilder<Subscriber>()
                        .styleClass("query-table")
                        .stringCol("Фамилия", "lastname", 150)
                        .stringCol("Имя", "firstname", 150)
                        .intCol("Возраст", "age", 100)
                        .stringCol("Пол", "gender", 100)
                        .build())
                .title("Список абонентов");
        queryPages.add(subscribersListPage.build());

        var freePhoneNumbersPage = new QueryPageBuilder<>(new FreePhoneNumbersQuery())
                .tableview(new TableBuilder<PhoneNumber>()
                        .styleClass("query-table")
                        .intCol("Номер", "number", 100)
                        .stringCol("Улица", "street", 150)
                        .intCol("Дом", "house", 50)
                        .build())
                .title("Свободные номера");
        queryPages.add(freePhoneNumbersPage.build());

        var debtorsListPage = new QueryPageBuilder<>(new DebtorsListQuery())
                .tableview(new TableBuilder<Debtor>()
                        .styleClass("query-table")
                        .stringCol("Фамилия", "lastname", 150)
                        .stringCol("Имя", "firstname", 150)
                        .doubleCol("Задолжность", "debt", 150)
                        .build())
                .title("Список должников");
        queryPages.add(debtorsListPage.build());

        var payphonesPage = new QueryPageBuilder<>(new PayphonesListQuery())
                .tableview(new TableBuilder<Payphone>()
                        .styleClass("query-table")
                        .intCol("Номер", "number", 100)
                        .stringCol("Улица", "street", 150)
                        .intCol("Дом", "house", 50)
                        .build())
                .title("Список таксофонов");
        queryPages.add(payphonesPage.build());

        var subscribersStatPage = new QueryPageBuilder<>(new SubscribersStatQuery())
                .tableview(new TableBuilder<SubscribersStat>()
                        .styleClass("query-table")
                        .stringCol("Район", "district", 150)
                        .doubleCol("Процент льготников", "percent", 200)
                        .intCol("Льготники", "beneficiariesCount", 100)
                        .intCol("Всего", "total", 100)
                        .build())
                .title("Соотношение абонентов");
        queryPages.add(subscribersStatPage.build());

        var parallelPhoneOwnersListPage = new QueryPageBuilder<>(new ParallelPhoneOwnersListQuery())
                .tableview(new TableBuilder<PhoneOwner>()
                        .styleClass("query-table")
                        .stringCol("Фамилия", "lastname", 150)
                        .stringCol("Имя", "firstname", 150)
                        .intCol("Номер телефона", "number", 100)
                        .build())
                .title("Параллельные телефоны");
        queryPages.add(parallelPhoneOwnersListPage.build());

        var phoneNumberByAddressPage = new QueryPageBuilder<>(new PhoneByAddressQuery())
                .tableview(new TableBuilder<PhoneNumber>()
                        .styleClass("query-table")
                        .intCol("Номер", "number", 100)
                        .stringCol("Улица", "street", 150)
                        .intCol("Дом", "house", 50)
                        .build())
                .title("Телефоны по адресу");
        queryPages.add(phoneNumberByAddressPage.build());

        var subscribersByPhonePage = new QueryPageBuilder<>(new SubscribersByPhoneQuery())
                .tableview(new TableBuilder<Subscriber>()
                        .styleClass("query-table")
                        .stringCol("Фамилия", "lastname", 150)
                        .stringCol("Имя", "firstname", 150)
                        .build())
                .title("Абоненты по номеру");
        queryPages.add(subscribersByPhonePage.build());

        var pairedPhonesForReplacementPage = new QueryPageBuilder<>(new PairedPhonesForReplacementQuery())
                .tableview(new TableBuilder<PhoneNumber>()
                        .styleClass("query-table")
                        .intCol("Номер", "number", 100)
                        .stringCol("Улица", "street", 150)
                        .intCol("Дом", "house", 50)
                        .build())
                .title("Спаренные телефоны под замену");
        queryPages.add(pairedPhonesForReplacementPage.build());

        StatisticsPage statistics = new StatisticsPage("Статистика");
        queryPages.add(statistics);

        return queryPages;
    }

    private static List<Page> createOperationPages() {
        List<Page> operationPages = new ArrayList<>();

        var createSubscriberPage = new OperationPage("Добавить абонента", new CreateSubscriberOp());
        operationPages.add(createSubscriberPage);

        var createPhoneNumberPage = new OperationPage("Добавить номер телефона", new CreatePhoneNumberOp());
        operationPages.add(createPhoneNumberPage);

        var changePhoneTypePage = new OperationPage("Изменить тип телефона", new ChangePhoneTypeOp());
        operationPages.add(changePhoneTypePage);

        var createPayphonePage = new OperationPage("Добавить таксофон", new CreatePayphoneOp());
        operationPages.add(createPayphonePage);

        var enableIntercityCallsPage = new OperationPage("Подключить межгород", new EnableIntercityCallsOp());
        operationPages.add(enableIntercityCallsPage);

        var disableIntercityCallsPage = new OperationPage("Отключить межгород", new DisableIntercityCallsOp());
        operationPages.add(disableIntercityCallsPage);

        var registerPhoneNumberForSubscriberPage = new OperationPage("Оформить номер", new RegisterPhoneNumberForSubscriberOp());
        operationPages.add(registerPhoneNumberForSubscriberPage);

        return operationPages;
    }

}
