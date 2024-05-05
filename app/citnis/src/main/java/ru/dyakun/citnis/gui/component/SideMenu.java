package ru.dyakun.citnis.gui.component;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SideMenu {

    private final StackPane contentArea;
    private final VBox sidebar;
    private final Map<Integer, Page> pages = new HashMap<>();
    private final Map<Integer, Button> buttons = new HashMap<>();
    private int current = -1;

    public SideMenu(StackPane contentArea, VBox sidebar) {
        this.contentArea = contentArea;
        this.sidebar = sidebar;
    }

    public void addPages(String title, List<Page> pageBuilders) {
        Label label = new Label(title);
        label.getStyleClass().add("side-label");

        VBox labelBox = new VBox();
        labelBox.setPadding(new Insets(0, 0, 0 ,10));
        labelBox.getChildren().add(label);

        VBox buttonBox = new VBox();
        buttonBox.setFillWidth(true);
        buttonBox.getStyleClass().add("side-menu");

        for(var page: pageBuilders) {
            pages.put(page.getId(), page);

            JFXButton button = new JFXButton(page.getTitle());
            button.getStyleClass().add("side-btn");
            button.setPrefWidth(Double.POSITIVE_INFINITY);
            button.setOnAction(actionEvent -> setPage(page.getId()));
            buttonBox.getChildren().add(button);
            buttons.put(page.getId(), button);
        }

        VBox box = new VBox();
        box.setSpacing(5);
        box.getChildren().addAll(labelBox, buttonBox);

        sidebar.getChildren().add(box);
    }

    public void setCurrent(int i) {
        if(current == -1) {
            current = i;
            buttons.get(current).getStyleClass().remove("side-btn");
            buttons.get(current).getStyleClass().add("side-btn-selected");
            Page page = pages.get(current);
            page.reset();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(page.getNode());
        } else {
            setPage(i);
        }
    }

    private void setPage(int i) {
        if(i == current) {
            return;
        }
        buttons.get(current).getStyleClass().remove("side-btn-selected");
        buttons.get(current).getStyleClass().add("side-btn");
        current = i;
        buttons.get(current).getStyleClass().remove("side-btn");
        buttons.get(current).getStyleClass().add("side-btn-selected");
        Page page = pages.get(current);
        page.reset();
        contentArea.getChildren().clear();
        contentArea.getChildren().add(page.getNode());
    }

}
