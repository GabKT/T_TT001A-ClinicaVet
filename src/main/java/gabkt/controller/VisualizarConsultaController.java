package gabkt.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import gabkt.Dao.AnimalDao;
import gabkt.Dao.ConsultaDao;
import gabkt.Dao.VeterinarioDao;
import gabkt.model.Animal;
import gabkt.model.Consulta;
import gabkt.model.Veterinario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class VisualizarConsultaController implements Initializable {

    @FXML
    private TextField txtBuscar;
    @FXML
    private TableView<Consulta> tableViewConsulta;
    @FXML
    private TableColumn<Consulta, String> tableColumnData;
    @FXML
    private TableColumn<Consulta, String> tableColumnHorario;
    @FXML
    private TableColumn<Consulta, String> tableColumnTipo;
    @FXML
    private TableColumn<Consulta, String> tableColumnStatus;
    @FXML
    private TableColumn<Consulta, String> tableColumnAnimal;
    @FXML
    private TableColumn<Consulta, String> tableColumnVeterinario;

    List<Consulta> listConsulta;
    List<Animal> listAnimals;
    List<Veterinario> listVet;
    private ConsultaDao consultaDao = new ConsultaDao();
    private AnimalDao animalDao = new AnimalDao();
    private VeterinarioDao veterinarioDao = new VeterinarioDao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarTableViewConsulta();
        configurarBuscaDinamica();
    }

    public void carregarTableViewConsulta() {
        listConsulta = consultaDao.getAllConsultas();
        listAnimals = animalDao.getAllAnimals();
        listVet = veterinarioDao.getAllVeterinarios();

        tableColumnData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tableColumnHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
        tableColumnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableColumnAnimal.setCellValueFactory(cellData -> {
            int animalId = cellData.getValue().getAnimal();
            String nomeAnimal = listAnimals.stream()
                    .filter(animal -> animal.getId() == animalId)
                    .map(Animal::getNome)
                    .findFirst()
                    .orElse("Desconhecido");
            return new SimpleStringProperty(nomeAnimal);
        });

        tableColumnVeterinario.setCellValueFactory(cellData -> {
            int vetId = cellData.getValue().getVeterinario();
            String nomeVet = listVet.stream()
                    .filter(vet -> vet.getId() == vetId)
                    .map(Veterinario::getNome)
                    .findFirst()
                    .orElse("Desconhecido");
            return new SimpleStringProperty(nomeVet);
        });

        tableViewConsulta.setItems(FXCollections.observableArrayList(listConsulta));
    }

    private void configurarBuscaDinamica() {
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            String nomeVeterinario = newValue.trim().toLowerCase(); // Converte para minúsculo para não diferenciar
                                                                    // maiúsculas e minúsculas
            if (!nomeVeterinario.isEmpty()) {
                // Filtra as consultas onde o nome do veterinário contém o texto digitado
                List<Consulta> filteredConsultas = listConsulta.stream()
                        .filter(consulta -> {
                            int vetId = consulta.getVeterinario();
                            String nomeVet = veterinarioDao.getAllVeterinarios().stream()
                                    .filter(vet -> vet.getId() == vetId)
                                    .map(Veterinario::getNome)
                                    .findFirst()
                                    .orElse("").toLowerCase(); // Nome do veterinário em minúsculo
                            return nomeVet.contains(nomeVeterinario); // Verifica se o nome do veterinário contém o
                                                                      // texto de busca
                        })
                        .collect(Collectors.toList());

                tableViewConsulta.setItems(FXCollections.observableArrayList(filteredConsultas));
            } else {
                // Se o campo de busca estiver vazio, exibe todas as consultas
                tableViewConsulta.setItems(FXCollections.observableArrayList(listConsulta));
            }
        });
    }

}
