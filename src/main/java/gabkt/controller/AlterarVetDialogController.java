package gabkt.controller;

import gabkt.Dao.VeterinarioDao;
import gabkt.model.Cliente;
import gabkt.model.Veterinario;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AlterarVetDialogController {
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtCPF;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtCRMV;
    @FXML
    private TextField txtEspecialidade;

    private Stage dialogStage;
    private Veterinario vet;
    private VeterinarioDao vetDao = new VeterinarioDao();

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setVet(Veterinario vet) {
        this.vet = vet;

        if (vet != null) {
            txtNome.setText(vet.getNome());
            txtCPF.setText(String.valueOf(vet.getCpf()));
            txtTelefone.setText(String.valueOf(vet.getTelefone()));
            txtCRMV.setText(vet.getCrmv());
            txtEspecialidade.setText(vet.getEspecialidade());
        }
    }

    @FXML
    public void handleOk() {
        vet.setNome(txtNome.getText());
        vet.setCpf(Long.parseLong(txtCPF.getText()));
        vet.setTelefone(Long.parseLong(txtTelefone.getText()));
        vet.setCrmv(txtCRMV.getText());
        vet.setEspecialidade(txtEspecialidade.getText());

        vetDao.updateVeterinario(vet);

        dialogStage.close();
    }

    @FXML
    private void handleButtonCancelar() {
        dialogStage.close();
    }
}
