package GUI;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;



public class EntryScene
{
    public static Scene getEntryScene(Color backgroundColor)
    {
        Color bgColor = backgroundColor;

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20,20,20,20));

        TextField input = new TextField("erastdthf");
        Button button =  new Button("earstdyfy");

        layout.getChildren().addAll(input,button);
        layout.setStyle("-fx-background-color: " + FrontTools.ColortoHEX(backgroundColor) +";");
        Scene scene = new Scene(layout,300,200);

        return scene;
    }

}
