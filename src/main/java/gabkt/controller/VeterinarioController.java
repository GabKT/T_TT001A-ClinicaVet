package gabkt.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import gabkt.Dao.VeterinarioDao;
import gabkt.model.Veterinario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class VeterinarioController implements Initializable {

    @FXML
    private TableView<Veterinario> tableViewVeterinario;
    @FXML
    private TableColumn<Veterinario, String> tableColumnVeterinarioNome;
    @FXML
    private TableColumn<Veterinario, String> tableColumnVeterinarioCPF;
    @FXML
    private Label labelVeterinarioID;
    @FXML
    private Label labelVeterinarioNome;
    @FXML
    private Label labelVeterinarioCPF;
    @FXML
    private Label labelVeterinarioTelefone;
    @FXML
    private Label labelVeterinarioCRMV;
    @FXML
    private Label labelVeterinarioEspecialidade;
    @FXML
    private TextField txtBuscarVet;
    @FXML
    private TextField txtNomeVet;

    @FXML
    private TextField txtCPFVet;

    @FXML
    private TextField txtTelefoneVet;

    @FXML
    private TextField txtCRMVVet;

    @FXML
    private TextField txtEspecialidadeVet;

    @FXML
    private Button btnCadastrarVet;

    @FXML
    private Button btnRemoverVet;

    List<Veterinario> veterinarios;
    private ObservableList<Veterinario> observableVeterinarios;
    private final VeterinarioDao vetDao = new VeterinarioDao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarTableViewVeterinario();

        tableViewVeterinario.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionaritemTableViewVeterinarios(newValue));

        txtBuscarVet.textProperty().addListener((observable, oldValue, newValue) -> buscarVeterinarioPorCPF(newValue));
    }

    public void carregarTableViewVeterinario() {
        veterinarios = vetDao.getAllVeterinarios();

        tableColumnVeterinarioNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnVeterinarioCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        tableViewVeterinario.setItems(FXCollections.observableArrayList(veterinarios));
    }

    public void selecionaritemTableViewVeterinarios(Veterinario vet) {
        labelVeterinarioID.setText(String.valueOf(vet.getId()));
        labelVeterinarioNome.setText(String.valueOf(vet.getNome()));
        labelVeterinarioCPF.setText(String.valueOf(vet.getCpf()));
        labelVeterinarioTelefone.setText(String.valueOf(vet.getTelefone()));
        labelVeterinarioCRMV.setText(String.valueOf(vet.getCrmv()));
        labelVeterinarioEspecialidade.setText(String.valueOf(vet.getEspecialidade()));
    }

    public void buscarVeterinarioPorCPF(String cpf) {
        List<Veterinario> filtrados = veterinarios.stream()
                .filter(vet -> String.valueOf(vet.getCpf()).contains(cpf))
                .collect(Collectors.toList());

        observableVeterinarios = FXCollections.observableArrayList(filtrados);
        tableViewVeterinario.setItems(observableVeterinarios);
    }

    @FXML
    private void cadastrarVeterinario() {
        try {

            String nome = txtNomeVet.getText().trim();
            long cpf = Long.parseLong(txtCPFVet.getText().trim());
            long telefone = Long.parseLong(txtTelefoneVet.getText().trim());
            String crmv = txtCRMVVet.getText().trim();
            String especialidade = txtEspecialidadeVet.getText().trim();

            if (nome.isEmpty() || crmv.isEmpty() || especialidade.isEmpty()) {
                throw new IllegalArgumentException("Preencha todos os campos obrigatórios.");
            }

            Veterinario veterinario = new Veterinario();
            veterinario.setNome(nome);
            veterinario.setCpf(cpf);
            veterinario.setTelefone(telefone);
            veterinario.setCrmv(crmv);
            veterinario.setEspecialidade(especialidade);

            vetDao.addVeterinario(veterinario);

            System.out.println("Veterinário cadastrado com sucesso.");
            limparCamposCadastro();

            carregarTableViewVeterinario();

        } catch (NumberFormatException e) {
            System.out.println("CPF e Telefone devem conter apenas números.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar o veterinário: " + e.getMessage());
        }
    }

    @FXML
    private void excluirVeterinario() {
        try {
            Veterinario veterinarioSelecionado = tableViewVeterinario.getSelectionModel().getSelectedItem();
            if (veterinarioSelecionado == null) {
                throw new IllegalArgumentException("Nenhum veterinário selecionado.");
            }

            vetDao.deleteVeterinario(veterinarioSelecionado.getId());

            veterinarios.remove(veterinarioSelecionado);
            tableViewVeterinario.setItems(FXCollections.observableArrayList(veterinarios));

            System.out.println("Veterinário excluído com sucesso.");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao excluir o veterinário: " + e.getMessage());
        }
    }

    private void limparCamposCadastro() {
        txtNomeVet.clear();
        txtCPFVet.clear();
        txtTelefoneVet.clear();
        txtCRMVVet.clear();
        txtEspecialidadeVet.clear();
    }

}
