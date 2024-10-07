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
    MenuItem menuItemListarVeterinarios;

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }

    @FXML
    public void handleMenuItemListarClientes() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/ListarClienteView.fxml"));
        anchorPane.getChildren().setAll(a);
    }

}
