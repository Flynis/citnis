package ru.dyakun.citnis.form.validator;

public class ValidationResult {

    private final boolean result;
    private final String errorMessage;

    ValidationResult(boolean result, String errorMessage) {
        this.result = result;
        this.errorMessage = !result ? errorMessage : null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean getResult() {
        return result;
    }

}