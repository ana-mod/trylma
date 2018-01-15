package Game;

import GameInfo.Move;

public interface Player
{
    abstract String getNickname();
    abstract void notify (Object msg);
}
