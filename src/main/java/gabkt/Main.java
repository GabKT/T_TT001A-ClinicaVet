package gabkt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage arg0) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
        Parent root = loader.load();
        arg0.setTitle("Clínica Veterinária App");
        arg0.setScene(new Scene(root, 1100, 600));
        arg0.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}