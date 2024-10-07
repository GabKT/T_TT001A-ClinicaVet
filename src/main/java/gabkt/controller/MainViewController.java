package gabkt.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class MainViewController {

    @FXML
    private void sayHello() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hello");
        alert.setHeaderText(null);
        alert.setContentText("Hello, JavaFX!");
        alert.showAndWait();
    }
}
