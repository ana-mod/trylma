package GUI;

import Connection.GameTableInfo;
import GUI.OtherWindows.GameRulesWindow;
import GUI.OtherWindows.InfoWindow;
import GUI.OtherWindows.NewGameWindow;
import GUI.OtherWindows.ServerErrorWindow;
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


        Label gameRulesWorkaround = new Label("Zasady Gry");
        gameRules = new Menu("", gameRulesWorkaround);
        gameRulesWorkaround.setOnMouseClicked(e -> GameRulesWindow.displayWindow());


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
            try
            {
                boolean isFree = clientConnection.setNickname(userInput.getText());
                if(isFree)
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
            }
            catch (IOException er)
            {
                ServerErrorWindow.displayWindow();
            }
            catch (ClassNotFoundException er)
            {
                ServerErrorWindow.displayWindow();
            }
        });
        userInput.setMaxWidth(200);

        entryLayout.getChildren().addAll(nicknameLabel, userInput);

    }

    private void prepareLobbyLayout() throws ClassNotFoundException, IOException, IOException
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

        tableInfoTableView.setItems(clientConnection.getGamesInfo());
        tableInfoTableView.getColumns().addAll(nameColumn, playersColumn, maxColumn);
        tableInfoTableView.setMaxHeight(600);
        tableInfoTableView.setMaxWidth(403);

        Button newGameButton = new Button("Nowa Gra");
        newGameButton.setOnAction(e -> {
            try{
                clientConnection.createNewGame(NewGameWindow.displayWindow());
                tableInfoTableView.setItems(clientConnection.getGamesInfo());
            }catch (IOException | ClassNotFoundException ex)
            {

            }

        });

        Button joinGameButton = new Button("Połącz");

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(newGameButton,joinGameButton);

        lobbyLayout.getChildren().addAll(tableInfoTableView,hBox);

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
