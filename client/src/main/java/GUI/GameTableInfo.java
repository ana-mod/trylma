package GUI;

public class GameTableInfo
{
    private String title;
    private int maxPlayers;
    private int currentPlayers;

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

    public GameTableInfo (int currentPlayers, int maxPlayers)
    {
        title = "Gra";
        this.maxPlayers = maxPlayers;
        this.currentPlayers = currentPlayers;
    }


}
