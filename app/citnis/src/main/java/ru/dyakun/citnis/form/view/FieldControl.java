package ru.dyakun.citnis.form.view;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.view.util.ViewMixin;
import javafx.collections.ListChangeListener;
import javafx.css.PseudoClass;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public abstract class FieldControl<F extends Field> extends GridPane implements ViewMixin {

    protected F field;

    protected Tooltip tooltip;

    protected static final PseudoClass INVALID_CLASS = PseudoClass.getPseudoClass("invalid");

    public void setField(F field) {
        if (this.field != null) {
            throw new IllegalStateException("Cannot change a control's field once set.");
        }
        this.field = field;
        init();
    }

    @Override
    public void initializeParts() {
        getStyleClass().add("field-control");

        tooltip = new Tooltip();
        tooltip.getStyleClass().add("field-tooltip");

        getStyleClass().addAll(field.getStyleClass());

        updateStyle(INVALID_CLASS, !field.isValid());
    }

    @Override
    public void layoutParts() {
        setAlignment(Pos.CENTER_LEFT);

        int columns = field.getSpan();

        for (int i = 0; i < columns; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / columns);
            getColumnConstraints().add(colConst);
        }
    }

    @Override
    public void setupValueChangedListeners() {
        field.validProperty().addListener((observable, oldValue, newValue) -> updateStyle(INVALID_CLASS, !newValue));

        field.getStyleClass().addListener((ListChangeListener<String>) c -> {
            while (c.next()) {
                if (c.wasRemoved()) {
                    getStyleClass().removeAll(c.getRemoved());
                }

                if (c.wasAdded()) {
                    getStyleClass().addAll(c.getAddedSubList());
                }
            }
        });
    }

    protected void toggleTooltip(Node reference) {
        this.toggleTooltip(reference, (Control) reference);
    }

    protected void toggleTooltip(Node reference, Control below) {
        String fieldTooltip = field.getTooltip();

        if ((reference.isFocused() || reference.isHover()) && (!fieldTooltip.equals("") || field.getErrorMessages().size() > 0)) {
            tooltip.setText((!fieldTooltip.equals("") ? fieldTooltip + "\n" : "") + String.join("\n", field.getErrorMessages()));

            if (tooltip.isShowing()) {
                return;
            }

            Point2D p = below.localToScene(0.0, 0.0);

            tooltip.show(
                    getScene().getWindow(),
                    p.getX() + getScene().getX() + getScene().getWindow().getX(),
                    p.getY() + getScene().getY() + getScene().getWindow().getY() + below.getHeight() + 5
            );
        } else {
            tooltip.hide();
        }
    }

    protected void updateStyle(PseudoClass pseudo, boolean newValue) {
        pseudoClassStateChanged(pseudo, newValue);
    }

    public void addStyleClass(String name) {
        getStyleClass().add(name);
    }

    public void removeStyleClass(String name) {
        getStyleClass().remove(name);
    }

}
