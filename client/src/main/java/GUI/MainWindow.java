package GUI;

import Game.Board;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MainWindow extends Application
{
    private Stage window;
    private MenuBar menuBar;

    private BorderPane mainLayout;
    private VBox entryLayout;
    private HBox lobbyLayout;


    public static void main(String[] args)
    {
        launch(args);
    }

    public void start (Stage primaryStage) throws Exception
    {
        window = primaryStage;

        mainLayout = new BorderPane();

        prepareMenuBar();
        mainLayout.setTop(menuBar);
        BorderPane.setMargin(menuBar,new Insets(0,0,20,0));

        prepareEntryLayout();
        mainLayout.setCenter(entryLayout);


        //Board Print test


        Scene scene = new Scene(mainLayout,300,200);
        //scene.getStylesheets().add("SceneStyle.css");


        window.setScene(scene);


        window.show();
    }

    private Group boardPrint(Board board)
    {
        Group group = new Group();

        for(int i=1; i < board.rownum; i++)
            for (int j=1; j < board.colnum; j++)
            {
                if(board.board[i][j])
                {
                    Circle circle = new Circle(10.0f);
                    circle.setLayoutX(i * 50.0f);
                    circle.setLayoutY(j * 50.0);

                    group.getChildren().add(circle);

                }
            }

        return group;
    }

    private void prepareMenuBar()
    {
        menuBar = new MenuBar();
        Menu options, gameRules, info;

        options = new Menu("Opcje");
        MenuItem display = new MenuItem("Wyświetlanie");
        MenuItem connection = new MenuItem("Połączenie");
        options.getItems().addAll(display,connection);

        gameRules = new Menu("Zasady Gry");
        info = new Menu("Info");

        menuBar.getMenus().addAll(options, gameRules, info);
    }

    private void prepareEntryLayout()
    {
        entryLayout = new VBox();
        Label nicknameLabel = new Label("Pseudonim :");
        TextField userInput = new TextField();
        Button nextScene = new Button("nextScene");

        entryLayout.getChildren().addAll(nicknameLabel, userInput, nextScene);


    }
}
