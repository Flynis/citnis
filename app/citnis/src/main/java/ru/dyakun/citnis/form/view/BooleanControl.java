package ru.dyakun.citnis.form.view;

import com.dlsc.formsfx.model.structure.BooleanField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class BooleanControl extends FieldControl<BooleanField> {

    protected Label fieldLabel;
    protected CheckBox checkBox;
    protected VBox container;

    @Override
    public void initializeParts() {
        super.initializeParts();

        getStyleClass().add("boolean-control");

        fieldLabel = new Label(field.getLabel());
        checkBox = new CheckBox();
        container = new VBox();
        checkBox.setSelected(field.getValue());
    }

    @Override
    public void layoutParts() {
        super.layoutParts();

        int columns = field.getSpan();
        container.getChildren().add(checkBox);

        add(fieldLabel, 0, 0, 2, 1);
        add(container, 2, 0, columns - 2, 1);
    }

    @Override
    public void setupBindings() {
        super.setupBindings();

        checkBox.disableProperty().bind(field.editableProperty().not());
        fieldLabel.textProperty().bind(field.labelProperty());
    }

    @Override
    public void setupValueChangedListeners() {
        super.setupValueChangedListeners();
        field.userInputProperty().addListener((observable, oldValue, newValue) -> checkBox.setSelected(Boolean.parseBoolean(field.getUserInput())));

        field.errorMessagesProperty().addListener((observable, oldValue, newValue) -> toggleTooltip(checkBox));
        field.tooltipProperty().addListener((observable, oldValue, newValue) -> toggleTooltip(checkBox));

        checkBox.focusedProperty().addListener((observable, oldValue, newValue) -> toggleTooltip(checkBox));
    }

    @Override
    public void setupEventHandlers() {
        setOnMouseEntered(event -> toggleTooltip(checkBox));
        setOnMouseExited(event -> toggleTooltip(checkBox));

        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> field.userInputProperty().setValue(String.valueOf(newValue)));
    }

}
