import Game.Play;
import Game.Player;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

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
        private Socket connection;
        private BufferedReader input;
        private PrintWriter output;

        private String nickname;

        public ClientHandler (Socket connection)
        {
            try
            {
                this.connection = connection;
                this.input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                this.output = new PrintWriter(connection.getOutputStream(), true);

                setNickname();
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
            start();
        }

        public void setNickname () throws IOException
        {
            this.nickname = "";

            while(this.nickname.equals(""))
            {
                String msg = input.readLine();
                boolean free = true;

                for (ClientHandler ch : clietConnections)
                {
                    if (msg.equals(ch.getNickname()) && !ch.equals(this))
                    {
                        output.println("errnicktaken");
                        free = false;
                    }
                }

                if(free)
                {
                    output.println("done");
                    this.nickname = msg;
                }
            }


            System.out.println(nickname + " taken");
        }

        public String getNickname ()
        {
            return nickname;
        }

        public void run ()
        {
            String msg;
            while (!isInterrupted())
            {
                try
                {
                    msg = input.readLine();
                    if(msg == null)
                    {
                        clietConnections.remove(clietConnections.indexOf(this));
                        break;
                    }

                    msg = "~"+ nickname + ": " + msg;

                    for (ClientHandler sub : clietConnections)
                    {
                        sub.output.println(msg);
                    }
                }
                catch (IOException e)
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
