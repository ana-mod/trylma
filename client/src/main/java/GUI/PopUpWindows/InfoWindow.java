package GUI.PopUpWindows;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InfoWindow
{
    private static InfoWindow instance;

    private Stage window;
    private Scene scene;
    private VBox layout;
    private Label messageBox;

    private String message = "aaaa info message";

    private InfoWindow()
    {
        window = new Stage();
        window.setTitle("Info");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(250);

        messageBox = new Label(message);

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
            instance = new InfoWindow();
            instance.display();
        }
    }

    private void display()
    {
        window.showAndWait();
    }
}
