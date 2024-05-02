package ru.dyakun.citnis;

import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dyakun.citnis.gui.Window;
import ru.dyakun.citnis.model.selection.SelectionStorage;

public class Launcher extends Application {

    private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        SelectionStorage.getInstance().init();
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            new Window(stage);
        } catch (Exception e) {
            logger.error("Fatal error", e);
        }
    }

}