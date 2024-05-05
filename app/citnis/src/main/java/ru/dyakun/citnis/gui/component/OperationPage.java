package ru.dyakun.citnis.gui.component;

import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.dyakun.citnis.model.db.DatabaseManager;
import ru.dyakun.citnis.model.operation.Operation;

import java.sql.SQLException;

public class OperationPage extends Page {

    private final Operation operation;
    private final Button okBtn;

    public OperationPage(String title, Operation op) {
        super(title);
        this.operation = op;

        VBox contentArea = new VBox();
        contentArea.setPrefWidth(Double.POSITIVE_INFINITY);
        contentArea.setPadding(new Insets(5, 5, 5, 5));
        contentArea.getStyleClass().add("content-area");
        contentArea.setSpacing(10);

        okBtn = new Button(operation.getName());
        okBtn.getStyleClass().add("form-btn");

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(0, 0, 30, 30));
        hBox.getChildren().addAll(okBtn);

        Form form = operation.getForm();
        FormRenderer formPane = new FormRenderer(form);
        for(var child: formPane.getChildren()) {
            child.getStyleClass().remove("formsfx-group");
        }

        BorderPane opSettings = new BorderPane();
        opSettings.getStyleClass().add("content");
        opSettings.setCenter(formPane);
        opSettings.setBottom(hBox);

        contentArea.getChildren().addAll(opSettings);
        pane.setCenter(contentArea);
        setEventHandlers();
    }

    private void setEventHandlers() {
        okBtn.setOnAction(e -> onClick());
    }

    public void onClick() {
        Form form = operation.getForm();
        if(form.isValid()) {
            DatabaseManager manager = DatabaseManager.getInstance();
            try {
                if(operation.needCallFunction()) {
                    manager.callFunction(operation.getQuery());
                } else {
                    manager.executeOperation(operation.getQuery());
                }
                FxDialogs.showInformation("Успех", operation.getSuccessMessage());
                form.reset();
            } catch (SQLException e) {
                FxDialogs.showError("Ошибка", operation.getErrorMessage(e));
            }
        }
    }

    @Override
    public void reset() {
        operation.getForm().reset();
    }

}
