package Connection;

import javax.swing.*;
import java.awt.event.ActionEvent;
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
        Read t1 = new Read();
        t1.start();
    }

    public boolean checkNickname(String nickname) throws ClassNotFoundException
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
