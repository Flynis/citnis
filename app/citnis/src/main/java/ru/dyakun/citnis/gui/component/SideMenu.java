package ru.dyakun.citnis.gui.component;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class SideMenu {
    private static final Logger logger = LoggerFactory.getLogger(SideMenu.class);

    private final StackPane contentArea;
    private final Map<PageDesc, Node> pages = new EnumMap<>(PageDesc.class);
    private final Map<PageDesc, Button> buttons = new EnumMap<>(PageDesc.class);
    private PageDesc current;

    public SideMenu(StackPane contentArea, VBox buttonArea) {
        this.contentArea = contentArea;

        for(PageDesc pageDesc : PageDesc.values()) {
            String fxmlPath = String.format("/scenes/%s.fxml", pageDesc.name());
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
                pages.put(pageDesc, root);
            } catch (IOException e) {
                logger.error("Page {} load failed", pageDesc);
                throw new RuntimeException(e);
            }
            JFXButton button = new JFXButton(pageDesc.getTitle());
            button.getStyleClass().add("side-btn");
            button.setPrefWidth(Double.POSITIVE_INFINITY);
            button.setOnAction(actionEvent -> setPage(pageDesc));
            buttonArea.getChildren().add(button);
            buttons.put(pageDesc, button);
        }
        current = PageDesc.values()[0];
        buttons.get(current).getStyleClass().remove("side-btn");
        buttons.get(current).getStyleClass().add("side-btn-selected");
        Node page = pages.get(current);
        contentArea.getChildren().clear();
        contentArea.getChildren().add(page);
    }

    private void setPage(PageDesc desc) {
        if(desc == current) {
            return;
        }
        buttons.get(current).getStyleClass().remove("side-btn-selected");
        buttons.get(current).getStyleClass().add("side-btn");
        current = desc;
        buttons.get(current).getStyleClass().remove("side-btn");
        buttons.get(current).getStyleClass().add("side-btn-selected");
        Node page = pages.get(current);
        contentArea.getChildren().clear();
        contentArea.getChildren().add(page);
    }

}
