package checkers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class GameBoardController {
    @FXML
    private Canvas canvas;

    private Game game;

    private Scene previousScene;
    private Stage stage;


    @FXML
    Text gameOverText;
    @FXML
    private Text whiteScoreText;
    @FXML
    private Text blackScoreText;

    @FXML
    Rectangle whiteRectangle;

    @FXML
    Rectangle blackRectangle;

    @FXML
    private void initialize() {
        Score score = new Score(whiteScoreText,blackScoreText,0,0);
        game = new Game(canvas,score);
        game.draw();
    }

    @FXML
    private void onMouseClicked(MouseEvent mouseEvent){
        int x = (int) Math.floor(mouseEvent.getX() / (canvas.getWidth()  /8));
        int y = (int) Math.floor(mouseEvent.getY() / (canvas.getHeight() /8));

        System.out.printf("Clicked detected at: [%d,%d] \n",x,y);

        game.click(x,y);

        blackRectangle.setFill(game.isRound() ? Color.valueOf("#fccf98") : Color.valueOf("#f1ff87"));
        whiteRectangle.setFill(game.isRound() ? Color.valueOf("#f1ff87") : Color.valueOf("#fccf98"));

        if(game.isGameOver()) {
            gameOverText.setVisible(true);
            if(game.isRound()) {
                gameOverText.setText("Black Won, Congratulation!");
            } else {
                gameOverText.setText("White Won, Congratulation!");
            }

        }

    }

    @FXML
    private void newGamePressed() {
        gameOverText.setVisible(false);
        this.initialize();
    }

    @FXML
    private void mainMenuPressed() {
        SaveManager saveManager = new SaveManager();

        System.out.println("Saving the Game");
        saveManager.save(game);

        this.stage.setScene(previousScene);
    }


    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void loadLastGame() {
        SaveManager saveManager = new SaveManager();
        saveManager.load(game,whiteScoreText,blackScoreText);

        blackRectangle.setFill(game.isRound() ? Color.valueOf("#fccf98") : Color.valueOf("#f1ff87"));
        whiteRectangle.setFill(game.isRound() ? Color.valueOf("#f1ff87") : Color.valueOf("#fccf98"));
    }



}
