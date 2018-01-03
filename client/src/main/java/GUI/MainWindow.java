package GUI;

import Game.Board;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.Vector;

public class MainWindow extends Application
{
    private Stage window;
    private MenuBar menuBar;

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start (Stage primaryStage) throws Exception
    {
        window = primaryStage;

        BorderPane mainLayout = new BorderPane();

        prepareMenuBar();
        mainLayout.setTop(menuBar);
        BorderPane.setMargin(menuBar,new Insets(0,0,20,0));

        //Board Print test
        Board board = new Board();
        board.createPieces();
        Group boardPrint = boardPrint(board);
        mainLayout.setCenter(boardPrint);

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
        gameRules = new Menu("Zasady Gry");
        info = new Menu("Info");

        menuBar.getMenus().addAll(options, gameRules, info);
    }
}
