module puf.wizardlocal {
    requires javafx.controls;
    requires javafx.fxml;


    opens puf.wizard to javafx.fxml;
    exports puf.wizard;
}