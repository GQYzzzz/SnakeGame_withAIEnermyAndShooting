package dms.game;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

/**
 * Represents an enemy object in the game.
 *
 * <p> The enemy will automatically move towards the food and eat the food.
 * If crashed with the snake, the game will end</p>
 *
 * @author Qianyun Gong
 * @version 1.0
 */

public class Enemy extends Object implements movable {
    private int direction = 0;  // the current direction of the enemy, 0 for 'up', 1 for 'left', 2 for 'down', 3 for 'right'
    private int isStart = 0;    // check whether at the start of the moving, 0 represents the beginning, 1 represent the other status
    private int isEqualX = 0;   // check whether x is right, 0 represent not right, 1 represents right
    private int isBlock = 0;    // check whether the enemy is blocked, 0 represents not blocked, 1 represents blocked
    private int beforeBlock = -1;   // record the direction before blocking
    public static Enemy enemy = new Enemy();

    /**
     * Get the enemy instance.
     *
     * @return An instance of enemy.
     */
    public static Enemy getInstance(){ return enemy; }

    /**
     * Set the initial properties of the enemy
     */
    private Enemy()	{
        this.setL(true);

        this.setI(ImageUtil.images.get("enemy"));

        this.setW((int) getI().getWidth());
        this.setH((int) getI().getHeight());

        // set the initial position of the enemy at the beginning (opposite to the snake)
        this.setX(800);
        this.setY(500);

        // avoid intersects with the snake's body
        while (checkOverlap()){
            this.setX((int) (Math.random() * (900 - getW())));
            this.setY((int) (Math.random() * (600 - getH())));
        }

    }

    /**
     * Check whether overlap with the snake's body
     */
    public Boolean checkOverlap(){
        // check each point of the snake's body
        for(Point2D point : Play.mySnake.getBodyPoints()){
            if(this.getRectangle().intersects(getBodyPointRectangle(
                    (int)point.getX(),(int)point.getY(),Play.mySnake.getW(), Play.mySnake.getH()).getBoundsInParent())){
                return true;
            }
        }
        return false;
    }

