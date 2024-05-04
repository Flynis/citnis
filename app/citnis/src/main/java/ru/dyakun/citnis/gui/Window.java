package ru.dyakun.citnis.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dyakun.citnis.model.db.Scheduler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Window {

    private static final Logger logger = LoggerFactory.getLogger(Window.class);

    public Window(Stage stage) {
        stage.setTitle("citnis");
        InputStream iconStream = Objects.requireNonNull(getClass().getResourceAsStream("/icons/city.png"));
        Image image = new Image(iconStream);
        stage.getIcons().add(image);
        stage.setMinHeight(480);
        stage.setMinWidth(640);
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/scenes/MainScene.fxml")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            logger.error("Main scene load failed", e);
            throw new IllegalStateException(e);
        }
        stage.setOnCloseRequest(e -> Scheduler.getInstance().stop());
        stage.show();
    }

}
