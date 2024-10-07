package gabkt.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import gabkt.Dao.ClienteDao;
import gabkt.model.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    private List<Cliente> listClientes;
    private ObservableList<Cliente> observableListClientes;

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

        observableListClientes = FXCollections.observableArrayList(listClientes);
        tableViewClientes.setItems(observableListClientes);
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

}
