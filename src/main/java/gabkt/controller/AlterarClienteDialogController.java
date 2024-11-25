package gabkt.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import gabkt.Dao.ClienteDao;
import gabkt.model.Cliente;

public class AlterarClienteDialogController {

    @FXML
    private TextField txtAlterarNomeCliente;
    @FXML
    private TextField txtAlterarCPFCliente;
    @FXML
    private TextField txtAlterarTelefoneCliente;
    @FXML
    private TextField txtAlterarEmailCliente;
    @FXML
    private TextField txtAlterarEnderecoCliente;

    private Stage dialogStage;
    private Cliente cliente;
    private final ClienteDao clienteDao = new ClienteDao();

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;

        if (cliente != null) {
            txtAlterarNomeCliente.setText(cliente.getNome());
            txtAlterarCPFCliente.setText(String.valueOf(cliente.getCpf()));
            txtAlterarTelefoneCliente.setText(String.valueOf(cliente.getTelefone()));
            txtAlterarEmailCliente.setText(cliente.getEmail());
            txtAlterarEnderecoCliente.setText(cliente.getEndereco());
        }
    }

    @FXML
    private void handleOk() {
        cliente.setNome(txtAlterarNomeCliente.getText());
        cliente.setCpf(Long.parseLong(txtAlterarCPFCliente.getText()));
        cliente.setTelefone(Long.parseLong(txtAlterarTelefoneCliente.getText()));
        cliente.setEmail(txtAlterarEmailCliente.getText());
        cliente.setEndereco(txtAlterarEnderecoCliente.getText());

        clienteDao.updateCliente(cliente);

        dialogStage.close();
    }

    @FXML
    private void handleButtonCancelar() {
        dialogStage.close();
    }
}
