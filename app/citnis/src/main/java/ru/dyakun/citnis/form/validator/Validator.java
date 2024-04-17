package ru.dyakun.citnis.form.validator;

public interface Validator<T> {

    ValidationResult validate(T input);

}