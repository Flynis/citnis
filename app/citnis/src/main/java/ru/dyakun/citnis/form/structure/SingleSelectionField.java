package ru.dyakun.citnis.form.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import ru.dyakun.citnis.form.validator.*;

public class SingleSelectionField<V> extends SelectionField<V, SingleSelectionField<V>> {

    protected final ObjectProperty<V> selection = new SimpleObjectProperty<>();

    protected final List<Validator<V>> validators = new ArrayList<>();

    protected SingleSelectionField(ListProperty<V> items, int selection) {
        super(items);

        if (selection < items.size() && selection >= 0) {
            this.selection.set(this.items.get(selection));
        }

        this.selection.addListener((observable, oldValue, newValue) -> validate());

        items.addListener((observable, oldValue, newValue) -> {
            this.selection.setValue(null);
        });
    }

    public SingleSelectionField<V> items(List<V> newValue, int newSelection) {
        items.setAll(newValue);
        if (newSelection != -1) {
            this.selection.setValue(items.get(newSelection));
        }
        return this;
    }

    public SingleSelectionField<V> items(List<V> newValue) {
        return this.items(newValue, -1);
    }

    @SafeVarargs
    public final SingleSelectionField<V> validate(Validator<V>... newValue) {
        validators.clear();
        Collections.addAll(validators, newValue);
        validate();
        return this;
    }

    public SingleSelectionField<V> select(int index) {
        if (index == -1) {
            selection.setValue(null);
        } else if (index < items.size() && index > -1 && (selection.get() == null || (selection.get() != null && !selection.get().equals(items.get(index))))) {
            selection.setValue(items.get(index));
        }

        return this;
    }

    public SingleSelectionField<V> deselect() {
        if (selection.get() != null) {
            selection.setValue(null);
        }

        return this;
    }


    public SingleSelectionField<V> bind(ListProperty<V> itemsBinding, ObjectProperty<V> selectionBinding) {
        items.bindBidirectional(itemsBinding);
        selection.bindBidirectional(selectionBinding);

        return this;
    }

    public boolean validate() {

        // Check all validation rules and collect any error messages.

        List<String> errorMessages = validators.stream()
                .map(v -> v.validate(selection.getValue()))
                .filter(r -> !r.getResult())
                .map(ValidationResult::getErrorMessage)
                .collect(Collectors.toList());

        return super.validate(errorMessages);
    }

    public V getSelection() {
        return selection.get();
    }

    public ObjectProperty<V> selectionProperty() {
        return selection;
    }

}

