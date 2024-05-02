package ru.dyakun.citnis.gui.query;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.SingleSelectionField;
import ru.dyakun.citnis.model.Mapper;
import ru.dyakun.citnis.model.query.Query;
import ru.dyakun.citnis.model.selection.SelectionStorage;
import ru.dyakun.citnis.model.data.Payphone;

import static ru.dyakun.citnis.model.selection.SelectionUtil.convertNumberSortType;

public class PayphonesQuery implements Query<Payphone> {

    private final SingleSelectionField<String> ats;
    private final SingleSelectionField<String> district;
    private final SingleSelectionField<String> sortType;
    private final Form form;
    private final Mapper<Payphone> mapper;

    public PayphonesQuery() {
        SelectionStorage selection = SelectionStorage.getInstance();
        ats = Field
                .ofSingleSelectionType(selection.cityAts(), 0)
                .label("АТС");
        district = Field
                .ofSingleSelectionType(selection.districts(), 0)
                .label("Район");
        sortType = Field
                .ofSingleSelectionType(selection.numberSort(), 0)
                .label("Сортировать");
        form = Form.of(Group.of(ats, district, sortType));
        mapper = rs -> new Payphone(
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
        builder.append("\tFROM payphones_by_ats\n");
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
    public Mapper<Payphone> getMapper() {
        return mapper;
    }

}
