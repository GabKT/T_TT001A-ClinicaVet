package gabkt.controller;

import java.time.LocalTime;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class CriarConsultaController {
    @FXML
    private TextField timeTextField;

    private LocalTime currentTime = LocalTime.of(12, 0); // Valor inicial

    // Regex para garantir o formato XX:XX (apenas números)
    private final String TIME_PATTERN = "^([01]?[0-9]|2[0-3]):([0-5]?[0-9])$";

    @FXML
    public void initialize() {
        // Configurações iniciais
        timeTextField.setText("00:00"); // Valor inicial no formato XX:XX

        // Listener para garantir que o usuário só insira números e manter o formato
        // XX:XX
        timeTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Se o valor não tiver o formato correto, vamos ajustá-lo.
                if (newValue.length() > 5) {
                    // Limitar a entrada para no máximo 5 caracteres (xx:xx)
                    timeTextField.setText(oldValue);
                    return;
                }

                // Adicionando ":" no meio automaticamente, caso o usuário tenha digitado algo
                // como "1234"
                if (newValue.length() == 2 && !newValue.contains(":")) {
                    timeTextField.setText(newValue + ":");
                } else if (newValue.length() == 3 && newValue.charAt(2) != ':') {
                    timeTextField.setText(newValue.substring(0, 2) + ":" + newValue.substring(2));
                }

                // Validar se o valor está correto no formato XX:XX
                if (newValue.matches(TIME_PATTERN)) {
                    // Se for válido, apenas atualiza o valor
                    // timeTextField.setText(newValue); // Já é feito automaticamente no listener
                }
            }
        });
    }
}
