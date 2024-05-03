package ru.dyakun.citnis.gui.query;

import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.model.validators.CustomValidator;
import com.dlsc.formsfx.model.validators.IntegerRangeValidator;
import com.dlsc.formsfx.model.validators.Validator;
import ru.dyakun.citnis.model.Mapper;
import ru.dyakun.citnis.model.query.Query;
import ru.dyakun.citnis.model.query.QueryStringBuilder;
import ru.dyakun.citnis.model.selection.SelectionStorage;
import ru.dyakun.citnis.model.data.Subscriber;
import ru.dyakun.citnis.model.selection.SortType;

import static ru.dyakun.citnis.model.selection.Selections.*;

public class AtsSubscribersQuery implements Query<Subscriber> {

    private final SingleSelectionField<String> ats;
    private final BooleanField onlyBeneficiaries;
    private final IntegerField ageFrom;
    private final IntegerField ageTo;
    private final SingleSelectionField<String> firstLastNameChar; // TODO
    private final SingleSelectionField<String> stringSortType;

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

        Validator<Integer> ageValidator = IntegerRangeValidator.between(14, 130,
                "Возраст от 14 до 130 лет");
        ageFrom = Field
                .ofIntegerType(18)
                .label("Возраст от")
                .span(4)
                .validate(ageValidator);

        Validator<Integer> ageToValidator = CustomValidator.forPredicate(
                integer -> integer >= ageFrom.getValue(), "Верхняя граница должна быть больше нижней");
        ageTo = Field.ofIntegerType(100)
                .label("до")
                .span(4)
                .validate(ageValidator, ageToValidator);

        firstLastNameChar = Field
                .ofSingleSelectionType(selection.alphabet(), 0)
                .label("Первая буква фамилии")
                .span(8);

        stringSortType = Field
                .ofSingleSelectionType(selection.stringSort(), 0)
                .label("Сортировать")
                .span(4);

        form = Form.of(Group.of(ats, onlyBeneficiaries, ageFrom, ageTo , firstLastNameChar, stringSortType));

        mapper = rs -> {
            var subscriber =  new Subscriber();
            subscriber.setLastname(rs.getString("last_name"));
            subscriber.setFirstname(rs.getString("first_name"));
            subscriber.setSurname(rs.getString("surname"));
            subscriber.setAge(rs.getInt("age"));
            subscriber.setGender((rs.getString("gender").equals("m") ? "мужской" : "женский"));
            subscriber.setBenefit(rs.getDouble("benefit"));
            return subscriber;
        };
    }

    public int getConditionsCount() {
        int count = 0;
        count +=  toInt(isChosen(ats.getSelection()));
        count += 2; // age from and age to
        count += toInt(onlyBeneficiaries.getValue());
        return count;
    }

    @Override
    public Form getForm() {
        return form;
    }

    @Override
    public String getQuery() {
        SelectionStorage storage = SelectionStorage.getInstance();

        QueryStringBuilder query = new QueryStringBuilder()
                .select("last_name, first_name, surname, gender, age, benefit")
                .from("ats_subscribers")
                .where(getConditionsCount())
                .and(isChosen(ats.getSelection()), )
        StringBuilder builder = new StringBuilder("SELECT \n");
        builder.append("\tFROM ats_subscribers\n");
        builder.append(String.format("\tWHERE (age >= %d) AND (age <= %d)\n", ageFrom.getValue(), ageTo.getValue()));
        if(isChosen(ats.getSelection())) {
            String atsSerial = storage.getAtsByName(ats.getSelection()).getSerial();
            builder.append(String.format("\t\tAND (serial_no = '%s')\n", atsSerial));
        }
        if(onlyBeneficiaries.getValue()) {
            builder.append("\t\tAND (benefit >= 0.5)\n");
        }
        String sqlSort = SortType.fromStringSortType(stringSortType.getSelection()).getSqlSortType();
        builder.append(String.format("\tORDER BY last_name %s;\n", sqlSort));
        return builder.toString();
    }

    @Override
    public Mapper<Subscriber> getMapper() {
        return mapper;
    }

}
