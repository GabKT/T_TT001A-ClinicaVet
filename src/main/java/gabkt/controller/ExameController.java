package gabkt.controller;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import gabkt.Dao.AnimalDao;
import gabkt.Dao.ConsultaDao;
import gabkt.Dao.ExameDao;
import gabkt.Dao.VeterinarioDao;
import gabkt.model.Animal;
import gabkt.model.Consulta;
import gabkt.model.Exame;
import gabkt.model.Veterinario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class ExameController implements Initializable {

    @FXML
    private TextField txtCadTipo;
    @FXML
    private DatePicker dateCad;
    @FXML
    private TextArea txtCadResultado;
    @FXML
    private TableView<Consulta> tableViewConsulta;
    @FXML
    private TableView<Exame> tableViewExame;
    @FXML
    private TableColumn<Consulta, String> tableColumnDataConsulta;
    @FXML
    private TableColumn<Consulta, String> tableColumnAnimalConsulta;
    @FXML
    private TableColumn<Consulta, String> tableColumnVeterinario;
    @FXML
    private TableColumn<Exame, String> tableColumnData;
    @FXML
    private TableColumn<Exame, String> tableColumnAnimal;

    @FXML
    private TextField txtInfoTipo;
    @FXML
    private TextField txtInfoData;
    @FXML
    private TextArea txtInfoResultado;
    @FXML
    private TextField txtInfoAnimal;
    @FXML
    private TextField txtInfoVeterinario;
    @FXML
    private TextField txtIdConsulta;
    @FXML
    private Label idExame;

    @FXML
    private TextField txtBuscarConsulta;
    @FXML
    private TextField txtBuscarExame;

    private List<Exame> listExame;
    private ExameDao exameDao = new ExameDao();

    List<Consulta> listConsulta;
    List<Animal> listAnimals;
    List<Veterinario> listVet;
    private ConsultaDao consultaDao = new ConsultaDao();
    private AnimalDao animalDao = new AnimalDao();
    private VeterinarioDao veterinarioDao = new VeterinarioDao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtIdConsulta.setEditable(false);
        carregarTableViewConsulta();
        carregarTableViewExame();
        configurarBuscaDinamica();
        configurarBuscaDinamicaExame();

        tableViewExame.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selecionaritemTableViewExame(newValue);
            }
        });
    }

    public void carregarTableViewConsulta() {
        listConsulta = consultaDao.getAllConsultas();
        listAnimals = animalDao.getAllAnimals();
        listVet = veterinarioDao.getAllVeterinarios();

        tableColumnDataConsulta.setCellValueFactory(new PropertyValueFactory<>("data"));

        tableColumnAnimalConsulta.setCellValueFactory(cellData -> {
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

    public void carregarTableViewExame() {
        listExame = exameDao.getAllExames();

        tableColumnData.setCellValueFactory(new PropertyValueFactory<>("data"));

        tableColumnAnimal.setCellValueFactory(cellData -> {
            int consultaId = cellData.getValue().getConsulta();
            Consulta consulta = listConsulta.stream()
                    .filter(c -> c.getId() == consultaId)
                    .findFirst()
                    .orElse(null);

            if (consulta != null) {
                int animalId = consulta.getAnimal();
                String nomeAnimal = listAnimals.stream()
                        .filter(animal -> animal.getId() == animalId)
                        .map(Animal::getNome)
                        .findFirst()
                        .orElse("Desconhecido");

                return new SimpleStringProperty(nomeAnimal);
            } else {
                return new SimpleStringProperty("Desconhecido");
            }
        });
        tableViewExame.setItems(FXCollections.observableArrayList(listExame));
    }

    @FXML
    public void registrarExame() {
        String tipo = txtCadTipo.getText().trim();
        LocalDate dataSelecionada = dateCad.getValue();
        Date data = java.sql.Date.valueOf(dataSelecionada);
        String resultado = txtCadResultado.getText().trim();
        Consulta consulta = tableViewConsulta.getSelectionModel().getSelectedItem();
        Exame exame = new Exame(tipo, data, resultado, consulta.getId());
        exameDao.addExame(exame);
        limparCamposCadastro();
        carregarTableViewExame();
    }

    public void limparCamposCadastro() {
        txtCadResultado.clear();
        txtCadTipo.clear();
    }

    private void configurarBuscaDinamica() {
        txtBuscarConsulta.textProperty().addListener((observable, oldValue, newValue) -> {
            String nomeVeterinario = newValue.trim().toLowerCase();
            if (!nomeVeterinario.isEmpty()) {
                List<Consulta> filteredConsultas = listConsulta.stream()
                        .filter(consulta -> {
                            int vetId = consulta.getVeterinario();
                            String nomeVet = veterinarioDao.getAllVeterinarios().stream()
                                    .filter(vet -> vet.getId() == vetId)
                                    .map(Veterinario::getNome)
                                    .findFirst()
                                    .orElse("").toLowerCase();
                            return nomeVet.contains(nomeVeterinario);
                        })
                        .collect(Collectors.toList());

                tableViewConsulta.setItems(FXCollections.observableArrayList(filteredConsultas));
            } else {
                tableViewConsulta.setItems(FXCollections.observableArrayList(listConsulta));
            }
        });
    }

    private void configurarBuscaDinamicaExame() {
        txtBuscarExame.textProperty().addListener((observable, oldValue, newValue) -> {
            String nomeAnimalBusca = newValue.trim().toLowerCase();

            if (!nomeAnimalBusca.isEmpty()) {
                List<Exame> filteredExames = listExame.stream()
                        .filter(exame -> {
                            int consultaId = exame.getConsulta();
                            Consulta consulta = listConsulta.stream()
                                    .filter(c -> c.getId() == consultaId)
                                    .findFirst()
                                    .orElse(null);
                            if (consulta != null) {
                                int animalId = consulta.getAnimal();
                                String nomeAnimal = listAnimals.stream()
                                        .filter(animal -> animal.getId() == animalId)
                                        .map(Animal::getNome)
                                        .findFirst()
                                        .orElse("").toLowerCase();

                                return nomeAnimal.contains(nomeAnimalBusca);
                            }
                            return false;
                        })
                        .collect(Collectors.toList());

                tableViewExame.setItems(FXCollections.observableArrayList(filteredExames));
            } else {
                tableViewExame.setItems(FXCollections.observableArrayList(listExame));
            }
        });
    }

    public void selecionaritemTableViewExame(Exame exame) {
        txtInfoTipo.setText(String.valueOf(exame.getTipo()));
        idExame.setText(String.valueOf(exame.getId()));
        txtInfoData.setText(String.valueOf(exame.getData()));
        txtInfoResultado.setText(String.valueOf(exame.getResultado()));
        txtIdConsulta.setText(String.valueOf(exame.getConsulta()));

        int consultaId = exame.getConsulta();
        Consulta consulta = listConsulta.stream()
                .filter(c -> c.getId() == consultaId)
                .findFirst()
                .orElse(null);

        if (consulta != null) {
            int animalId = consulta.getAnimal();
            String nomeAnimal = listAnimals.stream()
                    .filter(animal -> animal.getId() == animalId)
                    .map(Animal::getNome)
                    .findFirst()
                    .orElse("Desconhecido");
            txtInfoAnimal.setText(nomeAnimal);
        } else {
            txtInfoAnimal.setText("Desconhecido");
        }

        int veterinarioId = consulta != null ? consulta.getVeterinario() : -1;
        Veterinario veterinario = listVet.stream()
                .filter(vet -> vet.getId() == veterinarioId)
                .findFirst()
                .orElse(null);

        if (veterinario != null) {
            txtInfoVeterinario.setText(veterinario.getNome());
        } else {
            txtInfoVeterinario.setText("Desconhecido");
        }
    }

    @FXML
    public void atualizarExame() {
        String idExameString = idExame.getText();
        int idExame = Integer.parseInt(idExameString);
        String tipo = txtInfoTipo.getText();
        String resultado = txtInfoResultado.getText();

        Date dataExame = parseDate(txtInfoData.getText());
        int idConsulta = Integer.parseInt(txtIdConsulta.getText());

        Exame exameAtualizado = new Exame(idExame, tipo, dataExame, resultado, idConsulta);

        exameDao.updateExame(exameAtualizado);
        carregarTableViewExame();
    }

    @FXML
    public void removerExame() {
        Exame exameSelecionado = tableViewExame.getSelectionModel().getSelectedItem();
        if (exameSelecionado != null) {
            exameDao.deleteExame(exameSelecionado.getId());
            listExame.remove(exameSelecionado);
            tableViewExame.setItems(FXCollections.observableArrayList(listExame));
        }
    }

    private Date parseDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

}
