package ru.dyakun.citnis;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dyakun.citnis.model.SelectionStorage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Launcher extends Application {
    private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        SelectionStorage.getInstance().init();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        logger.info("Initialize window");
        stage.setTitle("citnis");
        InputStream iconStream = Objects.requireNonNull(getClass().getResourceAsStream("/icons/city.png"));
        Image image = new Image(iconStream);
        stage.getIcons().add(image);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/scenes/MainScene.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}