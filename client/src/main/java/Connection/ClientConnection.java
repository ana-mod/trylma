package Connection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.Socket;

public class ClientConnection extends Thread
{
    private PrintWriter output;
    private BufferedReader input;
    private Socket connection;

    private String nickname;

    private JFrame frame;
    private JTextArea frame_out;
    private JTextField frame_in;


    public ClientConnection (int port) throws IOException
    {
        this.connection = new Socket("localhost", port);
        this.output = new PrintWriter(connection.getOutputStream(), true);
        this.input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        //setNickname(JOptionPane.showInputDialog("Select your nickname:"));
        
        start();
    }

    @Override
    public void run ()
    {
        String msg="";
        do
        {
            try
            {
                setNickname(JOptionPane.showInputDialog("Select your nickname:"));
                msg = input.readLine();
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
        }
        while(msg.equals("errnicktaken"));

        Read t1 = new Read();
        t1.start();
    }

    public void setNickname(String n)
    {
        this.nickname=n;
        output.println(this.nickname);
    }

    public void actionPerformed (ActionEvent e)
    {
        String msg;
        msg = frame_in.getText();
        frame_in.setText("");
        output.println(msg);
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
