package dms.game;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The snake in the game.
 *
 * <p> The MySnake class extends the Object class and implements the movable interface.</p>
 *
 * @author Qianyun Gong - modified
 * @version 1.0
 */

public class MySnake extends Object implements movable
{
    private final int speed_XY;
    private int length;
    private final int num; // The points that each part of the snake's body has (i.e. each section of the body takes up 5 points)
    private int score = 0;
    private int headDirection;  // the direction of the snake's head

    private static final Image snakeOriginalHead = ImageUtil.images.get("snake-head-right");
    private static final Image snakeYellowHead = GameUtil.rotateImage(ImageUtil.images.get("snake-yellow-head"),90);
    private static final Image snakeYellowTail = GameUtil.rotateImage(ImageUtil.images.get("snake-yellow-tail"),90);
    private static final Image tailUp = ImageUtil.images.get("snake-yellow-tail");
    private static final Image tailLeft = GameUtil.rotateImage(tailUp,-90);
    private static final Image tailDown = GameUtil.rotateImage(tailUp,-180);
    private List<Point2D> bodyPoints = new LinkedList<>();
    private List<String> roundPoints = new ArrayList<>(); // used to store the point position of round corners
    private List<String> roundType = new ArrayList<>();   // used to store the type of each round corner

    private static Image newImgSnakeHead;
    private static Image snakeTail;
    boolean up, down, left, right = true;
    private boolean isRunning = true;   // used to judge whether the game is paused
    private Point2D headPoint;  // the head point of the snake

    public static MySnake mySnake = new MySnake(100,100);

    /**
     * Get the snake instance.
     *
     * @return An instance of MySnake.
     */
    public static MySnake getInstance(){ return mySnake; }

    /**
     * Initialize the snake with the specified starting position.
     *
     * @param x The x coordinate of the starting position.
     * @param y The y coordinate of the starting position.
     */
    private MySnake(int x, int y)
    {
        this.setL(true);
        this.setX(x);
        this.setY(y);

        // set the snake's appearance
        if(Player.getInstance().getSnakeAppearance()==0) this.setI(ImageUtil.images.get("snake-body"));
        if(Player.getInstance().getSnakeAppearance()==1) this.setI(ImageUtil.images.get("snake-yellow-body"));

        this.setW((int) getI().getWidth());
        this.setH((int) getI().getHeight());

        this.speed_XY = 5;
        this.length = 1;

        // set the initial direction to right
        headDirection = 3;

        /*
         * Attention : The distance traveled per unit of time
         */
        this.num = this.getW() / speed_XY;

        if(Player.getInstance().getSnakeAppearance()==0) newImgSnakeHead = snakeOriginalHead;
        if(Player.getInstance().getSnakeAppearance()==1) newImgSnakeHead = snakeYellowHead;
    }

    /**
     * Refresh the state and the position of the snake to be initial.
     *
     * @param x The x coordinate of the starting position.
     * @param y The y coordinate of the starting position.
     */
    public void refreshSnake(int x, int y){
        this.setL(true);
        this.setX(x);
        this.setY(y);
        headDirection = 3;
        up = false;
        down = false;
        left = false;
        right = true;
        setScore(0);
        length = 1;
        bodyPoints = new LinkedList<>();
        roundPoints = new ArrayList<>();
        roundType = new ArrayList<>();
        if(Player.getInstance().getSnakeAppearance()==0) this.setI(ImageUtil.images.get("snake-body"));
        if(Player.getInstance().getSnakeAppearance()==1) this.setI(ImageUtil.images.get("snake-yellow-body"));
        if(Player.getInstance().getSnakeAppearance()==0) newImgSnakeHead = snakeOriginalHead;
        if(Player.getInstance().getSnakeAppearance()==1) newImgSnakeHead = snakeYellowHead;
    }

    /**
     * Get the current score of the snake.
     *
     * @return The current score.
     */
    public int getScore() { return score; }

    /**
     * Set the score of the snake.
     *
     * @param newScore The new score to be set.
     */
    public void setScore(int newScore){ this.score = newScore; }

    /**
     * Get the length of the snake.
     *
     * @return The length of the snake.
     */
    public int getLength()
    {
        return length;
    }

    /**
     * Change the length of the snake.
     *
     * @param length The new length to be set.
     */
    public void changeLength(int length)
    {
        this.length = length;
    }

    /**
     * Get the state of the game.
     *
     * <p> Check whether the game is running or paused </p>
     *
     * @return True if the snake is running, false otherwise.
     */
    public boolean getRunning() {
        return isRunning;
    }

    /**
     * Set the state of the snake.
     *
     * @param b The new state.
     */
    public void setRunning(boolean b) {
        isRunning = b;
    }

