package ru.dyakun.citnis.model.query;

import com.dlsc.formsfx.model.structure.*;
import ru.dyakun.citnis.model.data.Ats;
import ru.dyakun.citnis.model.selection.SelectionStorage;
import ru.dyakun.citnis.model.data.PhoneNumber;
import ru.dyakun.citnis.model.selection.SortType;
import ru.dyakun.citnis.model.sql.SelectQueryBuilder;

import static ru.dyakun.citnis.model.selection.Selections.*;

public class FreePhoneNumbersQuery extends QueryBase<PhoneNumber> {

    private final SingleSelectionField<String> ats;
    private final SingleSelectionField<String> district;
    private final SingleSelectionField<String> numberSortType;

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
            phone.setHouse(rs.getInt("house_no"));
            return phone;
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
                .from("free_phone_numbers")
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
