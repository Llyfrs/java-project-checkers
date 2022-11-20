package checkers;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {
    List<Drawable> drawable;
    Board board;
    Pawn[][] pawns;

    private Canvas canvas;


    public Game(Canvas canvas) {
        board = new Board(canvas);
        drawable = new ArrayList<Drawable>();

        drawable.add(board);

        pawns = new Pawn[2][];

        Image blackPawnImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("PawnBlack.png" )));
        Image whitePawnImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("PawnWhite.png")));

        for(int i = 0 ; i < 2; i++) {
            pawns[i] = new Pawn[12];

            for(int j = 0; j < 12; j++) {
                int y = (int) Math.floor(j/4.0) + 5*i;
                int x = j%4*2 + y%2;


                Pawn pawn;

                if(i == 0) {
                    pawn = new Pawn(x,y,canvas,blackPawnImage);
                } else {
                    pawn = new Pawn(x,y,canvas,whitePawnImage);
                }




                pawns[i][j] = pawn;
                drawable.add(pawn);

            }
        }






    }

    public void draw() {
        for(Drawable obj : drawable) {
            obj.draw();
        }
    }
}
