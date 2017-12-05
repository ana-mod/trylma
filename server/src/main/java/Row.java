public class Row
{
    private Field[] fields;
    private int startIndex;
    private int length;

    public Field getField(int index)
    {
        return fields[index];
    }

    public void setField(int index,Field field)
    {
        this.fields[index] = field;
    }
}
