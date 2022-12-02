package checkers;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/*TODO
    - If no moves are available end game..
    - If no pawns are left end game
 */
public class Game {
    private List<Drawable> drawable;
    private List<Point2D> legalMoves;
    private Board board;
    private List<Pawn> pawns;

    private Score score;

    private Pawn focusedPawn;
    private boolean forcedFocus;

    private final Canvas canvas;

    // True  - It's white players round
    // False - It's black players round
    private boolean round;

    private boolean gameOver;

    public Game(Canvas canvas, Score score) {
        board = new Board(canvas);
        drawable = new ArrayList<Drawable>();
        legalMoves = new ArrayList<Point2D>();

        this.canvas = canvas;

        this.score = score;

        // White starts (gets to play the first round)
        this.round = true;

        // Board must be first in the list to be rendered behind pawns

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
                    pawn = new Pawn(x,y,false,false,canvas,blackPawnImage, crown);
                } else {
                    pawn = new Pawn(x,y,true,false,canvas,whitePawnImage,  crown);
                }

                pawns.add(pawn);

            }
        }

        focusedPawn = pawns.get(0);
        //Puts all drawable objects in to the drawable list.
        updateDrawables();
    }

    /**
     * Performs click on the game board and reacts accordingly
     * @param x Colum of tile that is to be clicked on
     * @param y Row of tile that is to be clicked on
     */
    public void click(int x, int y) {

        Pawn pawn = getPawn(x,y);

        //Puts clicked pawn in to focus mode and generates legal moves for it.
        if(pawn != null && !forcedFocus && pawn.isTeam() == round) {
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
                // Calculates the position of pawn to be removed
                int removeX = (int)(x + focusedPosition.getX()) / 2;
                int removeY = (int)(y + focusedPosition.getY()) / 2;

                System.out.printf("Removing [%d,%d]\n",removeX,removeY);

                score.increase(round);
                removePawn(removeX,removeY);

                // Finds if chain attack in possible
                List<Point2D> attackMoves = new ArrayList<Point2D>() ;
                for(Point2D move : getLegalMoves(x,y)) {
                    if (move.distance(x,y) > 2) {
                        attackMoves.add(move);
                    }
                }

                // If it is, it forces focus on the attacking pawn and doesn't switch round, making the player
                // do the chain kill
                if(!attackMoves.isEmpty()) {
                    forcedFocus = true;
                    legalMoves = attackMoves;
                } // If not, sets the forced focus to false (just in case its on true)
                else {
                    forcedFocus = false;
                }
            }

            // Moves pawn to the new position
            focusedPawn.setPosition(new Point2D(x,y));

            // If forced focus isn't on switch round, clear legal moves and de-focus current pawn.
            if(!forcedFocus) {
                focusedPawn.setFocused(false);
                legalMoves.clear();
                round = !round;
            }


            // If pawn gets to the end of a board they get turned in to a queen and can now move in all directions.
            if(focusedPawn.getPosition().getY() == 7 * (focusedPawn.isTeam() ? 0:1)) {
                focusedPawn.setQueen(true);
            }

        };

        if(!isThereMoveAvailable() ) {
            gameOver(!round);
        }else if(score.getBlackScore() == 12 || score.getWhiteScore() == 12) {
            gameOver(score.getWhiteScore() == 12);
        };

        // Draws the whole board, wit hall the changes implemented.
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

        // Area to check for legal moves, diagonally in all directions.
        int[] testX = {-1,1};
        int[] testY = {-1,1};

        for(int x : testY) {
            for(int y : testX) {

                // Sets in what direction what team can move
                // If the pawn is queen it can move in both directions so this can be ignored.
                if(!focusedPawn.isQueen()) {
                    if( round && y ==  1)  continue;
                    if(!round && y == -1)  continue;
                }

                // If the check tile doesn't have a pawn, you can move there
                Pawn p = getPawn(tileX + x, tileY + y);
                if(isTileEmpty(tileX + x, tileY + y)) {
                    pawnLegalMoves.add(new Point2D(tileX + x, tileY + y));
                }

                // If the tile has a pawn but the next on that diagonal is empty, you can jump over it and defeat it
                else {
                    if(p != null && p.isTeam() != round) {
                        if(isTileEmpty(tileX + 2*x,tileY + 2*y)) {
                            pawnLegalMoves.add(new Point2D(tileX + x + x,tileY + y + y));
                        }
                    }
                }
            }
        }

        return pawnLegalMoves;
    }

    public boolean isThereMoveAvailable() {
        ArrayList<Point2D> temp = new ArrayList<Point2D>();
        for(Pawn pawn : pawns){
            if(pawn.isTeam() == round) {
                temp.addAll(getLegalMoves(pawn));
            }

        }
        return !temp.isEmpty();
    }

    /**
     * Gets legal moves for a specific pawn.
     * @param pawn Pawn that we want to know the legal moves for
     * @return Returns all legal moves for that pawn (ignores team works with round variable)
     */
    private List<Point2D> getLegalMoves(Pawn pawn) {
        Point2D pos = pawn.getPosition();
        return getLegalMoves((int) pos.getX(), (int) pos.getY());
    }

    /**
     *  Gets pawn at specified position. Should not be used to check if tile is empty, use isTileEmpty for that because
     *  coordinates outside the board will also return null
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
     * Tells you if the title on inputted coordinates is empty.
     * @param x Column of tile that is to be checked
     * @param y Row of tile that is to be checked
     * @return returns true if the tile is empty and false if it isn't or if inputted coordinates are outside the board
     */
    private boolean isTileEmpty(int x, int y) {
        if(x > 7 || x < 0 || y > 7 || y < 0) {
            return false;
        }

        Pawn pawn = getPawn(x,y);
        return pawn == null;
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

    private void gameOver(boolean team) {
        drawable.clear(); // disables any player interaction (it won't render)
        gameOver = true;
        round = team;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public List<Pawn> getPawns() {
        return pawns;
    }

    public void setPawns(List<Pawn> pawns) {
        this.pawns = pawns;
    }

    public void updateDrawables() {
        drawable.clear();
        drawable.add(board);
        drawable.add(score);
        drawable.addAll(pawns);
        draw();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public boolean isRound() {
        return round;
    }
    public void setRound(boolean round) {
        this.round = round;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}

