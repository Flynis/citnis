package ru.dyakun.citnis.model.operation;

import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.model.validators.StringLengthValidator;
import com.dlsc.formsfx.model.validators.Validator;
import ru.dyakun.citnis.model.selection.PhoneType;
import ru.dyakun.citnis.model.selection.SelectionStorage;
import ru.dyakun.citnis.model.selection.Validators;

public class CreatePhoneNumberOp extends OperationBase {

    private final SingleSelectionField<String> ats;
    private final StringField number;
    private final SingleSelectionField<String> street;
    private final StringField house;
    private final SingleSelectionField<String> phoneType;

    public CreatePhoneNumberOp() {
        SelectionStorage selection = SelectionStorage.getInstance();

        Validator<String> lengthValidator = StringLengthValidator.between(1, 8,
                "Номер может иметь длину от 1 до 8 цифр");
        String requiredMsg = "Обязательное поле";
        Validator<String> houseLengthValidator = StringLengthValidator.between(0, 3,
                "Номер дома не длинее 3 цифр");

        ats = Field
                .ofSingleSelectionType(selection.ats(), 0)
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

        phoneType = Field
                .ofSingleSelectionType(selection.phoneTypes(), 0)
                .label("Тип телефона");

        form = Form.of(Group.of(ats, number, street, house, phoneType));
    }

    @Override
    public String getQuery() {
        SelectionStorage storage = SelectionStorage.getInstance();

        String atsSerial = storage.getAtsByName(ats.getSelection()).getSerial();
        String type = PhoneType.fromLabel(phoneType.getSelection()).name();

        return String.format(
            """
            SELECT create_phone_number(
                '%s',
                %s,
                '%s',
                %s,
                '%s');
            """,
            atsSerial,
            number.getValue(),
            street.getSelection(),
            house.getValue(),
            type);
    }

    @Override
    public void onUpdate() {
        SelectionStorage selection = SelectionStorage.getInstance();
        ats.items(selection.ats());
        street.items(selection.streets());
    }

    @Override
    public String getSuccessMessage() {
        return "Номер телефона успешно добавлен";
    }

    @Override
    public String getErrorMessage(Throwable e) {
        return "Ошибка при добавлении номера телефона: " + e.getMessage();
    }

    @Override
    public String getName() {
        return "Добавить";
    }

}
