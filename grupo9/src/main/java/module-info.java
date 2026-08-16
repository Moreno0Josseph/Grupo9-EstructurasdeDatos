module espol.grupo9 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;

    opens espol.grupo9 to javafx.fxml;
    exports espol.grupo9;
}
