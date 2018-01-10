package GUI;

import Connection.SingleGameInfo;
import GUI.PopUpWindows.GameRulesWindow;
import GUI.PopUpWindows.InfoWindow;
import GUI.PopUpWindows.CreateNewGameWindow;
import GUI.PopUpWindows.ServerErrorWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
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
import Connection.ClientConnection;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class MainWindow extends Application
{
    private Stage window;
    private MenuBar menuBar;

    private BorderPane mainLayout;
    private VBox entryLayout;
    private VBox lobbyLayout;
    private BorderPane gameLayout;
    private BorderPane waitLayout;

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

        setMenuBar();
        setEntryLayout();

        Scene scene = new Scene(mainLayout,300,200);
        scene.getStylesheets().add("SceneStyle.css");
        window.setScene(scene);
        window.show();
    }

    private void setMenuBar ()
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
        mainLayout.setTop(menuBar);
        BorderPane.setMargin(menuBar,new Insets(0,0,20,0));
    }

    private void setEntryLayout ()
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
                    setLobbyLayout();
                }
                else
                {
                    userInput.setText("");
                    if(!entryLayout.getChildren().contains(errorLabel))
                        entryLayout.getChildren().add(errorLabel);
                }
            }
            catch (IOException | ClassNotFoundException ex)
            {
                ServerErrorWindow.displayWindow();
            }
        });
        userInput.setMaxWidth(200);

        entryLayout.getChildren().addAll(nicknameLabel, userInput);
        mainLayout.setCenter(entryLayout);
    }

    private void setLobbyLayout () throws ClassNotFoundException, IOException
    {
        lobbyLayout = new VBox(10);
        lobbyLayout.setPadding(new Insets(10));

        TableView<SingleGameInfo> tableInfoTableView = prepareTableForSingleGameInfo();

        Button newGameButton = new Button("Nowa Gra");
        newGameButton.setOnAction(e -> {
            try{
                if(!clientConnection.createNewGame(CreateNewGameWindow.displayWindow()))
                    System.out.println("gameAlreadyexists");
                setWaitLayout();
            }catch (IOException | ClassNotFoundException | InterruptedException ex)
            {
                ServerErrorWindow.displayWindow();
            }

        });

        Button joinGameButton = new Button("Połącz");
        joinGameButton.setOnAction(e -> {
            try
            {
                clientConnection.connectToGame(tableInfoTableView.getSelectionModel().getSelectedItem());
                setWaitLayout();
            }
            catch (IOException | ClassNotFoundException | InterruptedException ex)
            {
                ServerErrorWindow.displayWindow();
            }
        });

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(newGameButton,joinGameButton);
        lobbyLayout.getChildren().addAll(tableInfoTableView,hBox);
        mainLayout.setCenter(lobbyLayout);
    }

    private void setWaitLayout() throws IOException, ClassNotFoundException, InterruptedException
    {
        Label label = new Label("Czekanie na innych graczy");//, gotowych: ");
        Label numberLabel = new Label(clientConnection.getReadyPlayers() + "");
        HBox hBox = new HBox();
        hBox.getChildren().addAll(label,numberLabel);
        BooleanProperty isRdy = new SimpleBooleanProperty(false);

        //numberLabel.textProperty().bind(clientConnection.readyPlayersProperty().asString());

        clientConnection.readyPlayersProperty().addListener( (v, oldV, newV) -> {
            numberLabel.setText(newV + "");
        });

        waitLayout = new BorderPane();
        waitLayout.setCenter(hBox);

        mainLayout.setCenter(waitLayout);
/*
        Task task = new Task<Void>() {
            @Override public Void call() {

                return null;
            }
        };*/
            Thread.sleep(15000);
            setGameLayout();

/*
        Platform.runLater(() -> {
            while(!clientConnection.waitForPlayers())
                numberLabel.setText(clientConnection.getReadyPlayers()+"");
        });*/
    }

    private void setGameLayout() throws IOException, ClassNotFoundException
    {
        Boolean[][] board = clientConnection.getBoard();
        gameLayout = new BorderPane();
        gameLayout.setCenter(boardPrint(board));

        mainLayout.setCenter(gameLayout);

    }

    private TableView<SingleGameInfo> prepareTableForSingleGameInfo() throws ClassNotFoundException, IOException
    {
        TableView<SingleGameInfo> tableInfoTableView = new TableView<>();

        TableColumn<SingleGameInfo, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<SingleGameInfo, Integer> playersColumn = new TableColumn<>("Players");
        playersColumn.setMinWidth(100);
        playersColumn.setCellValueFactory(new PropertyValueFactory<>("currentPlayers"));

        TableColumn<SingleGameInfo, Integer> maxColumn = new TableColumn<>("Max");
        maxColumn.setMinWidth(100);
        maxColumn.setCellValueFactory(new PropertyValueFactory<>("maxPlayers"));

        tableInfoTableView.setItems(clientConnection.getAllGamesInfo());
        tableInfoTableView.getColumns().addAll(nameColumn, playersColumn, maxColumn);
        tableInfoTableView.setMaxHeight(600);
        tableInfoTableView.setMaxWidth(403);

        return tableInfoTableView;
    }

    private Group boardPrint(Boolean[][] board)
    {
        Group group = new Group();

        for(int i=0; i<board.length; i++)
            for(int j=0; j<board[i].length; j++)

                if(board[i][j])
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