package checkers;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/*TODO
    - Highlight all legal moves for the person who's round it is
    - When pawn selected highlight only moves for it.
    - When player chooses one of legal moves, move pawn
 */
public class Game {
    private List<Drawable> drawable;
    private Board board;
    private Pawn[] pawns;

    private Pawn focusedPawn;

    private Canvas canvas;

    // True  - It's white players round
    // False - It's black players round
    private boolean round;

    public Game(Canvas canvas) {
        board = new Board(canvas);
        drawable = new ArrayList<Drawable>();

        // White starts
        this.round = true;

        // Board must be first in the list to be rendered behind pawns
        drawable.add(board);

        pawns = new Pawn[24];


        Image blackPawnImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("PawnBlack.png" )));
        Image whitePawnImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("PawnWhite.png")));

        for(int i = 0 ; i < 2; i++) {

            for(int j = 0; j < 12; j++) {
                int y = (int) Math.floor(j/4.0) + 5*i;
                int x = j%4*2 + (y+1)%2;

                Pawn pawn;

                if(i == 0) {
                    pawn = new Pawn(x,y,false,canvas,blackPawnImage);
                } else {
                    pawn = new Pawn(x,y,true,canvas,whitePawnImage);
                }

                pawns[i*12 + j] = pawn;
                drawable.add(pawn);

            }
        }

        focusedPawn = (Pawn) drawable.get(1);

    }

    public void click(int x, int y) {

        Pawn pawn = findPawn(x,y);

        if(pawn == null) return; // do nothing if paw not found
        // If player clicks on the same pawn switch focus state and exit.
        if(pawn == focusedPawn) {
            pawn.setFocused(!pawn.isFocused());
        }
        else {
            // Else set old focus pawn back
            focusedPawn.setFocused(false);
            pawn.setFocused(true);
            focusedPawn = pawn;
        }

        draw();
    }

    /**
     * Draws the board and pawns
     */
    public void draw() {
        for(Drawable obj : drawable) {
            obj.draw();
        }
    }


    private Pawn findPawn(int x , int y) {
        for(Pawn pawn : pawns) {
            if(pawn.isAtPosition(x,y)) {
                return pawn;
            }
        }

        return null;
    }
}
