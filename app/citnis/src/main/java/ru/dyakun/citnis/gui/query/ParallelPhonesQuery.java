package ru.dyakun.citnis.gui.query;

import com.dlsc.formsfx.model.structure.*;
import ru.dyakun.citnis.model.Mapper;
import ru.dyakun.citnis.model.Query;
import ru.dyakun.citnis.model.SelectionStorage;
import ru.dyakun.citnis.model.data.PhoneOwner;

import static ru.dyakun.citnis.model.SelectionUtil.convertStringSortType;
import static ru.dyakun.citnis.model.SelectionUtil.getViewNameByAtsType;

public class ParallelPhonesQuery implements Query<PhoneOwner> {

    private final SingleSelectionField<String> ats;
    private final SingleSelectionField<String> atsType;
    private final SingleSelectionField<String> district;
    private final BooleanField onlyBeneficiaries;
    private final SingleSelectionField<String> sortType;
    private final Form form;
    private final Mapper<PhoneOwner> mapper;

    public ParallelPhonesQuery() {
        SelectionStorage selection = SelectionStorage.getInstance();
        atsType = Field
                .ofSingleSelectionType(selection.atsType(), 0)
                .label("Тип АТС")
                .span(6);
        ats = Field
                .ofSingleSelectionType(selection.ats(), 0)
                .label("АТС")
                .span(6);
        onlyBeneficiaries = Field
                .ofBooleanType(false)
                .label("Только льготники");
        district = Field
                .ofSingleSelectionType(selection.districts(), 0)
                .label("Район");
        sortType = Field
                .ofSingleSelectionType(selection.stringSort(), 0)
                .label("Сортировать");
        form = Form.of(Group.of(atsType, ats, onlyBeneficiaries, district, sortType));
        mapper = rs -> new PhoneOwner(
                rs.getString("last_name"),
                rs.getString("first_name"),
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
        StringBuilder builder = new StringBuilder("SELECT last_name, first_name, phone_no, street_name, house_no\n");
        builder.append("\tFROM parallel_phone_owners\n");
        if(!atsType.getSelection().equals(SelectionStorage.notChosen)) {
            String atsView = getViewNameByAtsType(atsType.getSelection());
            builder.append(String.format("\tJOIN %s USING(ats_id, serial_no)\n", atsView));
        }
        boolean atsChosen = !ats.getSelection().equals(SelectionStorage.notChosen);
        if(atsChosen) {
            String atsSerial = storage.getAtsByName(ats.getSelection()).getSerial();
            builder.append(String.format("\tWHERE (serial_no = '%s')\n", atsSerial));
        }
        boolean districtChosen = !district.getSelection().equals(SelectionStorage.notChosen);
        if(districtChosen) {
            String districtName = district.getSelection();
            if(atsChosen) {
                builder.append(String.format("\t\tAND (district_name = '%s')\n", districtName));
            } else {
                builder.append(String.format("\tWHERE (district_name = '%s')\n", districtName));
            }
        }
        if(onlyBeneficiaries.getValue()) {
            if(atsChosen || districtChosen) {
                builder.append("\t\tAND (benefit >= 0.5)\n");
            } else {
                builder.append("\tWHERE (benefit >= 0.5)\n");
            }
        }
        String sqlSort = convertStringSortType(sortType.getSelection());
        builder.append(String.format("\tORDER BY last_name %s;\n", sqlSort));
        return builder.toString();
    }

    @Override
    public Mapper<PhoneOwner> getMapper() {
        return mapper;
    }

}
