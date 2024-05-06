package ru.dyakun.citnis.model.query;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.SingleSelectionField;
import ru.dyakun.citnis.model.data.Ats;
import ru.dyakun.citnis.model.selection.SelectionStorage;
import ru.dyakun.citnis.model.data.Payphone;
import ru.dyakun.citnis.model.selection.SortType;
import ru.dyakun.citnis.model.sql.SelectQueryBuilder;

import static ru.dyakun.citnis.model.selection.Selections.*;

public class PayphonesListQuery extends QueryBase<Payphone> {

    private final SingleSelectionField<String> ats;
    private final SingleSelectionField<String> district;
    private final SingleSelectionField<String> numberSortType;

    public PayphonesListQuery() {
        SelectionStorage selection = SelectionStorage.getInstance();

        ats = Field
                .ofSingleSelectionType(selection.cityAts(), 0)
                .label("АТС");

        district = Field
                .ofSingleSelectionType(selection.districts(), 0)
                .label("Район");

        numberSortType = Field
                .ofSingleSelectionType(selection.numberSort(), 0)
                .label("Сортировать");

        form = Form.of(Group.of(ats, district, numberSortType));

        mapper = rs -> {
            var payphone = new Payphone();
            payphone.setNumber(rs.getInt("phone_no"));
            payphone.setStreet(rs.getString("street_name"));
            payphone.setHouse(rs.getInt("house_no"));
            return payphone;
        };
    }

    private int getConditionsCount() {
        int count = 0;
        count += toInt(isChosen(ats.getSelection()));
        count += toInt(isChosen(district.getSelection()));
        return count;
    }

    @Override
    public String getQuery() {
        SelectionStorage storage = SelectionStorage.getInstance();

        Ats a = storage.getAtsByName(ats.getSelection());
        String atsSerial = (a != null) ? a.getSerial() : "";
        SortType sortType = SortType.fromNumberSortType(numberSortType.getSelection());

        SelectQueryBuilder query = new SelectQueryBuilder()
                .select("phone_no, street_name, house_no")
                .from("payphones_by_ats")
                .where(getConditionsCount())
                .and(isChosen(ats.getSelection()), "(serial_no = '%s')", atsSerial)
                .and(isChosen(district.getSelection()), "(district_name = '%s')", district.getSelection())
                .orderBy("phone_no", sortType);
        return query.toString();
    }

    @Override
    public void onUpdate() {
        SelectionStorage storage = SelectionStorage.getInstance();
        ats.items(storage.ats());
        district.items(storage.districts());
    }

}
