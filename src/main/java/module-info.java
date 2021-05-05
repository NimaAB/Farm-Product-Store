module Farm_Product_Store {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.app.controllers to javafx.fxml;

    exports org.app;
    exports org.app.controllers;
    //exports org.app.data;
    exports org.app.data.models;
    exports org.app.data.dataCollection;
    exports org.app.data.dataFormats;
    exports org.app.validation;
    exports org.app.validation.customExceptions;
    exports org.app.validation.ioExceptions;
    exports org.app.fileHandling.open;
    exports org.app.fileHandling.save;
    exports org.app.fileHandling.fileThreads;
}