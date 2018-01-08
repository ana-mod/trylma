package Connection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class ClientConnection extends Thread
{
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket connection;

    private String nickname;

    private JFrame frame;
    private JTextArea frame_out;
    private JTextField frame_in;


    public ClientConnection (int port) throws IOException
    {
        this.connection = new Socket("localhost", port);
        this.output = new ObjectOutputStream(connection.getOutputStream());
        this.input = new ObjectInputStream(connection.getInputStream());
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

                if(msg instanceof NickTaken)
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

    public ObservableList<GameTableInfo> getGamesInfo() throws IOException, ClassNotFoundException
    {
        output.writeObject(new GetGamesInfo());
        output.flush();
        ObservableList<GameTableInfo> list = FXCollections.observableArrayList();

        Object recived = input.readObject();
        while (recived instanceof GameTableInfo)
        {
            list.add((GameTableInfo) recived);
            recived = null;
            recived = input.readObject();
            if(recived instanceof EndOfTransfer)
                break;
        }

        return list;
    }

    public void createNewGame(GameTableInfo gameTableInfo) throws IOException
    {
        output.writeObject(new CreateNewGame(gameTableInfo));
        output.writeObject(gameTableInfo);
    }



    protected class Read extends Thread
    {
        @Override
        public void run ()
        {
            StringBuilder sb = new StringBuilder();
            while (!isInterrupted())
            {
                try
                {
                    sb.append(input.readLine());
                    sb.append('\n');
                    frame_out.setText(sb.toString());
                }
                catch (IOException e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
