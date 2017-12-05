public class Field
{
    private int radius;
    private Piece piece;

    public boolean isFree()
    {
        return true;
    }

    public int getRadius()
    {
        return this.radius;
    }

    public void setPiece (Piece piece)
    {
        this.piece = piece;
    }

    public Piece getPiece ()
    {
        return piece;
    }
}