    /**
     * Make the enemy move to the next position according to the position of the food
     */
    public void move(){
        // if at the start, then move x first
        if(isStart == 0) {
            if (this.getX() < Play.food.getX()) {
                direction = 3;
            } else {
                direction = 1;
            }
        }
        isStart = 1;

        // if it is moving right
        if (direction == 3) {
            // if it is blocked, then move to the next step in this direction
            if(isBlock == 1){
                this.setX(this.getX() + 3);
                // check whether able to continue the previous direction which is 'up'
                if(beforeBlock == 0){
                    this.setY(this.getY() - 3);
                    // change to the previous position if no blocks now
                    if(!(this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick1().getRectangle().getBoundsInParent())
                            || this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick2().getRectangle().getBoundsInParent()))){
                        direction = 0;
                        isEqualX = 0;
                        beforeBlock = -1;
                        isBlock = 0;
                    } else {
                        this.setY(this.getY() + 3);
                    }
                }
                // check whether able to continue the previous direction which is 'down'
                if(beforeBlock == 2){
                    this.setY(this.getY() + 3);
                    // change to the previous position if no blocks now
                    if(!(this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick1().getRectangle().getBoundsInParent())
                            || this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick2().getRectangle().getBoundsInParent()))){
                        direction = 2;
                        isEqualX = 0;
                        isBlock = 0;
                    } else {
                        this.setY(this.getY() - 3);
                    }
                }
            }
            // if x is right, then move in y direction
            else if(this.getX() <= Play.food.getX() && this.getX()+3 > Play.food.getX()){
                if(this.getY() < Play.food.getY()) direction = 2;
                if(this.getY() >= Play.food.getY()) direction = 0;
                isEqualX = 1;
            }
            // if it is in the boundary, then move a step in this direction
            else if (this.getX() + 3 < 900 - this.getW()) {
                this.setX(this.getX() + 3);
            }
            // if the next position in this direction will exceed the boundary, then change direction
            else if (this.getX() + 3 >= 900 - this.getW()){
                if(this.getY() > Play.food.getY()) direction = 0;
                if(this.getY() <= Play.food.getY()) direction = 2;
            }

            // if the next position in this direction will intersect with the brick, then change direction
            if (this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick1().getRectangle().getBoundsInParent())
                    || this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick2().getRectangle().getBoundsInParent())) {
                this.setX(this.getX() - 3);
                if(this.getY() < Play.food.getY()) direction = 2;
                if(this.getY() >= Play.food.getY()) direction = 0;
                beforeBlock = 3;
                isBlock = 1;
            }
        }

        // if it is moving down
        if (direction == 2) {
            // if it is blocked, then move to the next step in this direction
            if(isBlock == 1){
                this.setY(this.getY() + 3);
                // check whether able to continue the previous direction which is 'right'
                if(beforeBlock == 3){
                    this.setX(this.getX() + 3);
                    // change to the previous position if no blocks now
                    if(!(this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick1().getRectangle().getBoundsInParent())
                            || this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick2().getRectangle().getBoundsInParent()))){
                        direction = 3;
                        isEqualX = 0;
                        beforeBlock = -1;
                        isBlock = 0;
                    } else {
                        this.setX(this.getX() - 3);
                    }
                }
                // check whether able to continue the previous direction which is 'left'
                if(beforeBlock == 1){
                    this.setX(this.getX() - 3);
                    // change to the previous position if no blocks now
                    if(!(this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick1().getRectangle().getBoundsInParent())
                            || this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick2().getRectangle().getBoundsInParent()))){
                        direction = 1;
                        isEqualX = 0;
                        isBlock = 0;
                    } else {
                        this.setX(this.getX() + 3);
                    }
                }
            }
            // if y is right, then move in x direction
            else if(this.getY() <= Play.food.getY() && this.getY()+3 > Play.food.getY()){
                if(isEqualX == 0) {
                    if (this.getX() < Play.food.getX()) direction = 3;
                    if (this.getX() >= Play.food.getX()) direction = 1;
                }
            }
            // if it is in the boundary, then move a step in this direction
            else if (this.getY() + 3 < 600 - this.getH()) {
                this.setY(this.getY() + 3);
            }
            // if the next position in this direction will exceed the boundary, then change direction
            else if (this.getY() + 3 >= 600 - this.getH()){
                if(this.getX() > Play.food.getX()) direction = 1;
                if(this.getX() <= Play.food.getX()) direction = 3;
            }

            // if the next position in this direction will intersect with the brick, then change direction
            if (this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick1().getRectangle().getBoundsInParent())
                    || this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick2().getRectangle().getBoundsInParent())) {
                this.setY(this.getY() - 3);
                if(this.getX() < Play.food.getX()) direction = 3;
                if(this.getX() >= Play.food.getX()) direction = 1;
                beforeBlock = 2;
                isBlock = 1;
                isEqualX = 0;
            }

        }

        // if it is moving left
        if (direction == 1) {
            // if it is blocked, then move to the next step in this direction
            if(isBlock == 1){
                this.setX(this.getX() - 3);
                // check whether able to continue the previous direction which is 'up'
                if(beforeBlock == 0){
                    this.setY(this.getY() - 3);
                    // change to the previous position if no blocks now
                    if(!(this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick1().getRectangle().getBoundsInParent())
                            || this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick2().getRectangle().getBoundsInParent()))){
                        direction = 0;
                        isEqualX = 0;
                        beforeBlock = -1;
                        isBlock = 0;
                    } else {
                        this.setY(this.getY() + 3);
                    }
                }
                // check whether able to continue the previous direction which is 'down'
                if(beforeBlock == 2){
                    this.setY(this.getY() + 3);
                    // change to the previous position if no blocks now
                    if(!(this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick1().getRectangle().getBoundsInParent())
                            || this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick2().getRectangle().getBoundsInParent()))){
                        direction = 2;
                        isEqualX = 0;
                        isBlock = 0;
                    } else {
                        this.setY(this.getY() - 3);
                    }
                }
            }
            // if x is right, then move in y direction
            else if(this.getX() >= Play.food.getX() && this.getX()-3 < Play.food.getX()){
                if(this.getY() < Play.food.getY()) direction = 2;
                if(this.getY() >= Play.food.getY()) direction = 0;
                isEqualX = 1;
            }
            // if it is in the boundary, then move a step in this direction
            else if (this.getX() - 3 > 0) {
                this.setX(this.getX() - 3);
            }
            // if the next position in this direction will exceed the boundary, then change direction
            else if (this.getX() - 3 <= 0){
                if(this.getY() > Play.food.getY()) direction = 0;
                if(this.getY() <= Play.food.getY()) direction = 2;
            }

            // if the next position in this direction will intersect with the brick, then change direction
            if (this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick1().getRectangle().getBoundsInParent())
                    || this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick2().getRectangle().getBoundsInParent())) {
                this.setX(this.getX() + 3);
                if(this.getY() < Play.food.getY()) direction = 2;
                if(this.getY() >= Play.food.getY()) direction = 0;
                beforeBlock = 1;
                isBlock = 1;

            }

        }

        // if it is moving up
        if (direction == 0) {
            // if it is blocked, then move to the next step in this direction
            if(isBlock == 1){
                this.setY(this.getY() - 3);
                // check whether able to continue the previous direction which is 'right'
                if(beforeBlock == 3){
                    this.setX(this.getX() + 3);
                    // change to the previous position if no blocks now
                    if(!(this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick1().getRectangle().getBoundsInParent())
                            || this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick2().getRectangle().getBoundsInParent()))){
                        direction = 3;
                        isEqualX = 0;
                        beforeBlock = -1;
                        isBlock = 0;
                    } else {
                        this.setX(this.getX() - 3);
                    }
                }
                // check whether able to continue the previous direction which is 'left'
                if(beforeBlock == 1){
                    this.setX(this.getX() - 3);
                    // change to the previous position if no blocks now
                    if(!(this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick1().getRectangle().getBoundsInParent())
                            || this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick2().getRectangle().getBoundsInParent()))){
                        direction = 1;
                        isEqualX = 0;
                        isBlock = 0;
                    } else {
                        this.setX(this.getX() + 3);
                    }
                }
            }
            // if y is right, then move in x direction
            else if(this.getY() >= Play.food.getY() && this.getY()-3 < Play.food.getY()){
                if(isEqualX == 0) {
                    if (this.getX() < Play.food.getX()) direction = 3;
                    if (this.getX() >= Play.food.getX()) direction = 1;
                }
            }
            // if it is in the boundary, then move a step in this direction
            else if (this.getY() - 3 > 0) {
                this.setY(this.getY() - 3);
            }
            // if the next position in this direction will exceed the boundary, then change direction
            else if (this.getY() - 3 <= 0){
                if(this.getX() > Play.food.getX()) direction = 1;
                if(this.getX() <= Play.food.getX()) direction = 3;
            }

            // if the next position in this direction will intersect with the brick, then change direction
            if (this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick1().getRectangle().getBoundsInParent())
                    || this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick2().getRectangle().getBoundsInParent())) {
                this.setY(this.getY() + 3);
                if(this.getX() < Play.food.getX()) direction = 3;
                if(this.getX() >= Play.food.getX()) direction = 1;
                beforeBlock = 0;
                isBlock = 1;
                isEqualX = 0;
            }

        }

        // if the enemy touches the food, the food will be eaten by the enemy
        if(this.getRectangle().getBoundsInParent().intersects(Play.food.getRectangle().getBoundsInParent())) {
            Play.food.setL(false);
            Play.food.refreshFood();
            isStart = 0;
            isEqualX = 0;
        }
    }

    /**
     * Set the isStart variable
     *
     * @param isStart The value to set for the isStart variable.
     */
    public void setIsStart(int isStart) {
        this.isStart = isStart;
    }

    /**
     * Set the isEqualX variable
     *
     * @param isEqualX The value to set for the isEqualX variable.
     */
    public void setIsEqualX(int isEqualX) {
        this.isEqualX = isEqualX;
    }

    /**
     * Check whether crashes with the snake
     *
     * @param mySnake An instance of MySnake
     */
    public void crash(MySnake mySnake)	{
        // check whether the snake's head touches the enemy
        if (mySnake.getRectangle().intersects(this.getRectangle().getBoundsInParent()) && this.getL() && mySnake.getL()){
            MusicPlayer.getMusicPlayOnce("src/main/resources/dms/game/music/crash.mp3");
            if(Player.getInstance().getHumanVoice()==1){
                MusicPlayer.getMusicPlayOnce("src/main/resources/dms/game/music/crash-prompt.mp3");
            }
            mySnake.setL(false);
        }
        // check whether the snake's body touches the enemy
        for(Point2D point : mySnake.getBodyPoints()){
            Rectangle pointRectangle = getBodyPointRectangle((int)point.getX(),(int)point.getY(), mySnake.getW(), mySnake.getH());
            if(!(mySnake.getBodyPoints().indexOf(point) == mySnake.getBodyPoints().size()-1)) {
                if (pointRectangle.intersects(this.getRectangle().getBoundsInParent()) && this.getL() && mySnake.getL()) {
                    MusicPlayer.getMusicPlayOnce("src/main/resources/dms/game/music/crash.mp3");
                    if(Player.getInstance().getHumanVoice()==1){
                        MusicPlayer.getMusicPlayOnce("src/main/resources/dms/game/music/crash-prompt.mp3");
                    }
                    mySnake.setL(false);
                }
            }
        }
    }

    /**
     * Get the rectangle of the enemy
     *
     * @param x The x coordinate of the enemy
     * @param y The y coordinate of the enemy
     * @param w The width of the enemy
     * @param h The height of the enemy
     * @return The rectangle of the enemy instance
     */
    public Rectangle getBodyPointRectangle (int x, int y, int w, int h){
        return new Rectangle(x, y, w, h);
    }

    /**
     * Draw the graphic of enemy
     *
     * @param g The GraphicsContext used to draw images
     */
    @Override
    public void draw(GraphicsContext g) {
        g.drawImage(this.getI(), this.getX(), this.getY());
    }
}
