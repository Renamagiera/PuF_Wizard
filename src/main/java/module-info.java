module com.ducky.duckythewizard {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    //opens com.ducky.duckythewizard to javafx.fxml;
    //exports com.ducky.duckythewizard;
    exports com.ducky.duckythewizard.model;
    opens com.ducky.duckythewizard.model to javafx.fxml;
    exports com.ducky.duckythewizard.controller;
    opens com.ducky.duckythewizard.controller to javafx.fxml;
    exports com.ducky.duckythewizard.model.config;
    opens com.ducky.duckythewizard.model.config to javafx.fxml;
    exports com.ducky.duckythewizard.controller.TO_DELETE;
    opens com.ducky.duckythewizard.controller.TO_DELETE to javafx.fxml;
    exports com.ducky.duckythewizard.model.colors;
    opens com.ducky.duckythewizard.model.colors to javafx.fxml;
}