package Game;

import GameInfo.Move;

public interface Player
{
    abstract void move();
    abstract String getNickname();
    abstract void notify (Move mv);
}
