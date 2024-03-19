package dms.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * The Object class is an abstract class representing game objects.
 *
 * <p> This class provides draw methods for displaying the images and some common
 * properties such as position, image, width, height, and visibility. </p>
 *
 * @author Qianyun Gong - modified
 * @version 1.0
 */

public abstract class Object
{
    private int x;
    private int y;
    private Image i;
    private int w;
    private int h;
    private boolean l;

    /**
     * Get the x coordinate of the object.
     *
     * @return The x coordinate.
     */
    public int getX() { return x; }

    /**
     * Set the x coordinate of the object.
     *
     * @param x The new x coordinate.
     */
    public void setX(int x) { this.x = x; }

    /**
     * Get the y coordinate of the object.
     *
     * @return The y coordinate.
     */
    public int getY() { return y; }

    /**
     * Set the y coordinate of the object.
     *
     * @param y The new y coordinate.
     */
    public void setY(int y) { this.y = y; }

    /**
     * Get the object image.
     *
     * @return The image.
     */
    public Image getI() { return i; }

    /**
     * Set the object image.
     *
     * @param i The new image.
     */
    public void setI(Image i) { this.i = i; }

    /**
     * Get the width of the object.
     *
     * @return The width.
     */
    public int getW() { return w; }

    /**
     * Set the width of the object.
     *
     * @param w The new width.
     */
    public void setW(int w) { this.w = w; }

    /**
     * Get the height of the object.
     *
     * @return The height.
     */
    public int getH() { return h; }

    /**
     * Set the height of the object.
     *
     * @param h The new height.
     */
    public void setH(int h) { this.h = h; }

    /**
     * Get the visibility state of the object.
     *
     * @return True if the object is visible, false otherwise.
     */
    public boolean getL() { return l; }

    /**
     * Set the visibility state of the object.
     *
     * @param l The new visibility state.
     */
    public void setL(boolean l) { this.l = l; }

    /**
     * Abstract method to draw the object on the graphics context.
     *
     * @param g The GraphicsContext used to draw images.
     */
    public abstract void draw(GraphicsContext g);

    /**
     * Get the rectangle representing the object's bounds.
     *
     * @return The Rectangle object representing the object's bounds.
     */
    public Rectangle getRectangle()
    {
        return new Rectangle(x, y, w, h);
    }
}
