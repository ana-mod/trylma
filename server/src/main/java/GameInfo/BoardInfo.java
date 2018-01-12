package GameInfo;

import java.io.Serializable;

public class BoardInfo implements Serializable
{
    private boolean[][] board;
    private int[][] pieces;

    public BoardInfo (boolean[][] board, int[][] pieces)
    {
        this.board = board;
        this.pieces = pieces;
    }

    public boolean[][] getBoard ()
    {
        return board;
    }

    public void setBoard (boolean[][] board)
    {
        this.board = board;
    }

    public int[][] getPieces ()
    {
        return pieces;
    }

    public void setPieces (int[][] pieces)
    {
        this.pieces = pieces;
    }
}