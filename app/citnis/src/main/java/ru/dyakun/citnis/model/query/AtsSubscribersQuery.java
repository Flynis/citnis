package ru.dyakun.citnis.model.query;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import ru.dyakun.citnis.form.structure.*;

public class AtsSubscribersQuery implements Query {

    private final StringProperty ats;
    private final BooleanProperty onlyBeneficiaries = new SimpleBooleanProperty(false);
    private final IntegerProperty ageFrom = new SimpleIntegerProperty(18);
    private final IntegerProperty ageTo = new SimpleIntegerProperty(100);
    private final ListProperty<String> allAts = new SimpleListProperty<>(FXCollections.observableArrayList("Не выбрано", "АТС"));
    private final Form form;

    public AtsSubscribersQuery() {
        ats = new SimpleStringProperty(allAts.get(0));
        form = Form.of(
                Field.ofBoolean(onlyBeneficiaries)
                        .label("Только льготники")
//                Field.ofStringType(ats)
//                        .label("АТС"),
//
//                Field.ofIntegerType(ageFrom)
//                        .label("Возраст от"),
//                Field.ofIntegerType(ageTo)
//                        .label("до")
        );
    }

    @Override
    public Form getForm() {
        return form;
    }

    @Override
    public String getQuery() {
        return "";
    }

}
