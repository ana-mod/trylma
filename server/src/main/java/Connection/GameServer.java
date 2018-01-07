package Connection;

import Game.Play;
import Game.Player;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class GameServer extends Thread
{
    private ServerSocket serverSocket;
    private Vector<ClientHandler> clietConnections = new Vector<ClientHandler>();
    private Vector<Play> games = new Vector<Play>();

    private static GameServer instance;

    private GameServer (int port) throws IOException
    {
        serverSocket = new ServerSocket(port);
        start();
    }

    public static GameServer getInstance(int port) throws IOException
    {
        if (instance != null)
        {
            return instance;
        }
        else
        {
            synchronized (GameServer.class)
            {
                if (instance == null)
                {
                    instance = new GameServer(port);
                }
            }
            return instance;
        }
    }

    @Override
    public void run ()
    {
        try
        {
            while (!this.isInterrupted())
            {
                Socket connection = this.serverSocket.accept();
                addConnectionToSubServer(connection);
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void sendGamesInfo(ClientHandler clientHandler) throws IOException
    {
            //clientHandler.output.writeObject(new GameTableInfo(1,2));
            //clientHandler.output.flush();
            clientHandler.output.writeObject(new EndOfTransfer());
    }
    public void addConnectionToSubServer(Socket connection)
    {
        clietConnections.add(new ClientHandler(connection));
    }

    protected class ClientHandler extends Thread implements Player
    {
        private Socket connection;
        private ObjectInputStream input;
        private ObjectOutputStream output;

        private String nickname;

        public ClientHandler (Socket connection)
        {
            try
            {
                this.connection = connection;
                this.output = new ObjectOutputStream(connection.getOutputStream());
                this.input = new ObjectInputStream(connection.getInputStream());

                setNickname();
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
            catch (ClassNotFoundException e)
            {
                System.out.println(e.getMessage());
            }
            start();
        }

        public void setNickname () throws IOException, ClassNotFoundException
        {
            this.nickname = "";

            while(this.nickname.equals(""))
            {
                String msg = (String)input.readObject();
                boolean free = true;

                for (ClientHandler ch : clietConnections)
                {
                    if (msg.equals(ch.getNickname()) && !ch.equals(this))
                    {
                        output.writeObject(new NickTaken());
                        free = false;
                    }
                }

                if(free)
                {
                    output.writeObject(new Success());
                    this.nickname = msg;
                }
            }
        }

        public String getNickname ()
        {
            return nickname;
        }

        public void run ()
        {
            Object msg;
            while (!isInterrupted())
            {
                try
                {
                    msg = input.readObject();
                    if(msg == null)
                    {
                        clietConnections.remove(clietConnections.indexOf(this));
                        break;
                    }
                    if(msg instanceof GetGamesInfo)
                    {
                        sendGamesInfo(this);
                    }
                }
                catch (IOException e)
                {
                    System.out.println(e.getMessage());
                }
                catch (ClassNotFoundException e)
                {
                    System.out.println(e.getMessage());
                }
            }

        }

        public void move ()
        {

        }
    }
}
