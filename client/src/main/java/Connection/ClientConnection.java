package Connection;

import GUI.PopUpWindows.ServerErrorWindow;
import GameInfo.BoardInfo;
import GameInfo.Move;
import GameInfo.SingleGameInfo;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;

public class ClientConnection implements Runnable// extends Thread
{
    public ObjectOutputStream getOutput ()
    {
        return output;
    }

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket socket;
    private String nickname;

    public boolean isIsGameStarted ()
    {
        return isGameStarted.get();
    }

    public BooleanProperty isGameStartedProperty ()
    {
        return isGameStarted;
    }

    public void setIsGameStarted (boolean isGameStarted)
    {
        this.isGameStarted.set(isGameStarted);
    }

    private BooleanProperty isGameStarted = new  SimpleBooleanProperty(false);

    public String getNickname ()
    {
        return nickname;
    }


    public ClientConnection (int port) throws IOException
    {
        this.socket = new Socket("localhost", port);
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run ()
    {
        while(!waitForPlayers());
        isGameStarted.setValue(true);
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
}
