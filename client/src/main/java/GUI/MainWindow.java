package GUI;

import Game.Board;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.Vector;

public class MainWindow extends Application
{
    private Stage window;
    private Color backgroundColor;

    public static void main(String[] args)
    {
        launch(args);
    }
    public void start (Stage primaryStage) throws Exception
    {
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

        backgroundColor = Color.rgb(255,248,220);
        Color bgColor = backgroundColor;

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20,20,20,20));

        javafx.scene.control.TextField input = new javafx.scene.control.TextField();
        javafx.scene.control.Button button =  new Button("earstdyfy");

        layout.getChildren().addAll(input,button);

        Group g = new Group();

        for(int i=0;i<circles.size();i++)
            g.getChildren().add(circles.get(i));

        layout.getChildren().add(g);

        layout.setStyle("-fx-background-color: " + FrontTools.ColortoHEX(backgroundColor) +";");
        Scene scene = new Scene(layout,300,200);



        window = primaryStage;
        window.setScene(scene);


        window.show();
    }
}