    /**
     * Handles the key pressed event to control the snake's movement.
     *
     * @param keyCode The KeyCode corresponding to the pressed key.
     */
    public void keyPressed(KeyCode keyCode)
    {
        // check the key
        switch (keyCode)
        {
            // pause or continue when pressing 'Space'
            case SPACE:
                MusicPlayer.getMusicPlayOnce("src/main/resources/dms/game/music/key-press.mp3");
                setRunning(!getRunning());
                break;

            // end the game when pressing 'E'
            case E:
                MusicPlayer.getMusicPlayOnce("src/main/resources/dms/game/music/key-press.mp3");
                setL(false);
                break;

            // shoot the enemy when pressing 'S'
            case S:
                MusicPlayer.getMusicPlayOnce("src/main/resources/dms/game/music/key-press.mp3");
                if(Player.getInstance().getMode() == 2) {
                    Play.bullet = Bullet.getInstance();
                    Play.bullet.setPos();
                    Play.isBullet = true;
                    Play.bullet.setDirection(headDirection);
                }
                break;

            // move towards up when pressing up in the keyboard
            case UP:
                // set the sound of pressing keyboard to play once
                if(Player.getInstance().getHumanVoice()==1) {
                    MusicPlayer.getMusicPlayOnce("src/main/resources/dms/game/music/up.mp3");
                } else{
                    MusicPlayer.getMusicPlayOnce("src/main/resources/dms/game/music/key-press.mp3");
                }
                // if the previous direction is not down, then turn up successfully, fail otherwise
                if (!down)
                {
                    // record the round corner if the body point is more than 2
                    if(bodyPoints.size()>14) {
                        roundPoints.add(getX() + "," + getY());
                        if (right) roundType.add("right-to-up");
                        if (left) roundType.add("left-to-up");
                    }

                    // set the direction
                    up = true;
                    down = false;
                    left = false;
                    right = false;
                    headDirection = 0;

                    // set the head image according to the selected appearance
                    if(Player.getInstance().getSnakeAppearance()==0) newImgSnakeHead = GameUtil.rotateImage(snakeOriginalHead, -90);
                    if(Player.getInstance().getSnakeAppearance()==1) newImgSnakeHead = GameUtil.rotateImage(snakeYellowHead, -90);
                }
                break;

            // move towards down when pressing down in the keyboard
            case DOWN:
                // set the sound of pressing keyboard to play once
                if(Player.getInstance().getHumanVoice()==1) {
                    MusicPlayer.getMusicPlayOnce("src/main/resources/dms/game/music/down.mp3");
                } else{
                    MusicPlayer.getMusicPlayOnce("src/main/resources/dms/game/music/key-press.mp3");
                }
                // if the previous direction is not up, then turn down successfully, fail otherwise
                if (!up)
                {
                    // record the round corner if the body point is more than 2
                    if(bodyPoints.size()>14) {
                        roundPoints.add(getX()+","+getY());
                        if(right) roundType.add("right-to-down");
                        if(left) roundType.add("left-to-down");
                    }

                    // set the direction
                    up = false;
                    down = true;
                    left = false;
                    right = false;
                    headDirection = 2;

                    // set the head image according to the selected appearance
                    if(Player.getInstance().getSnakeAppearance()==0) newImgSnakeHead = GameUtil.rotateImage(snakeOriginalHead, 90);
                    if(Player.getInstance().getSnakeAppearance()==1) newImgSnakeHead = GameUtil.rotateImage(snakeYellowHead, 90);
                }
                break;

            // move towards left when pressing left in the keyboard
            case LEFT:
                // set the sound of pressing keyboard to play once
                if(Player.getInstance().getHumanVoice()==1) {
                    MusicPlayer.getMusicPlayOnce("src/main/resources/dms/game/music/left.mp3");
                } else{
                    MusicPlayer.getMusicPlayOnce("src/main/resources/dms/game/music/key-press.mp3");
                }
                // if the previous direction is not right, then turn left successfully, fail otherwise
                if (!right)
                {
                    // record the round corner if the body point is more than 2
                    if(bodyPoints.size()>14) {
                        roundPoints.add(getX() + "," + getY());
                        if (up) roundType.add("up-to-left");
                        if (down) roundType.add("down-to-left");
                    }
                    // set the direction
                    up = false;
                    down = false;
                    left = true;
                    right = false;
                    headDirection = 1;

                    // set the head image according to the selected appearance
                    if(Player.getInstance().getSnakeAppearance()==0) newImgSnakeHead = GameUtil.rotateImage(snakeOriginalHead, -180);
                    if(Player.getInstance().getSnakeAppearance()==1) newImgSnakeHead = GameUtil.rotateImage(snakeYellowHead, -180);
                }
                break;

            // move towards right when pressing right in the keyboard
            case RIGHT:
                // set the sound of pressing keyboard to play once
                if(Player.getInstance().getHumanVoice()==1) {
                    MusicPlayer.getMusicPlayOnce("src/main/resources/dms/game/music/right.mp3");
                } else{
                    MusicPlayer.getMusicPlayOnce("src/main/resources/dms/game/music/key-press.mp3");
                }
                // if the previous direction is not left, then turn right successfully, fail otherwise
                if (!left)
                {
                    // record the round corner if the body point is more than 2
                    if(bodyPoints.size()>14) {
                        roundPoints.add(getX() + "," + getY());
                        if (up) roundType.add("up-to-right");
                        if (down) roundType.add("down-to-right");
                    }

                    // set the direction
                    up = false;
                    down = false;
                    left = false;
                    right = true;
                    headDirection = 3;

                    // set the head image according to the selected appearance
                    if(Player.getInstance().getSnakeAppearance()==0) newImgSnakeHead = snakeOriginalHead;
                    if(Player.getInstance().getSnakeAppearance()==1) newImgSnakeHead = snakeYellowHead;
                }

            default:
                break;
        }
    }

