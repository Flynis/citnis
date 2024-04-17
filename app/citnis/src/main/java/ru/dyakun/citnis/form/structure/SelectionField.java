package ru.dyakun.citnis.form.structure;

import javafx.beans.property.ListProperty;
import javafx.collections.ObservableList;

import java.util.List;

public abstract class SelectionField<V, F extends SelectionField<V, F>> extends Field<F> {

    protected final ListProperty<V> items;

    protected SelectionField(ListProperty<V> items) {
        this.items = items;
    }

    protected boolean validate(List<String> errorMessages) {
        if (!errorMessages.isEmpty()) {
            errorMessage.set(errorMessages.get(0));
            valid.set(false);
            return false;
        }

        valid.set(true);
        return true;
    }

    public ObservableList<V> getItems() {
        return items.get();
    }

    public ListProperty<V> itemsProperty() {
        return items;
    }

}