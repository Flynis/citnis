package ru.dyakun.citnis.gui.query;

import com.dlsc.formsfx.model.structure.*;
import ru.dyakun.citnis.model.Mapper;
import ru.dyakun.citnis.model.Query;
import ru.dyakun.citnis.model.SelectionStorage;
import ru.dyakun.citnis.model.data.PhoneNumber;

import static ru.dyakun.citnis.model.SelectionUtil.convertNumberSortType;

public class PhoneByAddressQuery implements Query<PhoneNumber> {

    private final SingleSelectionField<String> street;
    private final IntegerField house;
    private final BooleanField intercity;
    private final SingleSelectionField<String> sortType;
    private final Form form;
    private final Mapper<PhoneNumber> mapper;

    public PhoneByAddressQuery() {
        SelectionStorage selection = SelectionStorage.getInstance();
        street = Field
                .ofSingleSelectionType(selection.streets(), 0)
                .label("Улица")
                .span(6);
        house = Field.ofIntegerType(0)
                .label("Дом")
                .span(6);
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
        if(house.getValue() != 0) {
            if(streetChosen) {
                builder.append(String.format("\t\tAND (house_no = %d)\n", house.getValue()));
            } else {
                builder.append(String.format("\tWHERE (house_no = %d)\n", house.getValue()));
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
