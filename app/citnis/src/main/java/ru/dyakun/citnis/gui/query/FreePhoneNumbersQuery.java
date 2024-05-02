package ru.dyakun.citnis.gui.query;

import com.dlsc.formsfx.model.structure.*;
import ru.dyakun.citnis.model.Mapper;
import ru.dyakun.citnis.model.query.Query;
import ru.dyakun.citnis.model.selection.SelectionStorage;
import ru.dyakun.citnis.model.data.PhoneNumber;
import ru.dyakun.citnis.model.selection.SortType;

public class FreePhoneNumbersQuery implements Query<PhoneNumber> {

    private final SingleSelectionField<String> ats;
    private final SingleSelectionField<String> district;
    private final SingleSelectionField<String> numberSortType;

    private final Form form;
    private final Mapper<PhoneNumber> mapper;

    public FreePhoneNumbersQuery() {
        SelectionStorage selection = SelectionStorage.getInstance();

        ats = Field
                .ofSingleSelectionType(selection.ats(), 0)
                .label("АТС");

        district = Field
                .ofSingleSelectionType(selection.districts(), 0)
                .label("Район");

        numberSortType = Field
                .ofSingleSelectionType(selection.numberSort(), 0)
                .label("Сортировать");

        form = Form.of(Group.of(ats, district, numberSortType));

        mapper = rs -> {
            var phone = new PhoneNumber();
            phone.setNumber(rs.getInt("phone_no"));
            phone.setStreet(rs.getString("street_name"));
            phone.setNumber(rs.getInt("house_no"));
            return phone;
        };
    }

    @Override
    public Form getForm() {
        return form;
    }

    @Override
    public String getQuery() {
        SelectionStorage storage = SelectionStorage.getInstance();
        StringBuilder builder = new StringBuilder("SELECT phone_no, street_name, house_no\n");
        builder.append("\tFROM free_phone_numbers\n");
        boolean atsChosen = !ats.getSelection().equals(SelectionStorage.notChosen);
        if(atsChosen) {
            String atsSerial = storage.getAtsByName(ats.getSelection()).getSerial();
            builder.append(String.format("\tWHERE (serial_no = '%s')\n", atsSerial));
        }
        if(!district.getSelection().equals(SelectionStorage.notChosen)) {
            String districtName = district.getSelection();
            if(atsChosen) {
                builder.append(String.format("\t\tAND (district_name = '%s')\n", districtName));
            } else {
                builder.append(String.format("\tWHERE (district_name = '%s')\n", districtName));
            }
        }
        String sqlSort = SortType.fromNumberSortType(numberSortType.getSelection()).getSqlSortType();
        builder.append(String.format("\tORDER BY phone_no %s;\n", sqlSort));
        return builder.toString();
    }

    @Override
    public Mapper<PhoneNumber> getMapper() {
        return mapper;
    }

}
