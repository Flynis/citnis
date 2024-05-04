package ru.dyakun.citnis.model.query;

import com.dlsc.formsfx.model.structure.*;
import ru.dyakun.citnis.model.data.Ats;
import ru.dyakun.citnis.model.selection.SelectionStorage;
import ru.dyakun.citnis.model.data.PhoneOwner;
import ru.dyakun.citnis.model.selection.SortType;

import static ru.dyakun.citnis.model.selection.Selections.*;

public class ParallelPhoneOwnersListQuery extends QueryBase<PhoneOwner> {

    private final SingleSelectionField<String> ats;
    private final SingleSelectionField<String> district;
    private final BooleanField onlyBeneficiaries;
    private final SingleSelectionField<String> stringSortType;

    public ParallelPhoneOwnersListQuery() {
        SelectionStorage selection = SelectionStorage.getInstance();

        ats = Field
                .ofSingleSelectionType(selection.ats(), 0)
                .label("АТС")
                .span(6);

        district = Field
                .ofSingleSelectionType(selection.districts(), 0)
                .label("Район")
                .span(6);

        onlyBeneficiaries = Field
                .ofBooleanType(false)
                .label("Только льготники");

        stringSortType = Field
                .ofSingleSelectionType(selection.stringSort(), 0)
                .label("Сортировать");

        form = Form.of(Group.of(ats, district, onlyBeneficiaries, stringSortType));

        mapper = rs -> {
            var owner =  new PhoneOwner();
            owner.setLastname(rs.getString("last_name"));
            owner.setFirstname(rs.getString("first_name"));
            owner.setNumber(rs.getInt("phone_no"));
            return owner;
        };
    }

    private int getConditionsCount() {
        int count = 0;
        count += toInt(isChosen(ats.getSelection()));
        count += toInt(isChosen(district.getSelection()));
        count += toInt(onlyBeneficiaries.getValue());
        return count;
    }

    @Override
    public String getQuery() {
        SelectionStorage storage = SelectionStorage.getInstance();

        Ats a = storage.getAtsByName(ats.getSelection());
        String atsSerial = (a != null) ? a.getSerial() : "";
        String sqlSort = SortType.fromStringSortType(stringSortType.getSelection()).getSqlSortType();

        QueryStringBuilder query = new QueryStringBuilder()
                .select("last_name, first_name, phone_no")
                .from("parallel_phone_owners")
                .where(getConditionsCount())
                .and(isChosen(ats.getSelection()), "(serial_no = '%s')", atsSerial)
                .and(isChosen(district.getSelection()), "(district_name = '%s')", district.getSelection())
                .and(onlyBeneficiaries.getValue(), "(benefit >= 0.5)")
                .orderBy("last_name", sqlSort);
        return query.toString();
    }

    @Override
    public void onUpdate() {
        SelectionStorage storage = SelectionStorage.getInstance();
        ats.items(storage.ats());
        district.items(storage.districts());
    }

}
