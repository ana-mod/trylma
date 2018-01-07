package GUI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import Game.Board;
import Connection.ClientConnection;

import java.io.IOException;

public class MainWindow extends Application
{
    private Stage window;
    private MenuBar menuBar;

    private BorderPane mainLayout;
    private VBox entryLayout;
    private VBox lobbyLayout;

    private ClientConnection clientConnection;

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start (Stage primaryStage) throws Exception
    {
        try
        {
            clientConnection = new ClientConnection(4444);
        }
        catch (IOException e)
        {
            ServerErrorWindow.displayWindow();
        }

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

        Label errorLabel = new Label("Pseudonim jest już zajęty");
        errorLabel.setTextFill(Color.RED);

        TextField userInput = new TextField();
        userInput.setOnAction(e -> {
            if(clientConnection.checkNickname(userInput.getText()))
            {
                prepareLobbyLayout();
                mainLayout.setCenter(lobbyLayout);
            }
            else
            {
                userInput.setText("");
                if(!entryLayout.getChildren().contains(errorLabel))
                    entryLayout.getChildren().add(errorLabel);
            }

        });
        userInput.setMaxWidth(200);

        entryLayout.getChildren().addAll(nicknameLabel, userInput);

    }

    private void prepareLobbyLayout()
    {
        lobbyLayout = new VBox(10);
        lobbyLayout.setPadding(new Insets(10));

        TableView<GameTableInfo> tableInfoTableView = new TableView<>();

        TableColumn<GameTableInfo, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<GameTableInfo, Integer> playersColumn = new TableColumn<>("Players");
        playersColumn.setMinWidth(100);
        playersColumn.setCellValueFactory(new PropertyValueFactory<>("currentPlayers"));

        TableColumn<GameTableInfo, Integer> maxColumn = new TableColumn<>("Max");
        maxColumn.setMinWidth(100);
        maxColumn.setCellValueFactory(new PropertyValueFactory<>("maxPlayers"));

        tableInfoTableView.setItems(tmp());
        tableInfoTableView.getColumns().addAll(nameColumn, playersColumn, maxColumn);
        tableInfoTableView.setMaxHeight(600);
        tableInfoTableView.setMaxWidth(403);

        Button newGameButton = new Button("Nowa Gra");
        Button joinGameButton = new Button("Połącz");

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(newGameButton,joinGameButton);

        lobbyLayout.getChildren().addAll(tableInfoTableView,hBox);

    }

    private ObservableList<GameTableInfo> tmp()
    {
        ObservableList<GameTableInfo> list = FXCollections.observableArrayList();
        list.addAll(new GameTableInfo(3,3));
        list.addAll(new GameTableInfo(1,3));
        list.addAll(new GameTableInfo(4,6));
        list.addAll(new GameTableInfo(2,4));
        list.addAll(new GameTableInfo(2,2));

        return list;
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

}
