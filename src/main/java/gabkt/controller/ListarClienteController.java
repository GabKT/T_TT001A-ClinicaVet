package gabkt.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    @FXML
    private TextField txtNomeCliente;
    @FXML
    private TextField txtCPFCliente;
    @FXML
    private TextField txtTelefoneCliente;
    @FXML
    private TextField txtEmailCliente;
    @FXML
    private TextField txtEnderecoCliente;

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

    @FXML
    public void alterarCliente() {

        Cliente clienteSelecionado = tableViewClientes.getSelectionModel().getSelectedItem();

        if (clienteSelecionado != null) {

            Cliente clienteAlterado = new Cliente();
            clienteAlterado.setId(Integer.parseInt(labelClienteID.getText()));
            clienteAlterado.setNome(labelClienteNome.getText());
            clienteAlterado.setCpf(Long.parseLong(labelClienteCPF.getText()));
            clienteAlterado.setTelefone(Long.parseLong(labelClienteTelefone.getText()));
            clienteAlterado.setEmail(labelClienteEmail.getText());
            clienteAlterado.setEndereco(labelClienteEndereco.getText());

            showAlterarClienteDialog(clienteAlterado);

            carregarTableViewCliente();
        } else {

            System.out.println("Nenhum cliente selecionado.");
        }
    }

    public void showAlterarClienteDialog(Cliente cliente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AlterarClienteDialogView.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Alterar Cliente");
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AlterarClienteDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCliente(cliente);

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

    @FXML
    public void cadastrarCliente() {
        String nome = txtNomeCliente.getText().trim();
        long cpf = Long.parseLong(txtCPFCliente.getText().trim());
        long telefone = Long.parseLong(txtTelefoneCliente.getText().trim());
        String email = txtEmailCliente.getText().trim();
        String endereco = txtEnderecoCliente.getText().trim();

        Cliente c1 = new Cliente(nome, cpf, telefone, email, endereco);
        try {
            clienteDao.addCliente(c1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        limparCamposCadastro();
        carregarTableViewCliente();
    }

    @FXML
    public void removerClienteSelecionado() {
        Cliente clienteSelecionado = tableViewClientes.getSelectionModel().getSelectedItem();

        int clienteId = clienteSelecionado.getId();

        clienteDao.deleteCliente(clienteId);

        listClientes.remove(clienteSelecionado);
        tableViewClientes.setItems(FXCollections.observableArrayList(listClientes));

    }

    private void limparCamposCadastro() {
        txtNomeCliente.clear();
        txtCPFCliente.clear();
        txtTelefoneCliente.clear();
        txtEmailCliente.clear();
        txtEnderecoCliente.clear();
    }

}
