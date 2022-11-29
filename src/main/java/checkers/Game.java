package checkers;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/*TODO
    - Highlight all legal moves for the person who's round it is (Probably won't be doint this one)
 */
public class Game {
    private List<Drawable> drawable;
    private List<Point2D> legalMoves;
    private Board board;
    private List<Pawn> pawns;



    private Pawn focusedPawn;
    private boolean forcedFocuse;

    private Canvas canvas;

    // True  - It's white players round
    // False - It's black players round
    private boolean round;

    public Game(Canvas canvas) {
        board = new Board(canvas);
        drawable = new ArrayList<Drawable>();
        legalMoves = new ArrayList<Point2D>();

        // White starts (gets to play the first round)
        this.round = true;

        // Board must be first in the list to be rendered behind pawns
        drawable.add(board);

        pawns = new ArrayList<Pawn>();

        Image blackPawnImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("PawnBlack.png" )));
        Image whitePawnImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("PawnWhite.png")));
        Image crown          = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Crown.png")));

        for(int i = 0 ; i < 2; i++) {

            for(int j = 0; j < 12; j++) {
                int y = (int) Math.floor(j/4.0) + 5*i;
                int x = j%4*2 + (y+1)%2;

                Pawn pawn;

                if(i == 0) {
                    pawn = new Pawn(x,y,false,canvas,blackPawnImage, crown);
                } else {
                    pawn = new Pawn(x,y,true,canvas,whitePawnImage,  crown);
                }

                pawns.add(pawn);
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

        //Puts clicked pawn in to focus mode and generates legal moves for it.
        if(pawn != null && !forcedFocuse &&pawn.isTeam() == round) {
            switchFocus(pawn);
            legalMoves.clear();
            legalMoves.addAll(getLegalMoves(focusedPawn));
        }

        // If clicked on one of the available legal moves, the focus pawn in moved on chosen tile.
        if(legalMoves.contains(new Point2D(x,y))) {
            System.out.print("Legal Move Chosen\n");

            // If the chosen location is further away that 2 tiles, it means it's attacking move and
            // enemy pawn needs to be removed from the table.
            Point2D focusedPosition = focusedPawn.getPosition();
            if(focusedPosition.distance(x,y) > 2) {
                int removeX = (int)(x + focusedPosition.getX()) / 2;
                int removeY = (int)(y + focusedPosition.getY()) / 2;

                System.out.printf("Removing [%d,%d]\n",removeX,removeY);

                removePawn(removeX,removeY);

                List<Point2D> attackMoves = new ArrayList<Point2D>() ;
                System.out.printf("Getting legal move for [%d,%d]",x,y);
                List<Point2D> tempMoves = getLegalMoves(x,y);
                for(Point2D move : tempMoves) {
                    if (move.distance(x,y) > 2) {
                        attackMoves.add(move);
                    }
                }

                if(!attackMoves.isEmpty()) {
                    forcedFocuse = true;
                    legalMoves = attackMoves;
                } else {
                    forcedFocuse = false;
                }
            }

            focusedPawn.setPosition(new Point2D(x,y));

            if(!forcedFocuse) {
                focusedPawn.setFocused(false);
                legalMoves.clear();
                round = !round;
            }


            // If pawn gets to the end of a board they get turned in to a queen and can now move in all directions.
            if(focusedPawn.getPosition().getY() == 7 * (focusedPawn.isTeam() ? 0:1)) {
                focusedPawn.setQueen(true);
            }

        };

        draw();
    }

    /**
     * Draws all drawable objects
     */
    public void draw() {
        for(Drawable obj : drawable) {
            obj.draw();
        }
        // Highlights tiles in legal moves
        for(Point2D cord : legalMoves) {
            board.highlight((int)cord.getX(),(int)cord.getY());
        }
    }

    /**
     * Generates legal moves for a tile at specified coordinates, the direction is decided by
     * whose round it currently is.
     * @param tileX Colum of tile
     * @param tileY Row of a tile
     * @return Returns list of legalMoves
     */
    private List<Point2D> getLegalMoves(int tileX, int tileY) {

        List<Point2D> pawnLegalMoves = new ArrayList<Point2D>();

        int[] testX = {-1,1};
        int[] testY = {-1,1};

        for(int x : testY) {
            for(int y : testX) {
                // Sets in what direction what team can move

                if(!focusedPawn.isQueen()) {
                    if( round && y ==  1)  continue;
                    if(!round && y == -1)  continue;
                }


                Pawn p = getPawn(tileX + x, tileY + y);
                if(p == null) {
                    pawnLegalMoves.add(new Point2D(tileX + x, tileY + y));
                }

                else if(p.isTeam() != round){
                    p = getPawn(tileX + x + x, tileY + y + y);
                    if(p == null) {
                        pawnLegalMoves.add(new Point2D(tileX + x + x,tileY + y + y));
                    }
                }
            }
        }

        return pawnLegalMoves;
    }

    private List<Point2D> getLegalMoves(Pawn pawn) {
        Point2D pos = pawn.getPosition();
        return getLegalMoves((int) pos.getX(), (int) pos.getY());
    }

    /**
     *  Gets pawn at specified position
     * @param x X position
     * @param y Y position
     * @return Returns pawn on that position or null if no pawn is found
     */
    private Pawn getPawn(int x , int y) {

        if(x > 7 || x < 0 || y > 7 || y < 0) {
            return pawns.get(0);
        }

        for(Pawn pawn : pawns) {
            if(pawn.isAtPosition(x,y)) {
                return pawn;
            }
        }

        return null; // Returns null if no pawn is found indicating the tile is empty.
    }

    private void removePawn(int x, int y) {
        Pawn p = getPawn(x,y);

        pawns.remove(p);
        drawable.remove(p);
    }

    /**
     * Switches focus to a chosen pawn, or turns it off, if the current pawn is already focused.
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
