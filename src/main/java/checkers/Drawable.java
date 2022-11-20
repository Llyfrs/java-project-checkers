package checkers;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


/**
    Interface for objects that can be drawn.
*/
public interface Drawable {

    Canvas canvas = null;
    // I'm using canvas to make the drawing more dynamic and be abel to react to changes
    /**
     * Draws the object on to a canvas
     *
     */
    public void draw();
}
