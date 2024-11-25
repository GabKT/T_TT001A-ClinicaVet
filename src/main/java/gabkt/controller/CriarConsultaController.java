package gabkt.controller;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import gabkt.Dao.AnimalDao;
import gabkt.Dao.ClienteDao;
import gabkt.Dao.ConsultaDao;
import gabkt.Dao.VeterinarioDao;
import gabkt.model.Animal;
import gabkt.model.Cliente;
import gabkt.model.Consulta;
import gabkt.model.Veterinario;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;

public class CriarConsultaController {
    @FXML
    private TextField timeTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField tipoField;
    @FXML
    private TextField statusField;
    @FXML
    private TextField searchAnimalField;
    @FXML
    private TextField searchClienteField;
    @FXML
    private TextField searchVeterinarioField;
    @FXML
    private TableView<Animal> tableViewAnimal;
    @FXML
    private TableView<Cliente> tableViewClientes;
    @FXML
    private TableView<Veterinario> tableViewVeterinario;
    @FXML
    private TableColumn<Animal, String> tableColumnAnimal;
    @FXML
    private TableColumn<Animal, String> tableColumnDono;
    @FXML
    private TableColumn<Cliente, String> tableColumnClienteNome;
    @FXML
    private TableColumn<Cliente, String> tableColumnClienteCPF;
    @FXML
    private TableColumn<Veterinario, String> tableColumnVeterinarioNome;
    @FXML
    private TableColumn<Veterinario, String> tableColumnVeterinarioCPF;

    private AnimalDao animalDao = new AnimalDao();
    private ClienteDao clienteDao = new ClienteDao();
    private VeterinarioDao vetDao = new VeterinarioDao();
    private ConsultaDao consultaDao = new ConsultaDao();

    List<Animal> listAnimals;
    List<Cliente> listClientes;
    List<Veterinario> listVet;

    private LocalTime currentTime = LocalTime.of(12, 0);

    private final String TIME_PATTERN = "^([01]?[0-9]|2[0-3]):([0-5]?[0-9])$";

    @FXML
    public void initialize() {

        carregarTableViewAnimal();
        carregarTableViewCliente();
        carregarTableViewVeterinario();

        timeTextField.setText("00:00");

        timeTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (newValue.length() > 5) {

                    timeTextField.setText(oldValue);
                    return;
                }

                if (newValue.length() == 2 && !newValue.contains(":")) {
                    timeTextField.setText(newValue + ":");
                } else if (newValue.length() == 3 && newValue.charAt(2) != ':') {
                    timeTextField.setText(newValue.substring(0, 2) + ":" + newValue.substring(2));
                }

                if (newValue.matches(TIME_PATTERN)) {

                }
            }
        });
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
        searchAnimalField.textProperty().addListener((observale, oldValue, newValue) -> buscarAnimal(newValue));
    }

    public void buscarAnimal(String animal) {
        List<Animal> filtrados = listAnimals.stream().filter(ani -> ani.getNome().contains(animal))
                .collect(Collectors.toList());

        tableViewAnimal.setItems(FXCollections.observableArrayList(filtrados));
    }

    public void carregarTableViewCliente() {
        listClientes = clienteDao.getAllClientes();
        tableColumnClienteNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnClienteCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        tableViewClientes.setItems(FXCollections.observableArrayList(listClientes));

        searchClienteField.textProperty().addListener((observale, oldValue, newValue) -> buscarClientePorCPF(newValue));
    }

    public void buscarClientePorCPF(String cpf) {
        List<Cliente> filtrados = listClientes.stream()
                .filter(cliente -> String.valueOf(cliente.getCpf()).contains(cpf))
                .collect(Collectors.toList());

        tableViewClientes.setItems(FXCollections.observableArrayList(filtrados));
    }

    public void carregarTableViewVeterinario() {
        listVet = vetDao.getAllVeterinarios();

        tableColumnVeterinarioNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnVeterinarioCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        tableViewVeterinario.setItems(FXCollections.observableArrayList(listVet));
        searchVeterinarioField.textProperty()
                .addListener((observale, oldValue, newValue) -> buscarVeterinarioPorCPF(newValue));
    }

    public void buscarVeterinarioPorCPF(String cpf) {
        List<Veterinario> filtrados = listVet.stream()
                .filter(vet -> String.valueOf(vet.getCpf()).contains(cpf))
                .collect(Collectors.toList());
        tableViewVeterinario.setItems(FXCollections.observableArrayList(filtrados));
    }

    @FXML
    private void cadastrarConsulta() {
        LocalDate dataSelecionada = datePicker.getValue();
        Date data = java.sql.Date.valueOf(dataSelecionada);

        String horasTexto = timeTextField.getText().trim();
        Time horas;
        try {
            horas = Time.valueOf(horasTexto + ":00");
        } catch (IllegalArgumentException e) {
            System.out.println("Formato de hora inválido. Use o formato HH:mm.");
            return;
        }

        String tipo = tipoField.getText().trim();
        String status = statusField.getText().trim();

        Cliente dono = tableViewClientes.getSelectionModel().getSelectedItem();
        Animal animal = tableViewAnimal.getSelectionModel().getSelectedItem();
        Veterinario vet = tableViewVeterinario.getSelectionModel().getSelectedItem();

        Consulta consulta = new Consulta(data, horas, tipo, status, animal.getId(), dono.getId(), vet.getId());

        consultaDao.inserirConsulta(consulta);
        limparCamposCadastro();
    }

    public void limparCamposCadastro() {
        timeTextField.clear();
        tipoField.clear();
        statusField.clear();
    }

}
