package GUI.PopUpWindows;

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

    private String message = "aaaa zasady gry";

    private GameRulesWindow()
    {
        window = new Stage();
        window.setTitle("Zasady Gry");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(250);

        messageBox = new Label(message);

        layout = new VBox();
        layout.getChildren().add(messageBox);

        scene = new Scene(layout);

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
