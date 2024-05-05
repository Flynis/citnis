package ru.dyakun.citnis.model.operation;

import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.model.validators.StringLengthValidator;
import com.dlsc.formsfx.model.validators.Validator;
import ru.dyakun.citnis.model.selection.PhoneType;
import ru.dyakun.citnis.model.selection.SelectionStorage;
import ru.dyakun.citnis.model.selection.Validators;

public class ChangePhoneTypeOp extends OperationBase {

    private final SingleSelectionField<String> ats;
    private final StringField number;
    private final SingleSelectionField<String> phoneType;

    public ChangePhoneTypeOp() {
        SelectionStorage selection = SelectionStorage.getInstance();

        Validator<String> lengthValidator = StringLengthValidator.between(1, 8,
                "Номер может иметь длину от 1 до 8 цифр");
        String requiredMsg = "Обязательное поле";

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

        phoneType = Field
                .ofSingleSelectionType(selection.phoneTypes(), 0)
                .label("Новый тип");

        form = Form.of(Group.of(ats, number, phoneType));
    }

    @Override
    public String getQuery() {
        SelectionStorage storage = SelectionStorage.getInstance();

        String atsSerial = storage.getAtsByName(ats.getSelection()).getSerial();
        String type = PhoneType.fromLabel(phoneType.getSelection()).name();

        return String.format(
            """
            SELECT change_phone_type(
                '%s',
                %s,
                '%s');
            """,
            atsSerial,
            number.getValue(),
            type);
    }

    @Override
    public void onUpdate() {
        SelectionStorage selection = SelectionStorage.getInstance();
        ats.items(selection.ats());
    }

    @Override
    public String getSuccessMessage() {
        return "Тип телефона успешно изменен";
    }

    @Override
    public String getErrorMessage(Throwable e) {
        return "Ошибка при изменении типа телефона: " + e.getMessage();
    }

    @Override
    public String getName() {
        return "Изменить";
    }

}
