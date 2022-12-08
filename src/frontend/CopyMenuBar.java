package frontend;

import com.sun.javafx.scene.web.skin.HTMLEditorSkin;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import java.util.ResourceBundle;

public class CopyMenuBar extends HBox {

    // Botones Barra Superior
    Button copyButton = new Button ("Copiar", getImage("copyIcon"));
    Button cutButton = new Button ("Cortar", getImage("cutIcon"));
    Button pasteButton = new Button ("Pegar", getImage("pasteIcon"));

    public CopyMenuBar(){
        Button[] copyToolsArr = { cutButton,copyButton,pasteButton};
        for(Button tool : copyToolsArr) {
            tool.setMinWidth(50);
            tool.setCursor(Cursor.HAND);
        }

        getChildren().addAll(copyToolsArr);
        setPadding(new Insets(5));
        setSpacing(10);
        setStyle("-fx-background-color: #999");
    }

    //Obtengo el icono correspondiente
    private ImageView getImage(String imagePath){
        String cutIconPath = ResourceBundle.getBundle(HTMLEditorSkin.class.getName()).getString(imagePath);
        Image cutIcon = new Image(HTMLEditorSkin.class.getResource(cutIconPath).toString());
        return new ImageView(cutIcon);
    }
}
