package gabkt.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gabkt.Dao.AnimalDao;
import gabkt.Dao.ProntuarioDao;
import gabkt.model.Animal;
import gabkt.model.Prontuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class VisualizarProntuarioController implements Initializable {
    @FXML
    private TextField txtBuscarAnimal;
    @FXML
    private TableView<Prontuario> tableViewAnimal;
    @FXML
    private TableColumn<Prontuario, String> tableColumnData;
    @FXML
    private TableColumn<Prontuario, String> tableColumnAnimal;
    @FXML
    private Label animal;
    @FXML
    private Label id;
    @FXML
    private Label data;
    @FXML
    private TextArea txtDiagnostico;
    @FXML
    private TextArea txtTratamento;
    @FXML
    private TextArea txtObs;

    private List<Animal> listAnimals;
    private List<Prontuario> listProntuarios;

    private AnimalDao animalDao = new AnimalDao();
    private ProntuarioDao prontDao = new ProntuarioDao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarTableViewAnimal();
        configurarSelecaoTabela();
        txtBuscarAnimal.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarTabelaPorAnimal(newValue);
        });
    }

    public void carregarTableViewAnimal() {
        listProntuarios = prontDao.getAllProntuarios();

        tableColumnData.setCellValueFactory(new PropertyValueFactory<>("dataConsulta"));
        tableColumnAnimal.setCellValueFactory(cellData -> {
            Prontuario prontuario = cellData.getValue();
            Animal animal = animalDao.getAnimalById(prontuario.getAnimal());
            return new SimpleStringProperty(animal != null ? animal.getNome() : "Desconhecido");
        });

        tableViewAnimal.setItems(FXCollections.observableArrayList(listProntuarios));
    }

    public void configurarSelecaoTabela() {
        tableViewAnimal.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                preencherCampos(newValue);
            }
        });
    }

    public void preencherCampos(Prontuario prontuario) {
        Animal animalSelecionado = animalDao.getAnimalById(prontuario.getAnimal());

        id.setText(String.valueOf(prontuario.getId()));
        data.setText(String.valueOf(prontuario.getDataConsulta()));
        animal.setText(animalSelecionado != null ? animalSelecionado.getNome() : "Desconhecido");
        txtDiagnostico.setText(prontuario.getDiagnostico());
        txtTratamento.setText(prontuario.getTratamento());
        txtObs.setText(prontuario.getObservacoes());
    }

    @FXML
    public void removerProntuario() {
        try {
            Prontuario prontuarioSelecionado = tableViewAnimal.getSelectionModel().getSelectedItem();

            if (prontuarioSelecionado != null) {
                prontDao.deleteProntuario(prontuarioSelecionado.getId());

                carregarTableViewAnimal();
                limparCampos();
            } else {
                System.err.println("Nenhum item selecionado para remoção.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao remover prontuário: " + e.getMessage());
        }
    }

    @FXML
    public void atualizarProntuario() {
        try {
            int idProntuario = Integer.parseInt(id.getText().trim());
            String diagnosticoAtualizado = txtDiagnostico.getText().trim();
            String tratamentoAtualizado = txtTratamento.getText().trim();
            String observacoesAtualizadas = txtObs.getText().trim();
            String dataConsultaAtualizada = data.getText().trim();
            Prontuario prontuarioAtualizado = new Prontuario(
                    idProntuario,
                    java.sql.Date.valueOf(dataConsultaAtualizada),
                    diagnosticoAtualizado,
                    tratamentoAtualizado,
                    observacoesAtualizadas,
                    tableViewAnimal.getSelectionModel().getSelectedItem().getAnimal());

            prontDao.updateProntuario(prontuarioAtualizado);

            carregarTableViewAnimal();
            limparCampos();

        } catch (Exception e) {
            System.err.println("Erro ao atualizar prontuário: " + e.getMessage());
        }
    }

    public void filtrarTabelaPorAnimal(String nomeAnimal) {
        if (nomeAnimal == null || nomeAnimal.isEmpty()) {
            tableViewAnimal.setItems(FXCollections.observableArrayList(listProntuarios));
            return;
        }

        List<Prontuario> prontuariosFiltrados = new ArrayList<>();
        for (Prontuario prontuario : listProntuarios) {
            Animal animal = animalDao.getAnimalById(prontuario.getAnimal());
            if (animal != null && animal.getNome().toLowerCase().contains(nomeAnimal.toLowerCase())) {
                prontuariosFiltrados.add(prontuario);
            }
        }

        tableViewAnimal.setItems(FXCollections.observableArrayList(prontuariosFiltrados));
    }

    public void limparCampos() {
        id.setText("");
        data.setText("");
        animal.setText("");
        txtDiagnostico.clear();
        txtTratamento.clear();
        txtObs.clear();
    }

}