    /**
     * Make the snake move to the next position in the current direction
     */
    public void move()
    {
        // let the entity move
        if (up)
        {
            this.setY(this.getY() - speed_XY);
        } else if (down)
        {
            this.setY(this.getY() + speed_XY);
        } else if (left)
        {
            this.setX(this.getX() - speed_XY);
        } else if (right)
        {
            this.setX(this.getX() + speed_XY);
        }

    }

    /**
     * Draw the graphic of snake
     *
     * @param gc The GraphicsContext used to draw images
     */
    @Override
    public void draw(GraphicsContext gc)
    {
        // determine the value of l
        outofBounds();
        eatBody();

        // add new point, the next position of the head
        bodyPoints.add(new Point2D(getX(), getY()));
        setHeadPoint(new Point2D(getX(), getY()));

        // remove the point of the tail of the body when moving forward for one unit
        if (bodyPoints.size() == (this.length + 1) * num)
        {
            bodyPoints.remove(0);
        }

        // add new head image to the new position and set the rest of the body to be the body image
        gc.drawImage(newImgSnakeHead, getX(), getY());
        drawBodyOriginal(gc);

        // get the next position of the head
        move();
    }

    /**
     * Check whether the snake touches its own body
     */
    public void eatBody()
    {
        /*
         * Set l to be false if the snake touches its own body, and the snake.game will end
         */
        for (Point2D point : bodyPoints)
        {
            for (Point2D point2 : bodyPoints)
            {
                if (point.equals(point2) && point != point2)
                {
                    this.setL(false);
                }
            }
        }
    }

    /**
     * Draw the graphic of snake's body
     *
     * @param gc The GraphicsContext used to draw images
     */
    public void drawBodyOriginal(GraphicsContext gc)
    {
        int len = bodyPoints.size() - 1 - num;

        for (int i = len; i >= num; i -= num)
        {
            Point2D point = bodyPoints.get(i);

            // if no snake body, only the head and the tail
            if(len-num < num && Player.getInstance().getSnakeAppearance()==1){
                if(right) snakeTail = snakeYellowTail;
                if(left) snakeTail = tailLeft;
                if(up) snakeTail = tailUp;
                if(down) snakeTail = tailDown;

                gc.drawImage(snakeTail, point.getX(), point.getY());
            }

            // draw tail for the yellow snake after the body
            else if(i-num<num && Player.getInstance().getSnakeAppearance()==1){
                if(roundPoints.contains((int)point.getX()+","+(int)point.getY())){
                    int idx = roundPoints.indexOf((int)point.getX()+","+(int)point.getY());
                    if(roundType.get(idx).equals("right-to-up") || roundType.get(idx).equals("up-to-left") || roundType.get(idx).equals("left-to-down") || roundType.get(idx).equals("down-to-right")) {
                        snakeTail = GameUtil.rotateImage(snakeTail,-90);
                        gc.drawImage(snakeTail, point.getX(), point.getY());
                    } else if(roundType.get(idx).equals("left-to-up") || roundType.get(idx).equals("up-to-right") || roundType.get(idx).equals("right-to-down") || roundType.get(idx).equals("down-to-left")) {
                        snakeTail = GameUtil.rotateImage(snakeTail,90);
                        gc.drawImage(snakeTail, point.getX(), point.getY());
                    }
                    roundPoints.remove(idx);
                    roundType.remove(idx);

                } else {
                    gc.drawImage(snakeTail, point.getX(), point.getY());
                }
            }

            // other cases draw body
            else {
                gc.drawImage(this.getI(), point.getX(), point.getY());
            }
        }
    }

    /**
     * Check if the snake touches the boundary
     */
    public void outofBounds()
    {
        /*
         * Judge whether the snake exceeds the bound, if so, set l to be false
         */
        boolean xOut = (getX() <= 0 || getX() >= (900 - getW()));
        boolean yOut = (getY() <= 0 || getY() >= (600 - getH()));
        if (xOut || yOut)
        {
            setL(false);
        }
    }

    /**
     * Get the body points list of the snake
     *
     * @return The body points
     */
    public List<Point2D> getBodyPoints() {
        return bodyPoints;
    }

    /**
     * Set the body point of the snake with new point
     *
     * @param point The body point
     */
    public void setBodyPoints(Point2D point) {
        this.bodyPoints.add(point);
    }

    /**
     * Get the head point of the snake
     *
     * @return The head point
     */
    public Point2D getHeadPoint() {
        return headPoint;
    }

    /**
     * Set the head point of the snake with new head point
     *
     * @param headPoint The new head point
     */
    public void setHeadPoint(Point2D headPoint) {
        this.headPoint = headPoint;
    }

    /**
     * Get the up value.
     *
     * @return A boolean value representing up
     */
    public boolean isUp() {
        return up;
    }

}
