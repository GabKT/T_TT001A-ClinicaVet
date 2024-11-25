package gabkt.controller;

import javafx.scene.layout.AnchorPane;

public class LayoutUtils {

    public static void fitToParent(AnchorPane anchorPane) {
        anchorPane.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }
}
