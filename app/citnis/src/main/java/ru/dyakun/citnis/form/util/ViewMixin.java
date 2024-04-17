package ru.dyakun.citnis.form.util;

import java.util.List;
import java.util.Objects;

public interface ViewMixin {

    List<String> getStylesheets();

    default void init() {
        initializeSelf();
        initializeParts();
        layoutParts();
        setupEventHandlers();
        setupValueChangedListeners();
        setupBindings();
    }

    default void initializeSelf() {}

    void initializeParts();

    void layoutParts();

    default void setupEventHandlers() {}

    default void setupValueChangedListeners() {}

    default void setupBindings() {}

    default void addStylesheetFiles(String... stylesheetFile) {
        for (String file : stylesheetFile) {
            String stylesheet = Objects.requireNonNull(getClass().getResource(file)).toExternalForm();
            getStylesheets().add(stylesheet);
        }
    }

}
