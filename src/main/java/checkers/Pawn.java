package checkers;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Pawn implements Drawable {

    Canvas canvas;
    int x;
    int y;

    public Pawn(int x, int y, Canvas canvas) {
        this.canvas = canvas;
        this.x = x;
        this.y = y;

    }

    @Override
    public void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();


        Image image = new Image(getClass().getResourceAsStream("PawnWhite.png"));

        gc.drawImage(image,0,0,60,60);


    }
}
