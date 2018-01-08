package Connection;

import java.io.Serializable;

public class GameTableInfo implements Serializable
{
    private String title;
    private int maxPlayers, currentPlayers;

    public GameTableInfo (String title, int maxPlayers, int currentPlayers)
    {
        this.title = title;
        this.maxPlayers = maxPlayers;
        this.currentPlayers = currentPlayers;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public int getMaxPlayers ()
    {
        return maxPlayers;
    }

    public void setMaxPlayers (int maxPlayers)
    {
        this.maxPlayers = maxPlayers;
    }

    public int getCurrentPlayers ()
    {
        return currentPlayers;
    }

    public void setCurrentPlayers (int currentPlayers)
    {
        this.currentPlayers = currentPlayers;
    }

}
