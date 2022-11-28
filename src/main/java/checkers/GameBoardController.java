package checkers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class GameBoardController {
    @FXML
    private Canvas canvas;

    private Game game;

    private Scene previousScene;
    private Stage stage;


    //TODO Connect NewGame button
    //TODO Connect Main Menu button

    @FXML
    private void initialize() {
        game = new Game(canvas);
        game.draw();
    }

    @FXML
    private void onMouseClicked(MouseEvent mouseEvent){
        int x = (int) Math.floor(mouseEvent.getX() / (canvas.getWidth()  /8));
        int y = (int) Math.floor(mouseEvent.getY() / (canvas.getHeight() /8));

        System.out.printf("Clicked detected at: [%d,%d] \n",x,y);

        game.click(x,y);

    }

    @FXML
    private void newGamePressed() {
        this.initialize();
    }

    @FXML
    private void mainMenuPressed() {
        this.stage.setScene(previousScene);
    }


    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
