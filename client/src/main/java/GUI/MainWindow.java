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

        Vector<Circle> circles = new Vector<>();
        Board board = new Board();
        board.createPieces();

        for(int i=1; i < board.rownum; i++)
            for (int j=1; j < board.colnum; j++)
            {
                if(board.board[i][j])
                {
                    Circle circle = new Circle(10.0f);
                    circle.setLayoutX(i * 50.0f);
                    circle.setLayoutY(j * 50.0);


                    circle.setOnMousePressed(e -> circle.setFill(Color.RED));
                    circle.setOnMouseReleased(e -> circle.setFill(Color.BLACK));
                    circles.add(circle);
                }

            }

        Group g = new Group();

        for(int i=0;i<circles.size();i++)
            g.getChildren().add(circles.get(i));







        mainLayout.setCenter(g);

        Scene scene = new Scene(mainLayout,300,200);
        //scene.getStylesheets().add("SceneStyle.css");


        window.setScene(scene);


        window.show();
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
