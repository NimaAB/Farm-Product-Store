module pc_configuration {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.app.gui.controllers to javafx.fxml;

    exports org.app.gui.controllers;
    exports org.app.gui;
    exports org.app;
    exports org.app.dataModels.data;
    exports org.app.dataModels.dataCollection;
    exports org.app.dataModels.dataFormats;
    exports org.app.filehandling.bin;
    exports org.app.filehandling.csv;
    exports org.app.validations.customExceptions;
    exports org.app.validations.ioExceptions;
}