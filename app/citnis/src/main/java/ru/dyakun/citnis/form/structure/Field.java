package ru.dyakun.citnis.form.structure;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public abstract class Field <F extends Field<F>> {

    protected final ListProperty<String> styleClass = new SimpleListProperty<>(FXCollections.observableArrayList());

    protected final StringProperty label = new SimpleStringProperty("");
    protected final StringProperty tooltip = new SimpleStringProperty("");
    protected final StringProperty placeholder = new SimpleStringProperty("");
    protected final BooleanProperty editable = new SimpleBooleanProperty(true);

    protected final StringProperty errorMessage = new SimpleStringProperty("");
    protected final BooleanProperty valid = new SimpleBooleanProperty(true);

    public F styleClass(String... newValue) {
        styleClass.setAll(newValue);
        return (F) this;
    }

    public F label(String newValue) {
        label.set(newValue);
        return (F) this;
    }

    public F tooltip(String newValue) {
        tooltip.set(newValue);
        return (F) this;
    }

    public F placeholder(String newValue) {
        placeholder.set(newValue);
        return (F) this;
    }

    public F editable(boolean newValue) {
        editable.set(newValue);
        return (F) this;
    }

    protected abstract boolean validate();

    public String getPlaceholder() {
        return placeholder.get();
    }

    public StringProperty placeholderProperty() {
        return placeholder;
    }

    public String getLabel() {
        return label.get();
    }

    public StringProperty labelProperty() {
        return label;
    }

    public String getTooltip() {
        return tooltip.get();
    }

    public StringProperty tooltipProperty() {
        return tooltip;
    }

    public boolean isValid() {
        return valid.get();
    }

    public BooleanProperty validProperty() {
        return valid;
    }

    public boolean isEditable() {
        return editable.get();
    }

    public BooleanProperty editableProperty() {
        return editable;
    }

    public String getErrorMessage() {
        return errorMessage.get();
    }

    public StringProperty errorMessageProperty() {
        return errorMessage;
    }

    public ObservableList<String> getStyleClass() {
        return styleClass.get();
    }

    public ListProperty<String> styleClassProperty() {
        return styleClass;
    }

    public static IntegerField ofInteger(IntegerProperty binding) {
        return new IntegerField(new SimpleIntegerProperty(binding.getValue()));
    }

    public static BooleanField ofBoolean(BooleanProperty binding) {
        return new BooleanField(new SimpleBooleanProperty(binding.getValue()));
    }

    public static <T> SingleSelectionField<T> ofSingleSelection(List<T> items, int selection) {
        return new SingleSelectionField<>(new SimpleListProperty<>(FXCollections.observableArrayList(items)), selection);
    }

    public static <T> SingleSelectionField<T> ofSingleSelection(List<T> items) {
        return new SingleSelectionField<>(new SimpleListProperty<>(FXCollections.observableArrayList(items)), -1);
    }

}
