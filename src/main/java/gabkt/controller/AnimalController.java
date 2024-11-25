package gabkt.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import gabkt.Dao.AnimalDao;
import gabkt.Dao.ClienteDao;
import gabkt.model.Animal;
import gabkt.model.Cliente;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AnimalController implements Initializable {
    @FXML
    private TableView<Animal> tableViewAnimal;
    @FXML
    private TableColumn<Animal, String> tableColumnAnimal;
    @FXML
    private TableColumn<Animal, String> tableColumnDono;
    @FXML
    private TextField txtInfoNome;
    @FXML
    private TextField txtInfoEspecie;
    @FXML
    private TextField txtInfoRaca;
    @FXML
    private TextField txtInfoIdade;
    @FXML
    private TextField txtInfoPeso;
    @FXML
    private TextField txtInfoDono;
    @FXML
    private TextField txtInfoNomeDono;
    @FXML
    private TextField txtBuscarDono;
    @FXML
    private TextField txtCadBuscarDono;
    @FXML
    private TableView<Cliente> tableViewDono;
    @FXML
    private TableColumn<Cliente, String> tableColumnNome;
    @FXML
    private TableColumn<Cliente, Long> tableColumnCPF;
    @FXML
    private TextField txtCadNome;
    @FXML
    private TextField txtCadEspecie;
    @FXML
    private TextField txtCadRaca;
    @FXML
    private TextField txtCadIdade;
    @FXML
    private TextField txtCadPeso;

    List<Animal> listAnimals;
    List<Cliente> listClientes;
    private final AnimalDao animalDao = new AnimalDao();
    private final ClienteDao clienteDao = new ClienteDao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtInfoDono.setEditable(false);
        carregarTableViewAnimal();
        carregarTableViewDono();
        tableViewAnimal.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> selecionaritemTableViewAnimal(newValue));
        txtBuscarDono.textProperty().addListener((observable, oldValue, newValue) -> buscarAnimal(newValue));
        txtCadBuscarDono.textProperty().addListener((observable, oldValue, newValue) -> buscarClientePorCPF(newValue));
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

    public void carregarTableViewDono() {
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        tableViewDono.setItems(FXCollections.observableArrayList(listClientes));
    }

    public void selecionaritemTableViewAnimal(Animal animal) {
        if (animal != null) {
            txtInfoNome.setText(animal.getNome());
            txtInfoEspecie.setText(animal.getEspecie());
            txtInfoRaca.setText(animal.getRaca());
            txtInfoIdade.setText(String.valueOf(animal.getIdade()));
            txtInfoPeso.setText(String.valueOf(animal.getPeso()));
            Cliente dono = clienteDao.getClienteById(animal.getDono());
            txtInfoDono.setText(String.valueOf((dono.getId())));
            txtInfoNomeDono.setText(String.valueOf((dono.getNome())));
        }
    }

    public void buscarAnimal(String animal) {
        List<Animal> filtrados = listAnimals.stream().filter(ani -> ani.getNome().contains(animal))
                .collect(Collectors.toList());

        tableViewAnimal.setItems(FXCollections.observableArrayList(filtrados));
    }

    @FXML
    private void cadastrarAnimal() {
        String nome = txtCadNome.getText().trim();
        String especie = txtCadEspecie.getText().trim();
        String raca = txtCadRaca.getText().trim();
        int idade = Integer.parseInt(txtCadIdade.getText().trim());
        double peso = Double.parseDouble(txtCadPeso.getText().trim());
        Cliente dono = tableViewDono.getSelectionModel().getSelectedItem();
        Animal animal = new Animal(nome, especie, raca, idade, peso, dono.getId());

        animalDao.addAnimal(animal);

        limparCamposCadastro();
        carregarTableViewAnimal();

    }

    public void buscarClientePorCPF(String cpf) {
        List<Cliente> filtrados = listClientes.stream()
                .filter(cliente -> String.valueOf(cliente.getCpf()).contains(cpf))
                .collect(Collectors.toList());

        tableViewDono.setItems(FXCollections.observableArrayList(filtrados));
    }

    public void limparCamposCadastro() {
        txtCadNome.clear();
        txtCadEspecie.clear();
        txtCadIdade.clear();
        txtCadPeso.clear();
        txtCadRaca.clear();

    }

    @FXML
    private void excluirAnimal() {
        Animal animalSelec = tableViewAnimal.getSelectionModel().getSelectedItem();
        animalDao.deleteAnimal(animalSelec.getId());
        listAnimals.remove(animalSelec);
        tableViewAnimal.setItems(FXCollections.observableArrayList(listAnimals));
    }

    @FXML
    private void atualizarAnimal() {
        Animal animalSelecionado = tableViewAnimal.getSelectionModel().getSelectedItem();
        int id = animalSelecionado.getId();
        String nome = txtInfoNome.getText().trim();
        String especie = txtInfoEspecie.getText().trim();
        String raca = txtInfoRaca.getText().trim();
        int idade = Integer.parseInt(txtInfoIdade.getText().trim());
        double peso = Double.parseDouble(txtInfoPeso.getText().trim());
        int dono = Integer.parseInt(txtInfoDono.getText().trim());
        Animal animal = new Animal(id, nome, especie, raca, idade, peso, dono);
        animalDao.updateAnimal(animal);
        carregarTableViewAnimal();
    }

}
