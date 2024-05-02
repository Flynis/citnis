package ru.dyakun.citnis.gui.query;

import com.dlsc.formsfx.model.structure.*;
import ru.dyakun.citnis.model.Mapper;
import ru.dyakun.citnis.model.query.Query;
import ru.dyakun.citnis.model.data.Debtor;
import ru.dyakun.citnis.model.selection.SelectionStorage;
import ru.dyakun.citnis.model.selection.Selections;
import ru.dyakun.citnis.model.selection.SortType;

public class DebtorsQuery implements Query<Debtor> {

    private final SingleSelectionField<String> ats;
    private final SingleSelectionField<String> district;
    private final SingleSelectionField<String> debtDuration;
    private final BooleanField subscriptionFee;
    private final BooleanField intercity;
    private final SingleSelectionField<String> stringSortType;

    private final Form form;
    private final Mapper<Debtor> mapper;

    public DebtorsQuery() {
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
            return debtor;
        };
    }

    @Override
    public Form getForm() {
        return form;
    }

    private boolean needWhereClause() {
        return Selections.isChosen(ats.getSelection()) ||
                Selections.isChosen(district.getSelection()) ||
                Selections.isAnyDuration(debtDuration.getSelection()) ||
                subscriptionFee.getValue() ||
                intercity.getValue();
    }

    @Override
    public String getQuery() {
        SelectionStorage storage = SelectionStorage.getInstance();
        StringBuilder builder = new StringBuilder("SELECT last_name, first_name\n");
        builder.append("\tFROM debtors\n");

        builder.append(String.format("\tWHERE (age >= %d) AND (age <= %d)\n", ageFrom.getValue(), ageTo.getValue()));
        if(!ats.getSelection().equals(SelectionStorage.notChosen)) {
            String atsSerial = storage.getAtsByName(ats.getSelection()).getSerial();
            builder.append(String.format("\t\tAND (serial_no = '%s')\n", atsSerial));
        }
        if(onlyBeneficiaries.getValue()) {
            builder.append("\t\tAND (benefit >= 0.5)\n");
        }
        String sqlSort = SortType.fromStringSortType(stringSortType.getSelection()).getSqlSortType();
        builder.append(String.format("\tORDER BY last_name %s;\n", sqlSort));
        return builder.toString();
    }

    @Override
    public Mapper<Debtor> getMapper() {
        return mapper;
    }

}
