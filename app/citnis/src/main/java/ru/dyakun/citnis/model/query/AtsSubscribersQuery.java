package ru.dyakun.citnis.model.query;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.view.util.ColSpan;
import javafx.beans.property.*;
import javafx.collections.FXCollections;


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
                Group.of(
                        Field.ofStringType(ats)
                                .label("АТС"),
                        Field.ofBooleanType(onlyBeneficiaries)
                                .label("Только льготники").span(4),
                        Field.ofIntegerType(ageFrom)
                                .label("Возраст от").span(4),
                        Field.ofIntegerType(ageTo)
                                .label("до").span(4)
                )
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
