package GUI;

import GameInfo.BoardInfo;
import GameInfo.SingleGameInfo;
import GUI.PopUpWindows.GameRulesWindow;
import GUI.PopUpWindows.InfoWindow;
import GUI.PopUpWindows.CreateNewGameWindow;
import GUI.PopUpWindows.ServerErrorWindow;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import java.util.HashMap;

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
        window.setTitle("ChineseCheckers");
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
        window.setTitle(window.getTitle() + "-" + clientConnection.getNickname());
        lobbyLayout = new VBox(10);
        lobbyLayout.setPadding(new Insets(10));

        TableView<SingleGameInfo> tableInfoTableView = prepareTableForSingleGameInfo();

        Button newGameButton = new Button("Nowa Gra");
        newGameButton.setOnAction(e -> {
            try{
                SingleGameInfo gameInfo = CreateNewGameWindow.displayWindow();
                if(!clientConnection.createNewGame(gameInfo))
                    System.out.println("gameAlreadyexists");
                clientConnection.connectToGame(gameInfo);
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

        Button refreshButton = new Button("Odśwież");
        refreshButton.setOnAction(e -> {
            try
            {
                setLobbyLayout();
            }
            catch (IOException | ClassNotFoundException ex)
            {
                ServerErrorWindow.displayWindow();
            }

        });

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(newGameButton,joinGameButton, refreshButton);
        lobbyLayout.getChildren().addAll(tableInfoTableView,hBox);
        lobbyLayout.setMinSize(500,800);
        mainLayout.setCenter(lobbyLayout);
        window.setWidth(433);
        window.setHeight(600);
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
        BoardInfo board = clientConnection.getBoard();
        gameLayout = new BorderPane();
        gameLayout.setCenter(boardPrint(board));

        mainLayout.setCenter(gameLayout);

        Label label = new Label("abc");
        label.setText(clientConnection.getActualPlayer());

        mainLayout.setRight(label);
        window.setWidth(720);
        window.setHeight(950);

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

    private BorderPane boardPrint(BoardInfo boardInfo)
    {
        Group boardGroup = new Group();
        Group piecesGroup = new Group();

        HashMap<Integer, Color> colorHashMap = new HashMap<>();
        colorHashMap.put(0,Color.RED);
        colorHashMap.put(1,Color.YELLOW);
        colorHashMap.put(2,Color.GREEN);
        colorHashMap.put(3,Color.BLUE);
        colorHashMap.put(4,Color.ORANGE);
        colorHashMap.put(5,Color.PURPLE);

        for(int i=0; i<boardInfo.getBoard().length; i++)
            for(int j=0; j<boardInfo.getBoard()[i].length; j++)
                if(boardInfo.getBoard()[i][j])
                {
                    Circle circle = new Circle(20);
                    circle.setLayoutX((j+1) * 50 + (i%2)*25);
                    circle.setLayoutY((i+1) * 50);
                    circle.setFill(Color.WHITE);
                    circle.setStroke(Color.BLACK);
                    boardGroup.getChildren().add(circle);
                }

        for(int i=0; i<boardInfo.getPieces().length; i++)
            for(int j=0; j<boardInfo.getPieces()[0].length; j++)
            {
                if(boardInfo.getPieces()[i][j] >=0 )
                {
                    Circle circle = new Circle(20);
                    circle.setLayoutX((j + 1) * 50 + (i % 2) * 25);
                    circle.setLayoutY((i + 1) * 50);
                    circle.setStroke(Color.BLACK);
                    circle.setFill(colorHashMap.get(boardInfo.getPieces()[i][j]));
                    piecesGroup.getChildren().add(circle);
                }
            }

        boardGroup.getChildren().addAll(piecesGroup);
        BorderPane pane = new BorderPane();
        pane.setCenter(boardGroup);

        return pane;
    }
}