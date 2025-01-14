package gabkt.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class MainViewController implements Initializable {

    @FXML
    private MenuItem menuItemListarClientes;
    @FXML
    MenuItem menuItemListarAnimais;
    @FXML
    private MenuItem menuItemListarVeterinarios;

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AnchorPane a;
        try {
            a = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/ListarClienteView.fxml"));
            anchorPane.getChildren().setAll(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleMenuItemListarClientes() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/ListarClienteView.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleMenuItemCriarConsulta() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/CriarConsultaView.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleMenuItemListarVeterinario() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/VeterinarioView.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleMenuItemListarAnimal() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/AnimalView.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleMenuItemVisualizarConsulta() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/VisualizarConsultaView.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleMenuItemListarExames() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/MarcarExameView.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleMenuItemCriarProntuario() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/ProntuariosView.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleMenuItemVisualizarProntuario() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/VisualizarProntuarioView.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleMenuItemListarRelatorios() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/RelatorioView.fxml"));
        anchorPane.getChildren().setAll(a);
    }

}
