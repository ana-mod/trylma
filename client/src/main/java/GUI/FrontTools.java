package GUI;

import javafx.scene.paint.Color;

public class FrontTools
{
    public static String RGBtoHEX(int red, int green, int blue)
    {
        return String.format( "#%02X%02X%02X",
                red * 255,
                green * 255,
                blue * 255);
    }

    public static Color HEXtoColor(String hex)
    {
        hex = hex.substring(1);
        int red = Integer.parseInt(hex.substring(0,2),16);
        int green = Integer.parseInt(hex.substring(2,4),16);
        int blue = Integer.parseInt(hex.substring(4,6),16);

        return Color.rgb(red,green,blue);
    }

    public static String ColortoHEX(Color c)
    {
        return String.format( "#%02X%02X%02X",
                (int)( c.getRed() * 255 ),
                (int)( c.getGreen() * 255 ),
                (int)( c.getBlue() * 255 ) );
    }
}
