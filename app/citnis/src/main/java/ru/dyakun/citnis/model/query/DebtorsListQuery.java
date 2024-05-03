package ru.dyakun.citnis.model.query;

import com.dlsc.formsfx.model.structure.*;
import ru.dyakun.citnis.model.data.Debtor;
import ru.dyakun.citnis.model.selection.Duration;
import ru.dyakun.citnis.model.selection.SelectionStorage;
import ru.dyakun.citnis.model.selection.SortType;

import static ru.dyakun.citnis.model.selection.Selections.*;

public class DebtorsListQuery extends QueryBase<Debtor> {

    private final SingleSelectionField<String> ats;
    private final SingleSelectionField<String> district;
    private final SingleSelectionField<String> debtDuration;
    private final BooleanField subscriptionFee;
    private final BooleanField intercity;
    private final SingleSelectionField<String> stringSortType;

    public DebtorsListQuery() {
        SelectionStorage selection = SelectionStorage.getInstance();

        ats = Field
                .ofSingleSelectionType(selection.ats(), 0)
                .label("АТС");

        district = Field
                .ofSingleSelectionType(selection.districts(), 0)
                .label("Район");

        debtDuration = Field
                .ofSingleSelectionType(selection.durations(), 0)
                .label("Продолжительность задолжности");

        subscriptionFee = Field.ofBooleanType(false)
                .label("За абонентскую плату");

        intercity = Field.ofBooleanType(false)
                .label("За межгород");

        stringSortType = Field
                .ofSingleSelectionType(selection.stringSort(), 0)
                .label("Сортировать")
                .span(4);

        form = Form.of(Group.of(ats, district, debtDuration, subscriptionFee , intercity, stringSortType));

        mapper = rs -> {
            var debtor =  new Debtor();
            debtor.setLastname(rs.getString("last_name"));
            debtor.setFirstname(rs.getString("first_name"));
            debtor.setDebt(rs.getDouble("debt_amount"));
            return debtor;
        };
    }

    private int getConditionsCount() {
        int count = 0;
        count += toInt(isChosen(ats.getSelection()));
        count += toInt(isChosen(district.getSelection()));
        count += toInt(!isAnyDuration(debtDuration.getSelection()));
        count += toInt(subscriptionFee.getValue());
        count += toInt(intercity.getValue());
        return count;
    }

    @Override
    public String getQuery() {
        SelectionStorage storage = SelectionStorage.getInstance();

        String atsSerial = storage.getAtsByName(ats.getSelection()).getSerial();
        String sqlSort = SortType.fromStringSortType(stringSortType.getSelection()).getSqlSortType();
        int duration = Duration.asInt(Duration.fromLabel(debtDuration.getSelection()));

        QueryStringBuilder query = new QueryStringBuilder()
                .select("last_name, first_name, debt_amount")
                .from("debtors")
                .where(getConditionsCount())
                .and(isChosen(ats.getSelection()), "(serial_no = '%s')", atsSerial)
                .and(isChosen(district.getSelection()), "(district_name = '%s')", district.getSelection())
                .and(!isAnyDuration(debtDuration.getSelection()), "(debt_duration >= %d)", duration)
                .and(subscriptionFee.getValue(), "(service_name = 'Звонок')")
                .and(intercity.getValue(), "(service_name = 'Междугородний звонок')")
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
