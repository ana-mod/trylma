public class Row
{
    private Field[] fields;
    private int startIndex;
    private int length;

    public Row(int startIndex, int length)
    {
        this.startIndex=startIndex;
        this.length = length;
    }

    public Field getField(int index)
    {
        return fields[index-startIndex];
    }

    public void setField(int index,Field field)
    {
        this.fields[index-startIndex] = field;
    }
}
