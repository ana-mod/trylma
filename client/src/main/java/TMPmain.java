import javax.swing.*;
import java.io.IOException;

public class TMPmain
{
    public static void main(String[] args) throws IOException
    {
        String nick = JOptionPane.showInputDialog("Select your nickname:");
        ClientConnection c = new ClientConnection(4444,nick);
    }
}
