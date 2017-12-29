package GUI;

import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainWindow extends Application
{
    private Stage window;
    private Color backgroundColor = Color.rgb(255,248,220);

    public static void main(String[] args)
    {
        launch(args);
    }
    public void start (Stage primaryStage) throws Exception
    {
        window = primaryStage;
        EntryScene scene = new EntryScene(new VBox());
        scene.setBackgroundColor("#FFF8DC");
        window.setScene(scene);
        window.show();
    }
}
