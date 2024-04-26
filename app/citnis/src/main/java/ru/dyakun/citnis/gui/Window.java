package ru.dyakun.citnis.gui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dyakun.citnis.model.DatabaseManager;

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
            scene.cursorProperty().bind(
                    Bindings
                            .when(DatabaseManager.getInstance().executingQueryProperty())
                            .then(Cursor.WAIT)
                            .otherwise(Cursor.DEFAULT)
            );
            stage.setScene(scene);
        } catch (IOException e) {
            logger.error("Main scene load failed", e);
            throw new IllegalStateException(e);
        }
        stage.show();
    }

}
