package checkers;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class Pawn implements Drawable {

    private Canvas canvas;

    private boolean team;
    private int x;
    private int y;

    private final double tileWidth;

    private final double tileHeight;
    public Pawn(int x, int y, Canvas canvas) {
        this.canvas = canvas;
        this.x = x;
        this.y = y;

        this.tileHeight = canvas.getHeight() / 8;
        this.tileWidth  = canvas.getWidth()  / 8;

    }

    @Override
    public void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();


        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("PawnWhite.png")));

        gc.drawImage(image,0,0,tileWidth,tileHeight);


    }
}
