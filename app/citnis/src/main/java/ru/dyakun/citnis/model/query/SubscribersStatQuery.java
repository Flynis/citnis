package ru.dyakun.citnis.model.query;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.SingleSelectionField;
import ru.dyakun.citnis.model.data.Ats;
import ru.dyakun.citnis.model.selection.SelectionStorage;
import ru.dyakun.citnis.model.data.SubscribersStat;
import ru.dyakun.citnis.model.selection.SortType;

import static ru.dyakun.citnis.model.selection.Selections.isChosen;
import static ru.dyakun.citnis.model.selection.Selections.toInt;


public class SubscribersStatQuery extends QueryBase<SubscribersStat> {

    private final SingleSelectionField<String> ats;
    private final SingleSelectionField<String> district;
    private final SingleSelectionField<String> stringSortType;

    public SubscribersStatQuery() {
        SelectionStorage selection = SelectionStorage.getInstance();

        ats = Field
                .ofSingleSelectionType(selection.ats(), 0)
                .label("АТС");

        district = Field
                .ofSingleSelectionType(selection.districts(), 0)
                .label("Район");

        stringSortType = Field
                .ofSingleSelectionType(selection.stringSort(), 0)
                .label("Сортировать");

        form = Form.of(Group.of(ats, district, stringSortType));

        mapper = rs -> {
            var stat = new SubscribersStat();
            stat.setSerial(rs.getString("serial_no"));
            stat.setDistrict(rs.getString("district_name"));
            stat.setBeneficiariesCount(rs.getInt("beneficiaries_count"));
            stat.setTotal(rs.getInt("total_subscribers"));
            return stat;
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
        String sqlSort = SortType.fromStringSortType(stringSortType.getSelection()).getSqlSortType();

        // TODO group by
        QueryStringBuilder query = new QueryStringBuilder()
                .select("serial_no, district_name, beneficiaries_count, total_subscribers")
                .from("ats_beneficiaries_count_by_district")
                .where(getConditionsCount())
                .and(isChosen(ats.getSelection()), "(serial_no = '%s')", atsSerial)
                .and(isChosen(district.getSelection()), "(district_name = '%s')", district.getSelection())
                .orderBy("serial_no", sqlSort);
        return query.toString();
    }

    @Override
    public void onUpdate() {
        SelectionStorage storage = SelectionStorage.getInstance();
        ats.items(storage.ats());
        district.items(storage.districts());
    }

}
