package ru.dyakun.citnis.model.query;

import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.model.validators.CustomValidator;
import com.dlsc.formsfx.model.validators.IntegerRangeValidator;
import com.dlsc.formsfx.model.validators.Validator;
import ru.dyakun.citnis.model.data.Ats;
import ru.dyakun.citnis.model.selection.Gender;
import ru.dyakun.citnis.model.selection.SelectionStorage;
import ru.dyakun.citnis.model.data.Subscriber;
import ru.dyakun.citnis.model.selection.SortType;
import ru.dyakun.citnis.model.sql.SelectQueryBuilder;

import static ru.dyakun.citnis.model.selection.Selections.*;

public class SubscribersListQuery extends QueryBase<Subscriber> {

    private final SingleSelectionField<String> ats;
    private final BooleanField onlyBeneficiaries;
    private final IntegerField ageFrom;
    private final IntegerField ageTo;
    private final SingleSelectionField<String> firstLastNameChar; // TODO
    private final SingleSelectionField<String> stringSortType;

    public SubscribersListQuery() {
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
            subscriber.setGender(Gender.fromFirstLetter(rs.getString("gender")).getLabel());
            subscriber.setBenefit(rs.getDouble("benefit"));
            return subscriber;
        };
    }

    private int getConditionsCount() {
        int count = 0;
        count += toInt(isChosen(ats.getSelection()));
        count += 2; // age from and age to
        count += toInt(onlyBeneficiaries.getValue());
        return count;
    }

    @Override
    public String getQuery() {
        SelectionStorage storage = SelectionStorage.getInstance();

        Ats a = storage.getAtsByName(ats.getSelection());
        String atsSerial = (a != null) ? a.getSerial() : "";
        SortType sortType = SortType.fromStringSortType(stringSortType.getSelection());

        SelectQueryBuilder query = new SelectQueryBuilder()
                .select("last_name, first_name, surname, gender, age, benefit")
                .from("ats_subscribers")
                .where(getConditionsCount())
                .and(isChosen(ats.getSelection()), "(serial_no = '%s')", atsSerial)
                .and(true, "(age >= %d)", ageFrom.getValue())
                .and(true, "(age <= %d)", ageTo.getValue())
                .and(onlyBeneficiaries.getValue(), "(benefit >= 0.5)")
                .orderBy("last_name", sortType);
        return query.toString();
    }

    @Override
    public void onUpdate() {
        SelectionStorage selection = SelectionStorage.getInstance();
        ats.items(selection.ats());
    }

}
