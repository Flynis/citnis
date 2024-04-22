package ru.dyakun.citnis.gui.query;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.SingleSelectionField;
import ru.dyakun.citnis.model.Mapper;
import ru.dyakun.citnis.model.Query;
import ru.dyakun.citnis.model.SelectionStorage;
import ru.dyakun.citnis.model.data.PhoneNumber;

import static ru.dyakun.citnis.model.SelectionUtil.convertNumberSortType;

public class PairedPhonesForReplacementQuery implements Query<PhoneNumber> {

    private final SingleSelectionField<String> sortType;
    private final Form form;
    private final Mapper<PhoneNumber> mapper;

    public PairedPhonesForReplacementQuery() {
        SelectionStorage selection = SelectionStorage.getInstance();
        sortType = Field
                .ofSingleSelectionType(selection.numberSort(), 0)
                .label("Сортировать");
        form = Form.of(Group.of(sortType));
        mapper = rs -> new PhoneNumber(
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
        StringBuilder builder = new StringBuilder("SELECT phone_no, street_name, house_no\n");
        builder.append("\tFROM paired_phones_for_replacement\n");
        String sqlSort = convertNumberSortType(sortType.getSelection());
        builder.append(String.format("\tORDER BY phone_no %s;\n", sqlSort));
        return builder.toString();
    }

    @Override
    public Mapper<PhoneNumber> getMapper() {
        return mapper;
    }

}
