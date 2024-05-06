package ru.dyakun.citnis.model.operation;

import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.model.validators.StringLengthValidator;
import com.dlsc.formsfx.model.validators.Validator;
import ru.dyakun.citnis.model.selection.SelectionStorage;
import ru.dyakun.citnis.model.selection.Validators;

public class DisableIntercityCallsOp extends OperationBase {

    private final SingleSelectionField<String> ats;
    private final StringField number;
    private final StringField lastname;
    private final StringField firstname;

    public DisableIntercityCallsOp() {
        SelectionStorage selection = SelectionStorage.getInstance();

        String requiredMsg = "Обязательное поле";
        Validator<String> wordValidator = Validators.onlyLettersStringValidator("Поле может содержать только буквы");
        Validator<String> lengthValidator = StringLengthValidator.between(1, 8,
                "Номер может иметь длину от 1 до 8 цифр");

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

        form = Form.of(Group.of(ats, number, lastname, firstname));
    }

    @Override
    public String getQuery() {
        SelectionStorage storage = SelectionStorage.getInstance();

        String atsSerial = storage.getAtsByName(ats.getSelection()).getSerial();

        return String.format(
                """
                SELECT disable_intercity_calls(
                    '%s',
                    %s,
                    '%s',
                    '%s');
                """,
                atsSerial,
                number.getValue(),
                lastname.getValue(),
                firstname.getValue());
    }

    @Override
    public String getSuccessMessage() {
        return "Междугородние звонки успешно отключены";
    }

    @Override
    public String getErrorMessage(Throwable e) {
        return "Ошибка при отключении: " + e.getMessage();
    }

    @Override
    public String getName() {
        return "Отключить";
    }

    @Override
    public void onUpdate() {
        SelectionStorage selection = SelectionStorage.getInstance();
        ats.items(selection.ats());
    }

}
