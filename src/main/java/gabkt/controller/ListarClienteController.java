package gabkt.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import gabkt.Dao.ClienteDao;
import gabkt.model.Cliente;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ListarClienteController implements Initializable {
    @FXML
    private TableView<Cliente> tableViewClientes;
    @FXML
    private TableColumn<Cliente, String> tableColumnClienteNome;
    @FXML
    TableColumn<Cliente, Long> tableColumnClienteCPF;
    @FXML
    private Button btnAlterar;
    @FXML
    private Button btnRemover;
    @FXML
    private Label labelClienteID;
    @FXML
    private Label labelClienteNome;
    @FXML
    private Label labelClienteCPF;
    @FXML
    private Label labelClienteTelefone;
    @FXML
    private Label labelClienteEmail;
    @FXML
    private Label labelClienteEndereco;
    @FXML
    private TextField txtBuscarCliente;

    private List<Cliente> listClientes;

    private final ClienteDao clienteDao = new ClienteDao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarTableViewCliente();

        tableViewClientes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionaritemTableViewClientes(newValue));
    }

    public void carregarTableViewCliente() {
        listClientes = clienteDao.getAllClientes();
        tableColumnClienteNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnClienteCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        tableViewClientes.setItems(FXCollections.observableArrayList(listClientes));

        txtBuscarCliente.textProperty().addListener((observale, oldValue, newValue) -> buscarClientePorCPF(newValue));
    }

    public void selecionaritemTableViewClientes(Cliente cliente) {
        labelClienteID.setText(String.valueOf(cliente.getId()));
        labelClienteNome.setText(String.valueOf(cliente.getNome()));
        labelClienteCPF.setText(String.valueOf(cliente.getCpf()));
        labelClienteTelefone.setText(String.valueOf(cliente.getTelefone()));
        labelClienteEmail.setText(String.valueOf(cliente.getEmail()));
        labelClienteEndereco.setText(String.valueOf(cliente.getEndereco()));
    }

    public void showAlterarClienteDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AlterarClienteDialogView.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Alterar Cliente");
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AlterarClienteDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buscarClientePorCPF(String cpf) {
        List<Cliente> filtrados = listClientes.stream()
                .filter(cliente -> String.valueOf(cliente.getCpf()).contains(cpf))
                .collect(Collectors.toList());

        tableViewClientes.setItems(FXCollections.observableArrayList(filtrados));
    }

}
