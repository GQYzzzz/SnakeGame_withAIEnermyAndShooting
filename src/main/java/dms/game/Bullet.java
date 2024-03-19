package dms.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Represents a bullet object in the game.
 *
 * <p> The bullet will move towards one direction.
 * If hit the enemy, the enemy will disappear and the score will increase</p>
 *
 * @author Qianyun Gong
 * @version 1.0
 */

public class Bullet extends Object implements movable {
    private int direction;  // the direction of the bullet
    private final Image imgBullet = ImageUtil.images.get("bullet");
    private static final Bullet bullet = new Bullet();

    /**
     * Get the bullet instance.
     *
     * @return An instance of bullet.
     */
    public static Bullet getInstance() { return bullet; }

    /**
     * Set the initial properties of the bullet
     */
    private Bullet()	{
        this.setL(true);
        this.setI(imgBullet);
        this.setW((int) getI().getWidth());
        this.setH((int) getI().getHeight());

    }

    /**
     * Set the position of the bullet
     */
    public void setPos(){
        this.setX((int) Play.mySnake.getHeadPoint().getX());
        this.setY((int) Play.mySnake.getHeadPoint().getY());
    }

    /**
     * Make the bullet move forward for each step
     */
    public void move(){
        // the step length of the bullet
        int speed = 10;

        // set the direction of the bullet image
        // towards up
        if(direction == 0) {this.setI(GameUtil.rotateImage(imgBullet,90));}
        // towards left
        if(direction == 1) {this.setI(imgBullet);}
        // towards down
        if(direction == 2) {this.setI(GameUtil.rotateImage(imgBullet,-90));}
        // towards right
        if(direction == 3) {this.setI(GameUtil.rotateImage(imgBullet,-180));}

        // move to the next step according to the direction
        if(direction == 0){
            this.setY(this.getY() - speed);
        } else if (direction == 1){
            this.setX(this.getX() - speed);
        } else if (direction == 2){
            this.setY(this.getY() + speed);
        } else if (direction == 3){
            this.setX(this.getX() + speed);
        }

        // if the bullet intersects with the enemy, then both will disappear
        if(this.getRectangle().getBoundsInParent().intersects(Enemy.getInstance().getRectangle().getBoundsInParent()) && Enemy.getInstance().getL()){
            MusicPlayer.getMusicPlayOnce("src/main/resources/dms/game/music/eat-music.mp3");
            if(Player.getInstance().getHumanVoice()==1){
                MusicPlayer.getMusicPlayOnce("src/main/resources/dms/game/music/shoot.mp3");
            }
            Enemy.getInstance().setL(false);
            this.setL(false);
            Play.isBullet = false;

            // add 200 to score if successfully shot the enemy
            Play.mySnake.setScore(Play.mySnake.getScore() + 200);
        }

        // if the bullet intersects with the brick, then the bullet will disappear
        if(this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick1().getRectangle().getBoundsInParent())
                || this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick2().getRectangle().getBoundsInParent())){
            this.setL(false);
            Play.isBullet = false;
        }

        // if the bullet arrives at the boundary, then it will disappear
        if(this.getX() <= 0 || this.getX() >= 900 - this.getW() || this.getY() <=0 || this.getY() >= 600 - this.getH()){
            this.setL(false);
            Play.isBullet = false;
        }

    }

    /**
     * Set the direction of the bullet
     *
     * @param direction An integer with 0 representing up, 1 representing left, 2 representing down, 3 representing right.
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * Get the direction of the bullet
     *
     * @return An integer representing the current direction
     */
    public int getDirection(){
        return direction;
    }

    /**
     * Draw the graphic of bullet
     *
     * @param g The GraphicsContext used to draw images
     */
    @Override
    public void draw(GraphicsContext g) {
        g.drawImage(this.getI(), this.getX(), this.getY());
    }
}
