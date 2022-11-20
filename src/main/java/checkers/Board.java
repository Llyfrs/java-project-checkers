package checkers;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *  Class handling the appearance of the checkers table
 *
 */

public class Board implements Drawable {

    private final double tileWidth;
    private final double tileHeight;

    Canvas canvas;

    public Board(Canvas canvas) {
        tileWidth  = canvas.getWidth()  / 8;
        tileHeight = canvas.getHeight() / 8;

        this.canvas = canvas;
    }
    @Override
    public void draw() {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.save();
        
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if((i*8 + j + i%2)%2 == 0) {
                    gc.setFill(Color.rgb(196, 98, 16)); // Light brown
                    gc.fillRect(tileWidth * j , tileHeight* i, tileWidth, tileHeight);
                }
                else {
                    gc.setFill(Color.rgb(138,69,11)); // Dark brown
                    gc.fillRect(tileWidth * j , tileHeight * i, tileWidth, tileHeight);
                }
            }

        }

        gc.restore();
    }

    public void highlight(int x, int y) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.save();
        gc.setFill(Color.rgb(70,70,200,0.6));
        gc.fillRect(tileWidth * x, tileHeight * y,tileWidth,tileHeight);

        gc.restore();

    }
}
