 import java.io.IOException;

public class TMPmain
{
    public static void main(String[] args)
    {
        try
        {
            ServerConnectionTest s = new ServerConnectionTest(4444);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
