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

    public void addConnectionToSubServer(Socket connection)
    {
        clietConnections.add(new ClientHandler(connection));
    }

    protected class ClientHandler extends Thread implements Player
    {
        private Socket socket;
        private ObjectInputStream input;
        private ObjectOutputStream output;

        private String nickname;
        private Play game;

        public String getNickname ()
        {
            return nickname;
        }

        public ClientHandler (Socket socket)
        {
            try
            {
                this.socket = socket;
                this.output = new ObjectOutputStream(socket.getOutputStream());
                this.input = new ObjectInputStream(socket.getInputStream());

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
                            createNewGame(this,(SingleGameInfo) msg);
                        }
                        //waitForOtherPlayers();
                    }
                    else if(msg instanceof ConnectToGame)
                    {
                        Play p = null;
                        for(Play play : games)
                        {
                            if(play.getTitle().equals(((ConnectToGame) msg).getSingleGameInfo().getTitle()))
                            {
                                p=play;
                                break;
                            }
                        }
                        connectToGame(p);
                        //waitForOtherPlayers();
                    }
                    else if(msg instanceof GetBoard)
                    {
                        sendBoard(this);

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

        private void connectToGame(Play play) throws IOException
        {
            this.game = play;
            game.addPlayer(this);
            output.writeObject(new TaskCompleted());
        }

        private void waitForOtherPlayers() throws IOException, InterruptedException
        {
            while(!game.isStarted())
            {
                output.writeBoolean(game.isStarted());
                this.sleep(1000);
            }
        }
    }

    private void createNewGame(ClientHandler clientHandler, SingleGameInfo singleGameInfo) throws IOException
    {
        for(Play p : games)
            if(p.getTitle().equals(singleGameInfo.getTitle()))
            {
                clientHandler.output.writeObject(new GameAlreadyExists());
                return;
            }

        Play p = new Play(singleGameInfo.getMaxPlayers());
        p.setTitle(singleGameInfo.getTitle());
        games.add(p);
        clientHandler.output.writeObject(new TaskCompleted());
    }



    private void sendGamesInfo(ClientHandler clientHandler) throws IOException
    {
        for(Play p : games)
            clientHandler.output.writeObject(new SingleGameInfo(p.getTitle(),p.getNumberOfPlayers(), p.players.size()));
        clientHandler.output.writeObject(new EndOfTransfer());
    }

    private void sendBoard(ClientHandler clientHandler) throws IOException
    {
        boolean[][] board = clientHandler.game.getBoard().getBoard();
        Boolean[][] boardWrapper = new Boolean[board.length][board[0].length];

        for (int i=0; i <board.length; i++)
            for (int j=0; j<board[i].length; j++)
                boardWrapper[i][j] = board[i][j];

        clientHandler.output.writeObject(boardWrapper);
    }
}