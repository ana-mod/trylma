package Connection;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.net.Socket;

public class ClientConnection extends Thread
{
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket socket;

    private String nickname;

    public int getReadyPlayers ()
    {
        return readyPlayers.get();
    }

    public IntegerProperty readyPlayersProperty ()
    {
        return readyPlayers;
    }

    private IntegerProperty readyPlayers;

    public ClientConnection (int port) throws IOException
    {
        this.socket = new Socket("localhost", port);
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run ()
    {
        Read readThread = new Read();
        readThread.start();
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
        if(input.readObject() instanceof GameAlreadyExists)
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

    public Boolean[][] getBoard() throws IOException, ClassNotFoundException
    {
        output.writeObject(new GetBoard());
        return (Boolean[][]) input.readObject();
    }

    public void waitForPlayers()
    {
        WaitForStart waitForStart = new WaitForStart();
        waitForStart.run();
    }

    protected class WaitForStart extends Thread
    {
        @Override
        public void run ()
        {
            readyPlayers = new SimpleIntegerProperty();

            try
            {
                Object msg = input.readObject();
                while ( msg instanceof WaitingForPlayers)
                {
                    readyPlayers.set(((WaitingForPlayers) msg).getReady());
                    msg = input.readObject();
                }
            }
            catch (IOException | ClassNotFoundException e)
            {
            }
        }
    }

    protected class Read extends Thread
    {
        @Override
        public void run ()
        {

        }
    }
}
