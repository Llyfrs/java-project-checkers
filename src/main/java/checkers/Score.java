package checkers;

import javafx.scene.text.Text;

public class Score implements Drawable{
    private final Text whiteScoreText;
    private final Text blackScoreText;

    private int whiteScore;
    private int blackScore;

    public Score(Text white, Text black) {
        whiteScoreText = white;
        blackScoreText = black;

        whiteScore = 0;
        blackScore = 0;
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
}
