package gabkt.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AlterarClienteDialogController {

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    public void handleButtonCancelar() {
        dialogStage.close();
    }
}
