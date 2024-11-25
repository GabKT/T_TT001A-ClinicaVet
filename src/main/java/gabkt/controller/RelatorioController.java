package gabkt.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import gabkt.Dao.RelatorioFDao;
import gabkt.model.RelatorioF;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class RelatorioController implements Initializable {

    @FXML
    private DatePicker datacad;
    @FXML
    private TextField txtCadFaturamento;
    @FXML
    private TextField txtBuscarData;
    @FXML
    private TableView<RelatorioF> tableViewFaturamento;
    @FXML
    private TableColumn<RelatorioF, String> tableColumnData;
    @FXML
    private TableColumn<RelatorioF, String> tableColumnFaturamento;
    @FXML
    private TextField data;
    @FXML
    private TextField faturamento;
    @FXML
    private TextField id;

    private List<RelatorioF> listRelatorio;
    private RelatorioFDao relatorioFDao = new RelatorioFDao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setEditable(false);
        carregarTableViewFaturamento();
        tableViewFaturamento.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> selecionaritemTableViewFaturamento(newValue));
        txtBuscarData.textProperty().addListener((observable, oldValue, newValue) -> buscarPorMes(newValue));
    }

    public void carregarTableViewFaturamento() {
        listRelatorio = relatorioFDao.getAllRelatoriosF();
        tableColumnData.setCellValueFactory(new PropertyValueFactory<>("periodo"));
        tableColumnFaturamento.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        tableViewFaturamento.setItems(FXCollections.observableArrayList(listRelatorio));
    }

    @FXML
    public void registrarFaturamento() {
        LocalDate dataSelecionada = datacad.getValue();
        Date data = java.sql.Date.valueOf(dataSelecionada);
        double faturamento = Double.parseDouble(txtCadFaturamento.getText().trim());
        RelatorioF rf = new RelatorioF(data, faturamento);
        relatorioFDao.addRelatorioF(rf);
        carregarTableViewFaturamento();
        limparCamposCadastro();
    }

    public void limparCamposCadastro() {
        txtCadFaturamento.clear();
    }

    public void selecionaritemTableViewFaturamento(RelatorioF rf) {
        rf = tableViewFaturamento.getSelectionModel().getSelectedItem();
        id.setText(String.valueOf(rf.getId()));
        data.setText(String.valueOf(rf.getPeriodo()));
        faturamento.setText(String.valueOf(rf.getValorTotal()));
    }

    @FXML
    public void atualizarFaturamento() {
        try {
            int idRelatorio = Integer.parseInt(id.getText().trim());
            LocalDate dataSelecionada = LocalDate.parse(data.getText().trim()); //
            Date dataAtualizada = java.sql.Date.valueOf(dataSelecionada);
            double faturamentoAtualizado = Double.parseDouble(faturamento.getText().trim());

            RelatorioF rfAtualizado = new RelatorioF(idRelatorio, dataAtualizada, faturamentoAtualizado);

            relatorioFDao.updateRelatorioF(rfAtualizado);

            carregarTableViewFaturamento();
            limparCamposEdicao();

        } catch (Exception e) {
            System.err.println("Erro ao atualizar o registro: " + e.getMessage());
        }
    }

    public void limparCamposEdicao() {
        id.clear();
        data.clear();
        faturamento.clear();
    }

    @FXML
    public void deletarFaturamento() {
        try {
            int idRelatorio = Integer.parseInt(id.getText().trim());

            relatorioFDao.deleteRelatorioF(idRelatorio);

            carregarTableViewFaturamento();
            limparCamposEdicao();

        } catch (Exception e) {
            System.err.println("Erro ao deletar o registro: " + e.getMessage());
        }
    }

    public void buscarPorMes(String mesTexto) {
        if (mesTexto == null || mesTexto.trim().isEmpty()) {
            carregarTableViewFaturamento();
            return;
        }

        try {
            int mes = Integer.parseInt(mesTexto.trim());
            if (mes < 1 || mes > 12) {
                System.err.println("Insira um mês válido (1 a 12).");
                return;
            }

            List<RelatorioF> resultadosFiltrados = listRelatorio.stream()
                    .filter(rf -> {
                        LocalDate dataRelatorio = new java.sql.Date(rf.getPeriodo().getTime()).toLocalDate();
                        return String.valueOf(dataRelatorio.getMonthValue()).startsWith(mesTexto.trim());
                    })
                    .toList();

            tableViewFaturamento.setItems(FXCollections.observableArrayList(resultadosFiltrados));

        } catch (NumberFormatException e) {
            System.err.println("Formato inválido para o mês. Insira apenas números.");
        }
    }

}
