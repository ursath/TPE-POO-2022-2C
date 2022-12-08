package frontend;

import javafx.scene.Cursor;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;

public class CopyMenuBar extends HBox {

    // Botones Barra Superior
    ToggleButton copyButton = new ToggleButton("Copiar");
    ToggleButton pasteButton = new ToggleButton("Pegar");
    ToggleButton cutButton = new ToggleButton("Cortar");

    public CopyMenuBar(){
        ToggleButton[] copyToolsArr = { cutButton,copyButton,pasteButton};
        ToggleGroup tools = new ToggleGroup();
        for(ToggleButton tool : copyToolsArr) {
            tool.setMinWidth(90);
            tool.setToggleGroup(tools);
            tool.setCursor(Cursor.HAND);
        }

        getChildren().addAll(copyToolsArr);
        setPadding(new Insets(5));
        setStyle("-fx-background-color: #999");
    }

}
