module com.ducky.duckythewizard {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ducky.duckythewizard to javafx.fxml;
    exports com.ducky.duckythewizard;
}