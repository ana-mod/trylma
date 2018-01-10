import Connection.GameServer;
import Game.BasicPlayer;
import Game.Board;
import Game.Play;
import Game.Player;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


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

        window.setOnCloseRequest(e -> System.exit(0));

        Scene scene = new Scene(layout, 400,100);
        window.setScene(scene);
        window.show();
    }
}
