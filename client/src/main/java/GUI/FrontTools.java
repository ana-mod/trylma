package GUI;

public class FrontTools
{
    public static String RGBtoHEX(int red, int green, int blue)
    {
        StringBuilder sb = new StringBuilder();
        sb.append('#');
        sb.append(Integer.toHexString(red));
        sb.append(Integer.toHexString(green));
        sb.append(Integer.toHexString(blue));

        return sb.toString();
    }
}
