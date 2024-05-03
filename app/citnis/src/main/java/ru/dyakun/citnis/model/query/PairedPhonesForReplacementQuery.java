package ru.dyakun.citnis.model.query;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.SingleSelectionField;
import ru.dyakun.citnis.model.selection.SelectionStorage;
import ru.dyakun.citnis.model.data.PhoneNumber;
import ru.dyakun.citnis.model.selection.SortType;

public class PairedPhonesForReplacementQuery extends QueryBase<PhoneNumber> {

    private final SingleSelectionField<String> numberSortType;

    public PairedPhonesForReplacementQuery() {
        SelectionStorage selection = SelectionStorage.getInstance();
        numberSortType = Field
                .ofSingleSelectionType(selection.numberSort(), 0)
                .label("Сортировать");

        form = Form.of(Group.of(numberSortType));

        mapper = rs -> {
            var phone = new PhoneNumber();
            phone.setNumber(rs.getInt("phone_no"));
            phone.setStreet(rs.getString("street_name"));
            phone.setNumber(rs.getInt("house_no"));
            return phone;
        };
    }

    @Override
    public String getQuery() {
        String sqlSort = SortType.fromNumberSortType(numberSortType.getSelection()).getSqlSortType();

        QueryStringBuilder query = new QueryStringBuilder()
                .select("phone_no, street_name, house_no")
                .from("paired_phones_for_replacement")
                .orderBy("phone_no", sqlSort);
        return query.toString();
    }

    @Override
    public void onUpdate() {}

}
