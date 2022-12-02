module com.ducky.duckythewizard {
    requires javafx.controls;
    requires javafx.fxml;


    //opens com.ducky.duckythewizard to javafx.fxml;
    //exports com.ducky.duckythewizard;
    exports com.ducky.duckythewizard.model;
    opens com.ducky.duckythewizard.model to javafx.fxml;
    exports com.ducky.duckythewizard.controller;
    opens com.ducky.duckythewizard.controller to javafx.fxml;
    exports com.ducky.duckythewizard.model.config;
    opens com.ducky.duckythewizard.model.config to javafx.fxml;
}