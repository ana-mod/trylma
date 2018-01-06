package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import Game.Board;

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

        Scene scene = new Scene(mainLayout,300,200);
        scene.getStylesheets().add("SceneStyle.css");

        window.setScene(scene);

        window.show();
    }

    private Group boardPrint(Board board)
    {
        Group group = new Group();

        for(int i=0; i<board.getRownum(); i++)
            for(int j=0; j<board.getColnum(); j++)

                if(board.getBoard()[i][j])
                {
                    Circle circle = new Circle(20);
                    circle.setLayoutX((j+1) * 50 + (i%2)*25);
                    circle.setLayoutY((i+1) * 50);
                    circle.setFill(Color.WHITE);
                    circle.setStroke(Color.BLACK);
                    circle.setOnMouseClicked(e -> circle.setFill(Color.GREEN));
                    group.getChildren().add(circle);
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

        Label infoWorkaround = new Label("Info");
        info = new Menu("", infoWorkaround);
        infoWorkaround.setOnMouseClicked(e -> InfoWindow.displayWindow());

        menuBar.getMenus().addAll(options, gameRules, info);
    }

    private void prepareEntryLayout()
    {
        entryLayout = new VBox(10);
        entryLayout.setPadding(new Insets(10));
        Label nicknameLabel = new Label("Pseudonim :");
        TextField userInput = new TextField();
        userInput.setMaxWidth(200);
        Button nextScene = new Button("nextScene");

        //test board
        Board b = new Board();
        b.createPieces();

        nextScene.setOnAction(e -> mainLayout.setCenter(boardPrint(b)));

        entryLayout.getChildren().addAll(nicknameLabel, userInput, nextScene);


    }
}
