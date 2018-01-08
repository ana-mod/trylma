package Connection;

import java.io.Serializable;

class NickAlreadyTaken implements Serializable {}
class TaskCompleted implements Serializable {}
class GetAllGamesInfo implements Serializable {}
class EndOfTransfer implements Serializable {}

class CreateNewGame implements Serializable
{
    private GameTableInfo gameTableInfo;
    public CreateNewGame (GameTableInfo gameTableInfo)
    {
        this.gameTableInfo = gameTableInfo;
    }
    public GameTableInfo getGameTableInfo ()
    {
        return gameTableInfo;
    }
}
