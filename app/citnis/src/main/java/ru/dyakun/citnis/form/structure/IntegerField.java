package ru.dyakun.citnis.form.structure;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class IntegerField extends DataField<IntegerProperty, Integer, IntegerField> {

    protected IntegerField(SimpleIntegerProperty valueProperty) {
        super(valueProperty);

        stringConverter = new AbstractStringConverter<>() {
            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }
        };

        userInput.set(stringConverter.toString(value.getValue()));
    }

}
