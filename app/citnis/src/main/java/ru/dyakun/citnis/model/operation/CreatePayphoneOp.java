package ru.dyakun.citnis.model.operation;

import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.model.validators.StringLengthValidator;
import com.dlsc.formsfx.model.validators.Validator;
import ru.dyakun.citnis.model.selection.SelectionStorage;
import ru.dyakun.citnis.model.selection.Validators;

public class CreatePayphoneOp extends OperationBase {

    private final SingleSelectionField<String> ats;
    private final StringField number;
    private final SingleSelectionField<String> street;
    private final StringField house;

    public CreatePayphoneOp() {
        SelectionStorage selection = SelectionStorage.getInstance();

        Validator<String> lengthValidator = StringLengthValidator.between(1, 8,
                "Номер может иметь длину от 1 до 8 цифр");
        String requiredMsg = "Обязательное поле";
        Validator<String> houseLengthValidator = StringLengthValidator.between(0, 3,
                "Номер дома не длинее 3 цифр");

        ats = Field
                .ofSingleSelectionType(selection.cityAts(), 0)
                .label("АТС")
                .required(requiredMsg)
                .validate(Validators.selectionChooseValidator("Атс не выбрана"));

        number = Field
                .ofStringType("")
                .placeholder("1001000")
                .label("Номер телефона")
                .validate(lengthValidator, Validators.onlyDigitsStringValidator("Номер состоит только из цифр"))
                .required(requiredMsg);

        street = Field
                .ofSingleSelectionType(selection.streets(), 0)
                .label("Улица")
                .required(requiredMsg)
                .validate(Validators.selectionChooseValidator("Улица не выбрана"));

        house = Field.ofStringType("")
                .label("Дом")
                .validate(houseLengthValidator, Validators.onlyDigitsStringValidator("Номер дома состоит только из цифр"))
                .required(requiredMsg);

        form = Form.of(Group.of(ats, number, street, house));
    }

    @Override
    public String getQuery() {
        SelectionStorage storage = SelectionStorage.getInstance();

        String atsSerial = storage.getAtsByName(ats.getSelection()).getSerial();

        return String.format(
            """
            SELECT create_payphone(
                '%s',
                %s,
                '%s',
                %s);
            """,
            atsSerial,
            number.getValue(),
            street.getSelection(),
            house.getValue());
    }

    @Override
    public void onUpdate() {
        SelectionStorage selection = SelectionStorage.getInstance();
        ats.items(selection.cityAts());
        street.items(selection.streets());
    }

    @Override
    public String getSuccessMessage() {
        return "Таксофон успешно добавлен";
    }

    @Override
    public String getErrorMessage(Throwable e) {
        return "Ошибка при добавлении таксофона: " + e.getMessage();
    }

    @Override
    public String getName() {
        return "Добавить";
    }

}
