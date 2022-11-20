package checkers;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class Pawn implements Drawable {

    private final Canvas canvas;

    private final Image image;

    private boolean team;
    private int x;
    private int y;

    private final double tileWidth;
    private final double tileHeight;

    public Pawn(int x, int y, Canvas canvas, Image image) {
        this.canvas = canvas;
        this.image = image;
        this.x = x;
        this.y = y;

        this.tileHeight = canvas.getHeight() / 8;
        this.tileWidth  = canvas.getWidth()  / 8;

    }

    @Override
    public void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.drawImage(this.image,x * tileWidth,y * tileHeight ,tileWidth,tileHeight);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
