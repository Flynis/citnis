package ru.dyakun.citnis.gui.query;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.SingleSelectionField;
import ru.dyakun.citnis.model.Mapper;
import ru.dyakun.citnis.model.Query;
import ru.dyakun.citnis.model.SelectionStorage;
import ru.dyakun.citnis.model.data.Ratio;

import static ru.dyakun.citnis.model.SelectionUtil.convertStringSortType;

public class SubscribersRatioQuery implements Query<Ratio> {

    private final SingleSelectionField<String> sortType;
    private final Form form;
    private final Mapper<Ratio> mapper;

    public SubscribersRatioQuery() {
        SelectionStorage selection = SelectionStorage.getInstance();
        sortType = Field
                .ofSingleSelectionType(selection.stringSort(), 0)
                .label("Сортировать");
        form = Form.of(Group.of(sortType));
        mapper = rs -> new Ratio(
                rs.getString("serial_no"),
                rs.getString("district_name"),
                rs.getInt("beneficiaries_count"),
                rs.getInt("total_subscribers")
        );
    }

    @Override
    public Form getForm() {
        return form;
    }

    @Override
    public String getQuery() {
        StringBuilder builder = new StringBuilder("SELECT serial_no, district_name, beneficiaries_count, total_subscribers\n");
        builder.append("\tFROM ats_beneficiaries_count_by_district\n");
        String sqlSort = convertStringSortType(sortType.getSelection());
        builder.append(String.format("\tORDER BY serial_no %s;\n", sqlSort));
        return builder.toString();
    }

    @Override
    public Mapper<Ratio> getMapper() {
        return mapper;
    }

}
