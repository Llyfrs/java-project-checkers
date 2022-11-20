package checkers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class GameBoardController {
    @FXML
    private Canvas canvas;

    //TODO Connect NewGame button
    //TODO Connect Main Menu button

    @FXML
    private void initialize() {
        Game game = new Game(canvas);

        game.draw();
    }

    @FXML
    private void onMouseClicked(MouseEvent mouseEvent){
        System.out.print(Math.floor(mouseEvent.getX() / (canvas.getWidth()  /8)));
        System.out.print(Math.floor(mouseEvent.getY() / (canvas.getHeight() /8)));

    }
}
