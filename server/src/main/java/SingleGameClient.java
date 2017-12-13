import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class SingleGameServer extends Thread
{
    private ServerSocket serverSocket;
    private Vector<SubServer> clietConnections = new Vector<SubServer>();
    
    public SingleGameServer (int port) throws IOException
    {
        serverSocket = new ServerSocket(port);
        start();
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
        clietConnections.add(new SubServer(connection));
    }
    
    protected class SubServer extends Thread
    {
        private Socket connection;
        private BufferedReader input;
        private PrintWriter output;
        private String nickname;

        public SubServer(Socket connection)
        {
            try
            {
                this.connection = connection;
                this.input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                this.output = new PrintWriter(connection.getOutputStream(), true);
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
            start();
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

                    for (SubServer sub : clietConnections)
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
    }
} 
