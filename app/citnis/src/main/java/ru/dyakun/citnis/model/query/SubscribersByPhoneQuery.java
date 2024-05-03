package ru.dyakun.citnis.model.query;

import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.model.validators.CustomValidator;
import com.dlsc.formsfx.model.validators.StringLengthValidator;
import com.dlsc.formsfx.model.validators.Validator;
import ru.dyakun.citnis.model.selection.SelectionStorage;
import ru.dyakun.citnis.model.data.Subscriber;
import ru.dyakun.citnis.model.selection.SortType;

public class SubscribersByPhoneQuery extends QueryBase<Subscriber> {

    private final StringField number;
    private final SingleSelectionField<String> stringSortType;

    public SubscribersByPhoneQuery() {
        SelectionStorage selection = SelectionStorage.getInstance();

        Validator<String> numberValidator = CustomValidator.forPredicate(string -> {
            for(int i = 0; i < string.length(); i++) {
                if(!Character.isDigit(string.charAt(i))) {
                    return false;
                }
            }
            return true;
        }, "Номер состоит только из цифр");
        Validator<String> lengthValidator = StringLengthValidator.between(1, 8,
                "Номер может иметь длину от 1 до 8 цифр");

        number = Field
                .ofStringType("")
                .placeholder("1001000")
                .label("Номер телефона")
                .span(8)
                .validate(lengthValidator, numberValidator)
                .required("Не все поля заполнены");

        stringSortType = Field
                .ofSingleSelectionType(selection.stringSort(), 0)
                .label("Сортировать")
                .span(4);

        form = Form.of(Group.of(number, stringSortType));

        mapper = rs -> {
            var subscriber =  new Subscriber();
            subscriber.setLastname(rs.getString("last_name"));
            subscriber.setFirstname(rs.getString("first_name"));
            return subscriber;
        };
    }

    @Override
    public String getQuery() {
        String sqlSort = SortType.fromStringSortType(stringSortType.getSelection()).getSqlSortType();

        QueryStringBuilder query = new QueryStringBuilder()
                .select("last_name, first_name")
                .from("ats_subscribers")
                .where(1)
                .and(true, "(phone_no = %s)", number.getValue())
                .orderBy("last_name", sqlSort);
        return query.toString();
    }

    @Override
    public void onUpdate() {}

}