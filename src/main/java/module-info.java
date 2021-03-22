module pc_configuration {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.app.controllers to javafx.fxml;

    exports io.fileThreads;
    exports io.open;
    exports io.save;
    exports org.app;
    exports org.app.controllers;
    exports dataModels.data;
    exports dataModels.dataCollection;
    exports dataModels.dataFormats;
    exports validations;
    exports validations.customExceptions;
    exports validations.ioExceptions;


}