package ru.dyakun.citnis.form.view;

import javafx.scene.layout.GridPane;
import ru.dyakun.citnis.form.structure.Field;
import ru.dyakun.citnis.form.structure.Form;
import ru.dyakun.citnis.form.util.ViewMixin;

import javafx.geometry.Insets;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FormPane extends GridPane implements ViewMixin {

    protected final int SPACING = 10;

    protected Form form;
    protected List<Field<?>> fields = new ArrayList<>();

    public FormPane(Form form) {
        this.form = form;
        init();
    }

    @Override
    public String getUserAgentStylesheet() {
        return Objects.requireNonNull(FormPane.class.getResource("/css/form.css")).toExternalForm();
    }

    @Override
    public void initializeParts() {
        fields = form.getFields();
    }

    @Override
    public void layoutParts() {
        getStyleClass().add("form-pane");

        setPadding(new Insets(10));
        getChildren().addAll(fields);
    }

}
