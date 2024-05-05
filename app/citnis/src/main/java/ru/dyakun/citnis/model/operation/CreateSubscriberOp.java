package ru.dyakun.citnis.model.operation;

import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.model.validators.Validator;
import ru.dyakun.citnis.model.selection.Gender;
import ru.dyakun.citnis.model.selection.SelectionStorage;
import ru.dyakun.citnis.model.selection.Validators;
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
        String requiredMsg = "Обязательное поле";

        Validator<String> wordValidator = Validators.onlyLettersStringValidator("Поле может содержать только буквы");

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

        age = Field
                .ofStringType("")
                .label("Возраст")
                .validate(Validators.onlyDigitsStringValidator("Поле может содержать только цифры"),
                        Validators.intRangeStringValidator(14, 130, "Возраст от 14 до 130 лет"));

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
        return "Ошибка при добавлении абонента: " + e.getMessage();
    }

    @Override
    public String getName() {
        return "Добавить";
    }

    @Override
    public void onUpdate() {}

    @Override
    public boolean needCallFunction() {
        return false;
    }

}
