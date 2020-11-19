module pc_configuration {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.app to javafx.fxml;
    exports org.app;
}