module espol.grupo9 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;

    opens espol to javafx.fxml;
    exports espol;

    opens espol.controller to javafx.fxml;
    exports espol.controller;
}
