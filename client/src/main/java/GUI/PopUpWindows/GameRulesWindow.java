package GUI.PopUpWindows;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameRulesWindow
{
    private static GameRulesWindow instance;

    private Stage window;
    private Scene scene;
    private VBox layout;
    private Label messageBox;

    private String message = "Celem gry jest przeprowadzenie swoich pionków do przeciwległego narożnika planszy." +
            "Można przesuwać pionek o jedno pole albo przeskakiwać sąsiadujące pionki." +
            "Po przeskoczeniu jednego pionka można przeskoczyć kolejny." +
            "Nie ma zbijania pionów przeciwnika a wygrywa ten z graczy, który jako pierwszy przeprowadzi wszystkie swoje pionki." +
            "\n\nŹródło : pl.wikipedia.org";

    private GameRulesWindow()
    {
        window = new Stage();
        window.setTitle("Zasady Gry");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(250);

        messageBox = new Label(message);
        messageBox.setMaxWidth(230);
        messageBox.setWrapText(true);

        layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().add(messageBox);

        scene = new Scene(layout);
        scene.getStylesheets().add("SceneStyle.css");

        window.setScene(scene);
    }

    public static void displayWindow()
    {
        if (instance != null)
        {
            instance.display();
        }
        else
        {
            instance = new GameRulesWindow();
            instance.display();
        }
    }

    private void display()
    {
        window.showAndWait();
    }
}
