package dms.game;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

/**
 * The score reduction object in the Snake game.
 *
 * <p> It reduces the player's score when the snake's head touches the object. </p>
 *
 * @author Qianyun Gong
 * @version 1.0
 */

public class ScoreReduce extends Object {
    public static ScoreReduce scoreReduce = new ScoreReduce();

    /**
     * Get the scoreReduce instance.
     *
     * @return An instance of ScoreReduce.
     */
    public static ScoreReduce getInstance(){ return scoreReduce; }

    /**
     * Initialize a ScoreReduce object with a random position.
     */
    public ScoreReduce()	{
        refreshScoreReduce();
    }

    /**
     * Checks whether the score reduction object overlaps with the snake's body.
     *
     * @return True if overlapped, false otherwise.
     */
    public Boolean checkOverlap(){
        for(Point2D point : Play.mySnake.getBodyPoints()){
            if(this.getRectangle().intersects(getBodyPointRectangle(
                    (int)point.getX(),(int)point.getY(),Play.mySnake.getW(), Play.mySnake.getH()).getBoundsInParent())){
                return true;
            }
        }
        return false;
    }

    /**
     * Get a rectangle representing a point in the snake's body.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param w The width of the point.
     * @param h The height of the point.
     * @return The rectangle representing the point.
     */
    public Rectangle getBodyPointRectangle (int x, int y, int w, int h){
        return new Rectangle(x, y, w, h);
    }


    /**
     * Check whether the snake touches the reduceScore object
     *
     * @param mySnake An instance of the snake.
     */
    public void crash(MySnake mySnake)	{
        // check whether the snake's head touches the object
        if (mySnake.getRectangle().intersects(this.getRectangle().getBoundsInParent()) && this.getL() && mySnake.getL()){
            this.setL(false);
            mySnake.setScore(mySnake.getScore() - 50);
            if(Player.getInstance().getHumanVoice()==1){
                MusicPlayer.getMusicPlayOnce("src/main/resources/dms/game/music/reduce50.mp3");
            }
        }
    }

    /**
     * Refresh the random position of scoreReduce
     */
    public void refreshScoreReduce(){
        this.setL(true);
        this.setI(ImageUtil.images.get("score-reduce"));

        this.setW((int) getI().getWidth());
        this.setH((int) getI().getHeight());

        this.setX((int) (Math.random() * (900 - getW())));
        this.setY((int) (Math.random() * (600 - getH())));

        // avoid intersects with the brick
        if(Player.getInstance().getMode()==1 || Player.getInstance().getMode()==2){
            while (checkOverlap() || this.getRectangle().intersects(Brick.getInstanceBrick1().getRectangle().getBoundsInParent())
                    || this.getRectangle().intersects(Brick.getInstanceBrick2().getRectangle().getBoundsInParent())
                    || this.getRectangle().intersects(Play.food.getRectangle().getBoundsInParent())){
                this.setX((int) (Math.random() * (900 - getW())));
                this.setY((int) (Math.random() * (600 - getH())));
            }
        }
        // avoid intersects with the snake
        else {
            while(checkOverlap()) {
                this.setX((int) (Math.random() * (900 - getW())));
                this.setY((int) (Math.random() * (600 - getH())));
            }
        }
    }

    /**
     * Draws the score reduction object.
     *
     * @param g The GraphicsContext used for drawing.
     */
    @Override
    public void draw(GraphicsContext g) {
        g.drawImage(this.getI(), this.getX(), this.getY());
    }
}
