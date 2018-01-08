package Connection;

import Game.Play;
import Game.Player;

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
            for(Play p : games)
                clientHandler.output.writeObject(new SingleGameInfo("haha",p.getNumberOfPlayers(), p.players.size()));
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
        public String getNickname ()
        {
            return nickname;
        }

        public ClientHandler (Socket connection)
        {
            try
            {
                this.connection = connection;
                this.output = new ObjectOutputStream(connection.getOutputStream());
                this.input = new ObjectInputStream(connection.getInputStream());

                setNickname();
            }
            catch (IOException | ClassNotFoundException e)
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
                        output.writeObject(new NickAlreadyTaken());
                        free = false;
                    }
                }

                if(free)
                {
                    output.writeObject(new TaskCompleted());
                    this.nickname = msg;
                }
            }
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
                    else if(msg instanceof GetAllGamesInfo)
                    {
                        sendGamesInfo(this);
                    }
                    else if(msg instanceof CreateNewGame)
                    {
                        msg=input.readObject();
                        if(msg instanceof SingleGameInfo)
                        {
                            games.add(new Play(((SingleGameInfo) msg).getMaxPlayers()));
                        }
                    }
                }
                catch (IOException | ClassNotFoundException e)
                {
                    System.out.println(e.getMessage());
                    break;
                }
            }

        }



        public void move ()
        {

        }
    }
}
