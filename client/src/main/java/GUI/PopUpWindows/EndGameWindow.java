package GUI.PopUpWindows;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EndGameWindow
{
    private static EndGameWindow instance;

    private Stage window;
    private Scene scene;
    private VBox layout;
    private Label messageBox;

    private String message = "Koniec gry";

    private EndGameWindow()
    {
        window = new Stage();
        window.setTitle("Koniec Gry");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(250);

        window.setOnCloseRequest(e -> System.exit(-1));

        messageBox = new Label(message);

        Button exitButton = new Button("wyjscie");
        exitButton.setOnAction(e -> System.exit(-1));

        layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(messageBox, exitButton);

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
            synchronized (GUI.PopUpWindows.ServerErrorWindow.class)
            {
                if (instance == null)
                {
                    instance = new EndGameWindow();
                }
            }
            instance.display();
        }
    }

    private void display()
    {
        window.showAndWait();
    }
}
