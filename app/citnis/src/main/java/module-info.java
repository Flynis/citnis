module ru.dyakun.citnis {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires com.jfoenix;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens ru.dyakun.citnis;
    opens ru.dyakun.citnis.gui.controller to javafx.fxml;
    opens ru.dyakun.citnis.gui.component to javafx.fxml;
}