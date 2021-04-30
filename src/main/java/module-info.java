module Farm_Product_Store {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.app.controllers to javafx.fxml;

    exports io.fileThreads;
    exports io.open;
    exports io.save;
    exports org.app;
    exports org.app.controllers;
    exports dataModels.models;
    exports dataModels.dataCollection;
    exports dataModels.dataFormats;
    exports validations;
    exports validations.customExceptions;
    exports validations.ioExceptions;

}