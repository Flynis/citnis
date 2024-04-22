package ru.dyakun.citnis.gui.query;

import com.dlsc.formsfx.model.structure.*;
import ru.dyakun.citnis.model.Mapper;
import ru.dyakun.citnis.model.Query;
import ru.dyakun.citnis.model.SelectionStorage;
import ru.dyakun.citnis.model.data.Ats;
import ru.dyakun.citnis.model.data.Subscriber;

public class AtsSubscribersQuery implements Query<Subscriber> {

    private final SingleSelectionField<String> ats;
    private final BooleanField onlyBeneficiaries;
    private final IntegerField ageFrom;
    private final IntegerField ageTo;
    private final SingleSelectionField<String> firstLastNameChar;
    private final SingleSelectionField<String> sortType;
    private final Form form;
    private final Mapper<Subscriber> mapper;

    public AtsSubscribersQuery() {
        SelectionStorage selection = SelectionStorage.getInstance();
        ats = Field
                .ofSingleSelectionType(selection.ats(), 0)
                .label("АТС");
        onlyBeneficiaries = Field
                .ofBooleanType(false)
                .label("Только льготники")
                .span(4);
        ageFrom = Field
                .ofIntegerType(18)
                .label("Возраст от")
                .span(4);
        ageTo = Field.ofIntegerType(100)
                .label("до")
                .span(4);
        firstLastNameChar = Field
                .ofSingleSelectionType(selection.alphabet(), 0)
                .label("Первая буква фамилии")
                .span(8);
        sortType = Field
                .ofSingleSelectionType(selection.stringSort(), 0)
                .label("Сортировать")
                .span(4);
        form = Form.of(Group.of(ats, onlyBeneficiaries, ageFrom, ageTo , firstLastNameChar, sortType));
        mapper = rs -> new Subscriber(
                rs.getString("last_name"),
                rs.getString("first_name"),
                rs.getString("surname"),
                rs.getInt("age"),
                (rs.getString("gender").equals("m") ? "male" : "female")
        );
    }

    @Override
    public Form getForm() {
        return form;
    }

    @Override
    public String getQuery() {
        SelectionStorage storage = SelectionStorage.getInstance();
        StringBuilder builder = new StringBuilder("SELECT last_name, first_name, surname, gender, age\n");
        builder.append("\tFROM ats_subscribers\n");
        builder.append(String.format("\tWHERE (age >= %d) AND (age <= %d)\n", ageFrom.getValue(), ageTo.getValue()));
        if(!ats.getSelection().equals(SelectionStorage.notChosen)) {
            String atsSerial = storage.getAtsByName(ats.getSelection()).getSerial();
            builder.append(String.format("\t\tAND (serial_no = %s)\n", atsSerial));
        }
        if(onlyBeneficiaries.getValue()) {
            builder.append("\t\tAND (benefit >= 0.5)\n");
        }
        String sqlSort = storage.sqlSortTypeFromStringSortType(sortType.getSelection());
        builder.append(String.format("\tORDER BY last_name %s;\n", sqlSort));
        return builder.toString();
    }

    @Override
    public Mapper<Subscriber> getMapper() {
        return mapper;
    }

}
