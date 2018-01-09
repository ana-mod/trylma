package Connection;

import java.io.Serializable;

class NickAlreadyTaken implements Serializable {}
class TaskCompleted implements Serializable {}
class GetAllGamesInfo implements Serializable {}
class EndOfTransfer implements Serializable {}
class GameAlreadyExists implements Serializable {}
class GetBoard implements Serializable {}

class CreateNewGame implements Serializable
{
    private SingleGameInfo singleGameInfo;
    public CreateNewGame (SingleGameInfo singleGameInfo)
    {
        this.singleGameInfo = singleGameInfo;
    }
    public SingleGameInfo getSingleGameInfo ()
    {
        return singleGameInfo;
    }
}

class ConnectToGame implements Serializable
{
    private SingleGameInfo singleGameInfo;
    public ConnectToGame (SingleGameInfo singleGameInfo) { this.singleGameInfo = singleGameInfo; }
    public SingleGameInfo getSingleGameInfo ()
    {
        return singleGameInfo;
    }
}
