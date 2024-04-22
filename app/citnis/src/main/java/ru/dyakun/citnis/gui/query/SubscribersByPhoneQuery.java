package ru.dyakun.citnis.gui.query;

import com.dlsc.formsfx.model.structure.*;
import ru.dyakun.citnis.model.Mapper;
import ru.dyakun.citnis.model.Query;
import ru.dyakun.citnis.model.SelectionStorage;
import ru.dyakun.citnis.model.data.Subscriber;

import static ru.dyakun.citnis.model.SelectionUtil.convertStringSortType;

public class SubscribersByPhoneQuery implements Query<Subscriber> {

    private final StringField number;
    private final SingleSelectionField<String> sortType;
    private final Form form;
    private final Mapper<Subscriber> mapper;

    public SubscribersByPhoneQuery() {
        SelectionStorage selection = SelectionStorage.getInstance();
        number = Field
                .ofStringType("")
                .placeholder("1001000")
                .label("Номер телефона")
                .span(8);
        sortType = Field
                .ofSingleSelectionType(selection.stringSort(), 0)
                .label("Сортировать")
                .span(4);
        form = Form.of(Group.of(number, sortType));
        mapper = rs -> new Subscriber(
                rs.getString("last_name"),
                rs.getString("first_name"),
                rs.getString("surname"),
                rs.getInt("age"),
                (rs.getString("gender").equals("m") ? "мужской" : "женский")
        );
    }

    @Override
    public Form getForm() {
        return form;
    }

    @Override
    public String getQuery() {
        StringBuilder builder = new StringBuilder("SELECT last_name, first_name, surname, gender, age\n");
        builder.append("\tFROM ats_subscribers\n");
        builder.append(String.format("\tWHERE (phone_no = %s)\n", number.getValue()));
        String sqlSort = convertStringSortType(sortType.getSelection());
        builder.append(String.format("\tORDER BY last_name %s;\n", sqlSort));
        return builder.toString();
    }

    @Override
    public Mapper<Subscriber> getMapper() {
        return mapper;
    }

}
