package ru.dyakun.citnis.gui.query;

import com.dlsc.formsfx.model.structure.*;
import ru.dyakun.citnis.model.SelectionStorage;

public class AtsSubscribersQuery implements Query {

    private final SingleSelectionField<String> ats;
    private final BooleanField onlyBeneficiaries;
    private final IntegerField ageFrom;
    private final IntegerField ageTo;
    private final SingleSelectionField<String> firstLastNameChar;
    private final SingleSelectionField<String> sortType;
    private final Form form;

    public AtsSubscribersQuery() {
        SelectionStorage selection = SelectionStorage.getInstance();
        ats = Field
                .ofSingleSelectionType(selection.ats(), 0)
                .label("АТС");
        onlyBeneficiaries = Field
                .ofBooleanType(false)
                .label("Только льготники")
                .span(4);
        ageFrom = Field
                .ofIntegerType(18)
                .label("Возраст от")
                .span(4);
        ageTo = Field.ofIntegerType(100)
                .label("до")
                .span(4);
        firstLastNameChar = Field
                .ofSingleSelectionType(selection.alphabet(), 0)
                .label("Первая буква фамилии")
                .span(8);
        sortType = Field
                .ofSingleSelectionType(selection.stringSort(), 0)
                .label("Сортировать")
                .span(4);
        form = Form.of(Group.of(ats, onlyBeneficiaries, ageFrom, ageTo , firstLastNameChar, sortType));
    }

    @Override
    public Form getForm() {
        return form;
    }

    @Override
    public String getQuery() {
        return "";
    }

}
