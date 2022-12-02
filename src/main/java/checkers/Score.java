package checkers;

import javafx.scene.text.Text;

public class Score implements Drawable{
    private final Text whiteScoreText;
    private final Text blackScoreText;



    private int whiteScore;
    private int blackScore;

    public Score(Text white, Text black, int whiteScore, int blackScore) {
        whiteScoreText = white;
        blackScoreText = black;

        this.whiteScore = whiteScore;
        this.blackScore = blackScore;
    }

    @Override
    public void draw() {
        whiteScoreText.setText(Integer.toString(whiteScore));
        blackScoreText.setText(Integer.toString(blackScore));
    }

    public void increase(boolean team) {
        if(team) {
            whiteScore++;
        } else {
            blackScore++;
        }
    }

    public int getWhiteScore() {
        return whiteScore;
    }

    public int getBlackScore() {
        return blackScore;
    }
}
