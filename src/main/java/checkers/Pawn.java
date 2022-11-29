package checkers;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;


public class Pawn implements Drawable {

    private final Canvas canvas;
    private final Image image;
    private final Image crown;

    private final boolean team;


    private boolean queen;
    private boolean focused;
    private int x;
    private int y;


    private final double tileWidth;
    private final double tileHeight;


    public Pawn(int x, int y, boolean team,Canvas canvas, Image image, Image crown) {
        this.canvas = canvas;
        this.image = image;
        this.crown = crown;
        this.x = x;
        this.y = y;

        this.team = team;
        this.queen = false;

        this.tileHeight = canvas.getHeight() / 8;
        this.tileWidth  = canvas.getWidth()  / 8;

    }



    @Override
    public void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.drawImage(this.image,x * tileWidth,y * tileHeight ,tileWidth,tileHeight);

        if(queen) {
            gc.drawImage(this.crown,x * tileWidth,y * tileHeight ,tileWidth,tileHeight);
        }

        if(focused) {
            gc.save();
            gc.setFill(Color.rgb(255,0,0,0.3));
            gc.fillOval(x*tileWidth, y * tileHeight, tileWidth, tileHeight);
            gc.restore();
        }
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isAtPosition(int x, int y) {
        return this.x == x && this.y == y;
    }

    public Point2D getPosition() {
        return new Point2D(x,y);
    }

    public void setPosition(Point2D position) {
        this.x = (int) position.getX();
        this.y = (int) position.getY();
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }
    public boolean isFocused() {
        return focused;
    }

    public boolean isTeam() {
        return team;
    }

    public boolean isQueen() {
        return queen;
    }

    public void setQueen(boolean queen) {
        this.queen = queen;
    }

}
