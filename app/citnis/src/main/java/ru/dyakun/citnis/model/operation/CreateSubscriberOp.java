package ru.dyakun.citnis.model.operation;

import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.model.validators.CustomValidator;
import com.dlsc.formsfx.model.validators.Validator;
import ru.dyakun.citnis.model.selection.Gender;
import ru.dyakun.citnis.model.selection.SelectionStorage;
import ru.dyakun.citnis.model.sql.InsertQueryBuilder;

public class CreateSubscriberOp extends OperationBase {

    private final StringField lastname;
    private final StringField firstname;
    private final StringField surname;
    private final SingleSelectionField<String> gender;
    private final StringField age;
    private final BooleanField isBeneficiary;

    public CreateSubscriberOp() {
        SelectionStorage selection = SelectionStorage.getInstance();
        String requiredMsg = "Не все поля заполнены";

        Validator<String> wordValidator = CustomValidator.forPredicate(string -> {
            for(int i = 0; i < string.length(); i++) {
                if(!Character.isAlphabetic(string.charAt(i))) {
                    return false;
                }
            }
            return true;
        }, "Поле может содержать только буквы");
        lastname = Field
                .ofStringType("")
                .required(requiredMsg)
                .label("Фамилия")
                .validate(wordValidator);

        firstname = Field
                .ofStringType("")
                .required(requiredMsg)
                .label("Имя")
                .validate(wordValidator);

        surname = Field
                .ofStringType("")
                .label("Отчество")
                .validate(wordValidator);

        gender = Field
                .ofSingleSelectionType(selection.genders(), 0)
                .label("Пол");

        Validator<String> numberValidator = CustomValidator.forPredicate(string -> {
            for(int i = 0; i < string.length(); i++) {
                if(!Character.isDigit(string.charAt(i))) {
                    return false;
                }
            }
            return true;
        }, "Поле может содержать только цифры");
        Validator<String> ageValidator = CustomValidator.forPredicate(string -> {
            if(string.isEmpty()) {
                return true;
            }
            try {
                int val = Integer.parseInt(string);
                return val >= 14 && val <= 130;
            } catch (NumberFormatException e) {
                return false;
            }
        }, "Возраст от 14 до 130 лет");
        age = Field
                .ofStringType("")
                .label("Возраст")
                .validate(numberValidator, ageValidator);

        isBeneficiary = Field
                .ofBooleanType(false)
                .label("Льготник");

        form = Form.of(Group.of(lastname, firstname, surname, gender, age, isBeneficiary));
    }

    @Override
    public String getQuery() {
        String genderStr = Gender.fromLabel(gender.getSelection()).getFirstLetter();
        double benefit = isBeneficiary.getValue() ? 0.5 : 0;

        InsertQueryBuilder query = new InsertQueryBuilder()
                .insertInto("subscribers")
                .column(true, "last_name", lastname.getValue())
                .column(true, "first_name", firstname.getValue())
                .column(!surname.getValue().isEmpty(), "surname", surname.getValue())
                .column(true, "gender", genderStr)
                .column(!age.getValue().isEmpty(), "age", age.getValue())
                .column(isBeneficiary.getValue(), "benefit", benefit);
        return query.toString();
    }

    @Override
    public String getSuccessMessage() {
        return "Абонент успешно добавлен";
    }

    @Override
    public String getErrorMessage(Throwable e) {
        return "Произошла ошибка при добавлении: " + e.getMessage();
    }

    @Override
    public String getName() {
        return "Добавить";
    }

    @Override
    public void onUpdate() {}

}
