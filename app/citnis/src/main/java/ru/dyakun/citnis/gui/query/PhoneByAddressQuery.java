package ru.dyakun.citnis.gui.query;

import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.model.validators.CustomValidator;
import com.dlsc.formsfx.model.validators.StringLengthValidator;
import com.dlsc.formsfx.model.validators.Validator;
import ru.dyakun.citnis.model.Mapper;
import ru.dyakun.citnis.model.query.Query;
import ru.dyakun.citnis.model.selection.SelectionStorage;
import ru.dyakun.citnis.model.data.PhoneNumber;

import static ru.dyakun.citnis.model.selection.SelectionUtil.convertNumberSortType;

public class PhoneByAddressQuery implements Query<PhoneNumber> {

    private final SingleSelectionField<String> street;
    private final StringField house;
    private final BooleanField intercity;
    private final SingleSelectionField<String> sortType;
    private final Form form;
    private final Mapper<PhoneNumber> mapper;

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
        sortType = Field
                .ofSingleSelectionType(selection.numberSort(), 0)
                .label("Сортировать");
        form = Form.of(Group.of(street, house, intercity, sortType));
        mapper = rs -> new PhoneNumber(
                rs.getInt("phone_no"),
                rs.getString("street_name"),
                rs.getInt("house_no")
        );
    }

    @Override
    public Form getForm() {
        return form;
    }

    @Override
    public String getQuery() {
        StringBuilder builder = new StringBuilder("SELECT phone_no, street_name, house_no\n");
        String phoneNumbersView = (intercity.getValue()) ? "intercity_phone_numbers" : "phone_numbers_v";
        builder.append(String.format("\tFROM %s\n", phoneNumbersView));
        builder.append("\tJOIN current_city USING(address_id)\n");
        boolean streetChosen = !street.getSelection().equals(SelectionStorage.notChosen);
        if(streetChosen) {
            String streetName = street.getSelection();
            builder.append(String.format("\tWHERE (street_name = '%s')\n", streetName));
        }
        if(!house.getValue().trim().isEmpty()) {
            int houseNumber = Integer.parseInt(house.getValue());
            if(streetChosen) {
                builder.append(String.format("\t\tAND (house_no = %d)\n", houseNumber));
            } else {
                builder.append(String.format("\tWHERE (house_no = %d)\n", houseNumber));
            }
        }
        String sqlSort = convertNumberSortType(sortType.getSelection());
        builder.append(String.format("\tORDER BY phone_no %s;\n", sqlSort));
        return builder.toString();
    }

    @Override
    public Mapper<PhoneNumber> getMapper() {
        return mapper;
    }

}
