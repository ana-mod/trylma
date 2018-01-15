package Connection;

import GUI.PopUpWindows.ServerErrorWindow;
import GameInfo.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;

public class ClientConnection
{
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket socket;
    private String nickname;

    private BooleanProperty isGameStarted = new  SimpleBooleanProperty(false);

    public String getNickname ()
    {
        return nickname;
    }
    private Board board;
    private ReadGameData readGameData = null;

    public void setBoard (Board board)
    {
        this.board = board;
    }

    public ClientConnection (int port) throws IOException
    {
        this.socket = new Socket("localhost", port);
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());
    }

    public boolean setNickname(String nickname) throws ClassNotFoundException
    {
        try
        {
            output.writeObject(nickname);
            Object msg = input.readObject();

            if(msg instanceof NickAlreadyTaken)
            {
                return false;
            }
            else
            {
                this.nickname = nickname;
                return true;
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public ObservableList<SingleGameInfo> getAllGamesInfo () throws IOException, ClassNotFoundException
    {
        output.writeObject(new GetAllGamesInfo());
        output.flush();
        ObservableList<SingleGameInfo> list = FXCollections.observableArrayList();

        Object feedback = input.readObject();
        while (feedback instanceof SingleGameInfo)
        {
            list.add((SingleGameInfo) feedback);
            feedback = input.readObject();
            if(feedback instanceof EndOfTransfer)
                break;
        }

        return list;
    }

    public boolean createNewGame(SingleGameInfo singleGameInfo) throws IOException, ClassNotFoundException
    {
        output.writeObject(new CreateNewGame(singleGameInfo));
        output.writeObject(singleGameInfo);
        Object msg = input.readObject();
        if(msg instanceof GameAlreadyExists)
            return false;
        else
            return true;
    }

    public void connectToGame(SingleGameInfo singleGameInfo) throws IOException, ClassNotFoundException
    {
        output.writeObject(new ConnectToGame(singleGameInfo));
        if(!(input.readObject() instanceof TaskCompleted))
            throw new IOException();
    }

    public BoardInfo getBoard() throws IOException, ClassNotFoundException
    {
        output.writeObject(new GetBoard());
        return (BoardInfo) input.readObject();
    }

    public boolean waitForPlayers()
    {
        try
        {
            return input.readBoolean();
        }
        catch (IOException ex)
        {
            ServerErrorWindow.displayWindow();
            return false;
        }
    }

    public String getActualPlayer() throws IOException, ClassNotFoundException
    {
        output.writeObject(new GetActualPlayer());
        Object msg = input.readObject();
        if(msg instanceof String )
            return (String) msg;
        else
            throw new ClassNotFoundException();
    }

    public boolean sendNewMove(Move move) throws IOException, ClassNotFoundException
    {
        output.writeObject(move);
        return (boolean) input.readObject();
    }

    public void endOfMove() throws IOException, ClassNotFoundException
    {
        output.writeObject(new EndOfMove());
    }

    public Move getMove() throws ClassNotFoundException, IOException
    {
        return (Move) input.readObject();
    }

    public class ReadGameData extends Thread
    {
        @Override
        public void run ()
        {
            while (!isInterrupted())
            {
                try
                {
                    Object msg = input.readObject();
                    if(msg instanceof Move)
                    {
                        Platform.runLater( () -> board.move((Move) msg));
                    }
                    else if (msg instanceof EndOfMove)
                    {
                        String actual = getActualPlayer();
                        board.setActualPlayer(actual);
                        if(actual.equals(nickname))
                            this.interrupt();
                    }
                }
                catch (IOException | ClassNotFoundException ex)
                {
                    break;
                }
            }
        }
    }

    public void startReadGameData()
    {
        if(readGameData!=null)
            if(!readGameData.isInterrupted())
                readGameData.interrupt();

        readGameData = new ReadGameData();
        readGameData.start();
    }

    public boolean isReading()
    {
        if(readGameData!=null)
            return readGameData.isAlive();
        else
            return false;
    }
}
