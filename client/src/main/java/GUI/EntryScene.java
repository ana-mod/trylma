package GUI;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class EntryScene extends Scene
{
    private Color backgroundColor;
    private Parent layout;

    public EntryScene (Parent root)
    {
        super(root);
        layout = root;
    }

    public void setBackgroundColor(String hex)
    {
        this.layout.setStyle("-fx-background-color: " + hex +";");
    }
}
