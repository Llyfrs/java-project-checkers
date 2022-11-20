package checkers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

public class GameBoardController {
    @FXML
    private Canvas canvas;

    Board board;

    @FXML
    private void initialize() {
        board = new Board(canvas);
        board.draw();

        board.highlight(2,2);
        board.highlight(2,7);
        board.highlight(7,3);

        Pawn pawn = new Pawn(0,0,canvas);

        pawn.draw();
    }

    @FXML
    private void onMouseClicked(MouseEvent mouseEvent){
        System.out.print(Math.floor(mouseEvent.getX() / (canvas.getWidth()  /8)));
        System.out.print(Math.floor(mouseEvent.getY() / (canvas.getHeight() /8)));

        board.highlight((int) Math.floor(mouseEvent.getX() / (canvas.getWidth()  /8)), (int)Math.floor(mouseEvent.getY() / (canvas.getHeight()  /8)));

        System.out.print("\r\n");
    }
}
