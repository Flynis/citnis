package ru.dyakun.citnis.model.query;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.SingleSelectionField;
import ru.dyakun.citnis.model.data.SubscribersStat;
import ru.dyakun.citnis.model.selection.SelectionStorage;
import ru.dyakun.citnis.model.sql.SelectQueryBuilder;

import static ru.dyakun.citnis.model.selection.Selections.isChosen;
import static ru.dyakun.citnis.model.selection.Selections.toInt;

public class DistrictSubscribersStatQuery extends QueryBase<SubscribersStat> {

    private final SingleSelectionField<String> district;

    public DistrictSubscribersStatQuery() {
        SelectionStorage selection = SelectionStorage.getInstance();

        district = Field
                .ofSingleSelectionType(selection.districts(), 0)
                .label("Район");

        form = Form.of(Group.of(district));

        mapper = rs -> {
            var stat = new SubscribersStat();
            stat.setBeneficiariesCount(rs.getInt("ben_cnt"));
            stat.setTotal(rs.getInt("total_subs"));
            return stat;
        };
    }

    private int getConditionsCount() {
        int count = 0;
        count += toInt(isChosen(district.getSelection()));
        return count;
    }

    @Override
    public String getQuery() {
        SelectQueryBuilder query = new SelectQueryBuilder()
                .select("SUM(beneficiaries_count) AS ben_cnt, SUM(total_subscribers) AS total_subs")
                .from("ats_beneficiaries_count_by_district")
                .where(getConditionsCount())
                .and(isChosen(district.getSelection()), "(district_name = '%s')", district.getSelection());
        return query.toString();
    }

    @Override
    public void onUpdate() {
        SelectionStorage storage = SelectionStorage.getInstance();
        district.items(storage.districts());
    }

}
