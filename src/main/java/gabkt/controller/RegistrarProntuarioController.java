package gabkt.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import gabkt.Dao.AnimalDao;
import gabkt.Dao.ClienteDao;
import gabkt.Dao.ProntuarioDao;
import gabkt.model.Animal;
import gabkt.model.Cliente;
import gabkt.model.Prontuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class RegistrarProntuarioController implements Initializable {
    @FXML
    private TableView<Animal> tableViewAnimal;
    @FXML
    private TableColumn<Animal, String> tableColumnAnimal;
    @FXML
    private TableColumn<Animal, String> tableColumnDono;
    @FXML
    private TextArea txtTratamento;
    @FXML
    private TextArea txtObs;
    @FXML
    private TextArea txtDiagnostico;
    @FXML
    private DatePicker data;

    private List<Animal> listAnimals;
    private List<Cliente> listClientes;

    private AnimalDao animalDao = new AnimalDao();
    private ClienteDao clienteDao = new ClienteDao();
    private ProntuarioDao prontuarioDao = new ProntuarioDao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarTableViewAnimal();
    }

    public void carregarTableViewAnimal() {
        listAnimals = animalDao.getAllAnimals();
        listClientes = clienteDao.getAllClientes();

        tableColumnAnimal.setCellValueFactory(cellData -> {
            Animal animal = cellData.getValue();
            return new SimpleStringProperty(animal.getNome());
        });

        tableColumnDono.setCellValueFactory(cellData -> {
            Animal animal = cellData.getValue();
            Cliente dono = clienteDao.getClienteById(animal.getDono());
            return dono != null ? new SimpleStringProperty(dono.getNome()) : new SimpleStringProperty("");
        });

        tableViewAnimal.setItems(FXCollections.observableArrayList(listAnimals));
    }

    @FXML
    public void registrarProntuario() {
        Animal ani = tableViewAnimal.getSelectionModel().getSelectedItem();
        String tratamento = txtTratamento.getText().trim();
        String obs = txtObs.getText().trim();
        String diagnostico = txtDiagnostico.getText().trim();
        LocalDate dataSelecionada = data.getValue();
        Date data2 = java.sql.Date.valueOf(dataSelecionada);
        Prontuario pront = new Prontuario(data2, diagnostico, tratamento, obs, ani.getId());

        prontuarioDao.addProntuario(pront);
        carregarTableViewAnimal();
        limparCamposCadastro();
    }

    public void limparCamposCadastro() {
        txtTratamento.clear();
        txtObs.clear();
        txtDiagnostico.clear();
    }

}
