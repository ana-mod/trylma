public class Field
{
    private int radius;
    private Piece piece;
    private Field field;

    public Field()
    {
        this.radius=-1;
    }

    public Field(int radius,Field field)
    {
        this.radius=radius;
        this.field = field;
    }
    
    public boolean isFree()
    {
        if (piece == null)
        {
            return true;
        }
        else
        {
            return false;
        }
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
