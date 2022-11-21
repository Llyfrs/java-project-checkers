package checkers;

import javafx.geometry.Point2D;
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
    private List<Point2D>  highlightedTiles;
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
        highlightedTiles = new ArrayList<Point2D>();

        // White starts (gets to play the first round)
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

    /**
     * Performs click on the game board and reacts accordingly
     * @param x Colum of tile that is to be clicked on
     * @param y Row of tile that is to be clicked on
     */
    public void click(int x, int y) {

        Pawn pawn = getPawn(x,y);

        if(pawn != null) {
            switchFocus(pawn);
        }



        draw();
    }

    /**
     * Draws all drawable objects
     */
    public void draw() {
        for(Drawable obj : drawable) {
            obj.draw();
        }
    }

    /**
     *  Gets pawn at specified position
     * @param x X position
     * @param y Y position
     * @return Returns pawn on that position or null if no pawn is found
     */
    private Pawn getPawn(int x , int y) {
        for(Pawn pawn : pawns) {
            if(pawn.isAtPosition(x,y)) {
                return pawn;
            }
        }

        return null; // Returns null if no pawn is found indicating the tile is empty.
    }

    /**
     *
     * @param pawn Pawn that we want to switch focus to.
     */
    private void switchFocus(Pawn pawn) {
        // If player clicks on the same pawn switch focus state
        if(pawn == focusedPawn) {
            pawn.setFocused(!pawn.isFocused());
        }
        else {
            // Else set pawn tha was previously focused to un-focused and switch it for the new pawn that is to be focused.
            focusedPawn.setFocused(false);
            pawn.setFocused(true);
            focusedPawn = pawn;
        }
    }
}
