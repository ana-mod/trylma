package GUI;

import javafx.scene.paint.Color;

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


    public static Color HEXtoColor(String hex)
    {
        hex = hex.substring(1);
        int red = Integer.parseInt(hex.substring(0,2),16);
        int green = Integer.parseInt(hex.substring(2,4),16);
        int blue = Integer.parseInt(hex.substring(4,6),16);

        return Color.rgb(red,green,blue);
    }
}
