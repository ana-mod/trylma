package GUI.OtherWindows;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ServerErrorWindow
{
    private static ServerErrorWindow instance;

    private Stage window;
    private Scene scene;
    private VBox layout;
    private Label messageBox;

    private String message = "aaaa error message";

    private ServerErrorWindow()
    {
        window = new Stage();
        window.setTitle("Server Connection Error");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(250);

        window.setOnCloseRequest(e -> System.exit(-1));

        messageBox = new Label(message);

        Button exitButton = new Button("exit");
        exitButton.setOnAction(e -> System.exit(-1));

        layout = new VBox();
        layout.getChildren().addAll(messageBox, exitButton);

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
            synchronized (ServerErrorWindow.class)
            {
                if (instance == null)
                {
                    instance = new ServerErrorWindow();
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
