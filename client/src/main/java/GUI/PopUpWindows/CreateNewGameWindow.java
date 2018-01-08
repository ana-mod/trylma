package GUI.PopUpWindows;

import Connection.SingleGameInfo;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateNewGameWindow
{
    private Stage window;
    private VBox layout;
    private Scene scene;
    private Label nameLabel, maxPlayersLabel;
    private TextField nameInput;
    private ChoiceBox<Integer> choiceBox;

    private static CreateNewGameWindow instance;

    private CreateNewGameWindow ()
    {
        window = new Stage();
        layout = new VBox(10);
        layout.setPadding(new Insets(20));

        nameLabel = new Label("Nazwa Gry");
        nameInput = new TextField();
        nameInput.setMinWidth(100);
        HBox h1 = new HBox(10);
        h1.getChildren().addAll(nameLabel, nameInput);

        maxPlayersLabel = new Label("Liczba Graczy");
        choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(2,3,4,6);
        choiceBox.setValue(6);

        HBox h2 = new HBox(10);
        h2.getChildren().addAll(maxPlayersLabel, choiceBox);

        Button create = new Button("Create");
        create.setOnAction(e -> window.close());

        layout.getChildren().addAll(h1,h2);
        scene= new Scene(layout,400,300);
        window.setScene(scene);
    }

    private SingleGameInfo display()
    {
        window.showAndWait();
        return new SingleGameInfo(nameInput.getText(),choiceBox.getValue(),0);
    }

    public static SingleGameInfo displayWindow()
    {
        if (instance != null)
        {
            return  instance.display();
        }
        else
        {
            instance = new CreateNewGameWindow();
            return instance.display();
        }
    }
}
