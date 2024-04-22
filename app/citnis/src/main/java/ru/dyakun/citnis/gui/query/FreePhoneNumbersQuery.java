package ru.dyakun.citnis.gui.query;

import com.dlsc.formsfx.model.structure.*;
import ru.dyakun.citnis.model.Mapper;
import ru.dyakun.citnis.model.Query;
import ru.dyakun.citnis.model.SelectionStorage;
import ru.dyakun.citnis.model.data.PhoneNumber;

import static ru.dyakun.citnis.model.SelectionUtil.convertNumberSortType;

public class FreePhoneNumbersQuery implements Query<PhoneNumber> {

    private final SingleSelectionField<String> ats;
    private final SingleSelectionField<String> district;
    private final SingleSelectionField<String> sortType;
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
        sortType = Field
                .ofSingleSelectionType(selection.numberSort(), 0)
                .label("Сортировать");
        form = Form.of(Group.of(ats, district, sortType));
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
        String sqlSort = convertNumberSortType(sortType.getSelection());
        builder.append(String.format("\tORDER BY phone_no %s;\n", sqlSort));
        return builder.toString();
    }

    @Override
    public Mapper<PhoneNumber> getMapper() {
        return mapper;
    }

}
