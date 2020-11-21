module pc_configuration {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.app.controllers to javafx.fxml;

    exports org.app;
    exports org.app.controllers;
    exports dataModels.data;
    exports dataModels.dataCollection;
    exports dataModels.dataFormats;
    exports filehandling;
    exports filehandling.bin;
    exports filehandling.csv;
    exports validations;
    exports validations.customExceptions;
    exports validations.ioExceptions;


}