package GameInfo;

import Connection.ClientConnection;
import GUI.MainWindow;
import GUI.PopUpWindows.ServerErrorWindow;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.HashMap;

public class Board
{
    private Group boardGroup;
    private Group piecesGroup;
    private BoardInfo boardInfo;

    private Color previus = Color.AQUAMARINE;
    private Circle selected = null;

    private HashMap<Integer, Color> colorHashMap = new HashMap<>();

    private Move move = null;
    private ClientConnection player;

    private MainWindow window;

    public Board(BoardInfo boardInfo, ClientConnection clientConnection, MainWindow mainWindow)
    {
        this.boardInfo = boardInfo;
        this.player = clientConnection;
        this.window = mainWindow;

        colorHashMap.put(0,Color.RED);
        colorHashMap.put(1,Color.YELLOW);
        colorHashMap.put(2,Color.GREEN);
        colorHashMap.put(3,Color.BLUE);
        colorHashMap.put(4,Color.ORANGE);
        colorHashMap.put(5,Color.PURPLE);

        boardGroup = new Group();
        piecesGroup = new Group();

        for(int i=0; i<boardInfo.getBoard().length; i++)
            for (int j = 0; j < boardInfo.getBoard()[i].length; j++)
                if (boardInfo.getBoard()[i][j])
                {
                    Circle circle = new Circle(20);
                    circle.setLayoutX(getLayoutXFromIndex(i,j));//(j + 1) * 50 + (i % 2) * 25);
                    circle.setLayoutY(getLayoutYFromIndex(i,j));//(i + 1) * 50);
                    circle.setFill(Color.WHITE);
                    circle.setStroke(Color.BLACK);

                    circle.setOnMouseClicked(e -> {
                        try
                        {
                            Move mv = new Move(getIndexX(selected), getIndexY(selected), getIndexX(circle), getIndexY(circle));
                            Boolean hasMoved = player.sendNewMove(mv);
                            if(hasMoved)
                                this.move(mv);
                        }
                        catch (IOException | ClassNotFoundException ex)
                        {
                            ServerErrorWindow.displayWindow();
                        }
                    });

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

                    circle.setOnMouseClicked(e -> {
                        if(selected == null)
                        {
                            previus = (Color) circle.getFill();
                            circle.setFill(Color.AQUAMARINE);
                            selected = circle;
                        }
                        else if(circle.equals(selected))
                        {
                            selected.setFill(previus);
                            selected=null;
                        }
                        else
                        {
                            selected.setFill(previus);
                            previus = (Color) circle.getFill();
                            circle.setFill(Color.AQUAMARINE);
                            selected = circle;
                        }
                    });

                    piecesGroup.getChildren().add(circle);
                }
            }
    }

    public BorderPane printBoard()
    {
        Group group = new Group();
        group.getChildren().addAll(boardGroup,piecesGroup);
        BorderPane pane = new BorderPane();
        pane.setCenter(group);
        return pane;
    }

    private int getIndexX(Circle circle)
    {
        return ((int)circle.getLayoutY())/50 - 1;
    }

    private int getIndexY(Circle circle)
    {
        return ((int)circle.getLayoutX() - (getIndexX(circle)%2)*25)/50 -1;
    }

    public void move(Move move)
    {
        for(Node c : piecesGroup.getChildren())
        {
            if(c instanceof Circle)
            {
                if(getIndexX((Circle) c) == move.x1 && getIndexY((Circle) c) == move.y1)
                {
                    ((Circle) c).setLayoutX(getLayoutXFromIndex(move.x2, move.y2));
                    ((Circle) c).setLayoutY(getLayoutYFromIndex(move.x2, move.y2));
                    window.repaintGame();
                    return;
                }
            }
        }
    }

    private int getLayoutYFromIndex(int x, int y)
    {
        return (x+1)*50;
    }

    private int getLayoutXFromIndex(int x, int y)
    {
        return (y+1)*50 + (x%2)*25;
    }
}
