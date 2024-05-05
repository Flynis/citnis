package ru.dyakun.citnis.model.query;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.SingleSelectionField;
import ru.dyakun.citnis.model.data.Ats;
import ru.dyakun.citnis.model.selection.SelectionStorage;
import ru.dyakun.citnis.model.data.SubscribersStat;
import ru.dyakun.citnis.model.sql.SelectQueryBuilder;

import static ru.dyakun.citnis.model.selection.Selections.isChosen;
import static ru.dyakun.citnis.model.selection.Selections.toInt;


public class AtsSubscribersStatQuery extends QueryBase<SubscribersStat> {

    private final SingleSelectionField<String> ats;

    public AtsSubscribersStatQuery() {
        SelectionStorage selection = SelectionStorage.getInstance();

        ats = Field
                .ofSingleSelectionType(selection.ats(), 0)
                .label("АТС");


        form = Form.of(Group.of(ats));

        mapper = rs -> {
            var stat = new SubscribersStat();
            stat.setBeneficiariesCount(rs.getInt("ben_cnt"));
            stat.setTotal(rs.getInt("total_subs"));
            return stat;
        };
    }

    private int getConditionsCount() {
        int count = 0;
        count += toInt(isChosen(ats.getSelection()));
        return count;
    }

    @Override
    public String getQuery() {
        SelectionStorage storage = SelectionStorage.getInstance();

        Ats a = storage.getAtsByName(ats.getSelection());
        String atsSerial = (a != null) ? a.getSerial() : "";

        SelectQueryBuilder query = new SelectQueryBuilder()
                .select("SUM(beneficiaries_count) AS ben_cnt, SUM(total_subscribers) AS total_subs")
                .from("ats_beneficiaries_count_by_district")
                .where(getConditionsCount())
                .and(isChosen(ats.getSelection()), "(serial_no = '%s')", atsSerial);
        return query.toString();
    }

    @Override
    public void onUpdate() {
        SelectionStorage storage = SelectionStorage.getInstance();
        ats.items(storage.ats());
    }

}
