package ru.dyakun.citnis.model.query;

import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.model.validators.CustomValidator;
import com.dlsc.formsfx.model.validators.StringLengthValidator;
import com.dlsc.formsfx.model.validators.Validator;
import ru.dyakun.citnis.model.selection.SelectionStorage;
import ru.dyakun.citnis.model.data.PhoneNumber;
import ru.dyakun.citnis.model.selection.SortType;
import ru.dyakun.citnis.model.sql.SelectQueryBuilder;

import static ru.dyakun.citnis.model.selection.Selections.isChosen;
import static ru.dyakun.citnis.model.selection.Selections.toInt;

public class PhoneByAddressQuery extends QueryBase<PhoneNumber> {

    private final SingleSelectionField<String> street;
    private final StringField house;
    private final BooleanField intercity;
    private final SingleSelectionField<String> numberSortType;

    public PhoneByAddressQuery() {
        SelectionStorage selection = SelectionStorage.getInstance();

        Validator<String> numberValidator = CustomValidator.forPredicate(string -> {
            for(int i = 0; i < string.length(); i++) {
                if(!Character.isDigit(string.charAt(i))) {
                    return false;
                }
            }
            return true;
        }, "Номер дома состоит только из цифр");
        Validator<String> lengthValidator = StringLengthValidator.between(0, 3,
                "Номер дома не длинее 3 цифр");

        street = Field
                .ofSingleSelectionType(selection.streets(), 0)
                .label("Улица")
                .span(6);

        house = Field.ofStringType("")
                .label("Дом")
                .span(6)
                .validate(lengthValidator, numberValidator);

        intercity = Field.ofBooleanType(false)
                .label("Выход на межгород");

        numberSortType = Field
                .ofSingleSelectionType(selection.numberSort(), 0)
                .label("Сортировать");

        form = Form.of(Group.of(street, house, intercity, numberSortType));

        mapper = rs -> {
            var phone = new PhoneNumber();
            phone.setNumber(rs.getInt("phone_no"));
            phone.setStreet(rs.getString("street_name"));
            phone.setNumber(rs.getInt("house_no"));
            return phone;
        };
    }

    private boolean isHouseChosen() {
        return !house.getValue().trim().isEmpty();
    }

    private int getConditionsCount() {
        int count = 0;
        count += toInt(isChosen(street.getSelection()));
        count += toInt(isHouseChosen());
        return count;
    }

    @Override
    public String getQuery() {
        String phoneNumbersView = (intercity.getValue()) ? "intercity_phone_numbers" : "phone_numbers_v";
        SortType sortType = SortType.fromNumberSortType(numberSortType.getSelection());

        SelectQueryBuilder query = new SelectQueryBuilder()
                .select("phone_no, street_name, house_no")
                .from(phoneNumbersView)
                .where(getConditionsCount())
                .and(isChosen(street.getSelection()), "(street_name = '%s')", street.getSelection())
                .and(isHouseChosen(), "(house_no = %d)", house.getValue())
                .orderBy("phone_no", sortType);
        return query.toString();
    }

    @Override
    public void onUpdate() {
        SelectionStorage storage = SelectionStorage.getInstance();
        street.items(storage.streets());
    }

}
