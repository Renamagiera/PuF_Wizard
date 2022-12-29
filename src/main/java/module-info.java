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
    exports com.ducky.duckythewizard.model.color;
    opens com.ducky.duckythewizard.model.color to javafx.fxml;
    exports com.ducky.duckythewizard.model.card;
    opens com.ducky.duckythewizard.model.card to javafx.fxml;
}