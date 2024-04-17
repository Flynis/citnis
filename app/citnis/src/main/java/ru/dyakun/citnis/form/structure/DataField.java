package ru.dyakun.citnis.form.structure;

import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.StringConverter;
import ru.dyakun.citnis.form.validator.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class DataField<P extends Property, V, F extends Field<F>> extends Field<F> {

    protected final P value;
    protected final StringProperty userInput = new SimpleStringProperty("");

    protected final List<Validator<V>> validators = new ArrayList<>();

    protected static abstract class AbstractStringConverter<V> extends StringConverter<V> {
        @Override
        public String toString(V object) {
            return String.valueOf(object);
        }
    }

    protected StringConverter<V> stringConverter = new AbstractStringConverter<>() {
        @Override
        public V fromString(String string) {
            return null;
        }
    };

    private final InvalidationListener externalBindingListener = (observable) -> {
        userInput.setValue(stringConverter.toString((V) ((P) observable).getValue()));
    };

    protected DataField(P valueProperty) {
        value = valueProperty;
        userInput.addListener((observable, oldValue, newValue) -> {
            if (validate()) {
                value.setValue(stringConverter.fromString(newValue));
            }
        });
    }

    @SafeVarargs
    public final F validate(Validator<V>... newValue) {
        validators.clear();
        Collections.addAll(validators, newValue);
        validate();
        return (F) this;
    }

    public F bind(P binding) {
        value.bind(binding);
        binding.addListener(externalBindingListener);
        return (F) this;
    }

    public boolean validate() {
        String newValue = userInput.getValue();

        V transformedValue = stringConverter.fromString(newValue);

        List<String> errorMessages = validators.stream()
                .map(v -> v.validate(transformedValue))
                .filter(r -> !r.getResult())
                .map(ValidationResult::getErrorMessage)
                .toList();

        if (!errorMessages.isEmpty()) {
            errorMessage.set(errorMessages.get(0));
            valid.set(false);
            return false;
        }

        valid.set(true);
        return true;
    }

    public String getUserInput() {
        return userInput.get();
    }

    public StringProperty userInputProperty() {
        return userInput;
    }

    public V getValue() {
        return (V) value.getValue();
    }

    public P valueProperty() {
        return value;
    }

}
