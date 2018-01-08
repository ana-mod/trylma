import Connection.GameServer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;


public class MainServer extends Application
{
    public static void main(String[] args)
    {
        try
        {
            GameServer s = GameServer.getInstance(4444);
        }
        catch (IOException e)
        {
            System.out.println(e.getStackTrace());
        }

        launch(args);
    }

    @Override
    public void start (Stage primaryStage) throws Exception
    {
        Stage window = primaryStage;

        BorderPane layout = new BorderPane();
        Label label = new Label("Server is now working");
        label.setFont(new Font(36));
        layout.setCenter(label);

        Scene scene = new Scene(layout, 400,100);
        window.setScene(scene);
        window.show();
    }
}
