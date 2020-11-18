module pc_configuration {
    requires javafx.controls;
    requires javafx.fxml;

    opens views to javafx.fxml;
    exports gui;
}