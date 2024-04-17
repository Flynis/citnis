package ru.dyakun.citnis.form.structure;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class BooleanField extends DataField<BooleanProperty, Boolean, BooleanField> {

    protected BooleanField(SimpleBooleanProperty valueProperty) {
        super(valueProperty);

        stringConverter = new AbstractStringConverter<>() {
            @Override
            public Boolean fromString(String string) {
                return Boolean.parseBoolean(string);
            }
        };

        userInput.set(stringConverter.toString(value.getValue()));
    }

}

