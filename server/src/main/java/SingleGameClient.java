import java.net.ServerSocket;

public class SingleGameServer extends Thread
{
    private ServerSocket serverSocket;
    
    public SingleGameServer (int port) throws IOException
    {
        serverSocket = new ServerSocket(port);
        start();
    }

    @Override
    public void run ()
    {
        
    }
} 
