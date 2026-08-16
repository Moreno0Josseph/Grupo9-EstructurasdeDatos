module espol.grupo9 {
    requires javafx.controls;
    requires javafx.fxml;

    opens espol.grupo9 to javafx.fxml;
    exports espol.grupo9;
}
