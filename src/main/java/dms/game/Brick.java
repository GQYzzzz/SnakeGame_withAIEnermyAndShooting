package dms.game;

import javafx.scene.canvas.GraphicsContext;

/**
 * Represents a brick object in the game.
 *
 * <p> If the snake touches the brick, then game will end.
 * The enemy will move around the brick </p>
 *
 * @author Qianyun Gong
 * @version 1.0
 */

public class Brick extends Object {
    private static final Brick brick1 = new Brick();
    private static final Brick brick2 = new Brick();

    /**
     * Get the brick instance 1.
     *
     * @return An instance of brick.
     */
    public static Brick getInstanceBrick1(){
        return brick1; }

    /**
     * Get the brick instance 2.
     *
     * @return An instance of brick.
     */
    public static Brick getInstanceBrick2(){ return brick2; }

    /**
     * Set the initial properties of the brick
     */
    private Brick()	{
        this.setL(true);

        this.setI(ImageUtil.images.get("brick"));

        this.setW((int) getI().getWidth());
        this.setH((int) getI().getHeight());

        // Set the random position of the brick
        this.setX((int) (Math.random() * (900 - getW())));
        this.setY((int) (Math.random() * (600 - getH())));

        //Reset the random position to avoid overlap with snake and enemy at the beginning of the game
        while(this.getX() <= 250 && this.getY() <= 150 || this.getX() >= 750-this.getW() && this.getY() >= 450-this.getH()){
            this.setX((int) (Math.random() * (900 - getW())));
            this.setY((int) (Math.random() * (600 - getH())));
        }
    }

    /**
     * Check if the snake crashes with the brick
     *
     * @param mySnake An instance of MySnake
     */
    public void crash(MySnake mySnake)	{
        //check whether the snake's head touches the brick
        if (mySnake.getRectangle().intersects(this.getRectangle().getBoundsInParent()) && this.getL() && mySnake.getL()){
            MusicPlayer.getMusicPlayOnce("src/main/resources/dms/game/music/crash.mp3");
            if(Player.getInstance().getHumanVoice()==1){
                MusicPlayer.getMusicPlayOnce("src/main/resources/dms/game/music/crash-prompt.mp3");
            }
            mySnake.setL(false);
        }
    }

    /**
     * Refresh the random position
     */
    public void refreshPos(){
        this.setX((int) (Math.random() * (900 - getW())));
        this.setY((int) (Math.random() * (600 - getH())));

        //Reset the random position to avoid overlap with snake and enemy at the beginning of the game
        while(this.getX() <= 250 && this.getY() <= 150 || this.getX() >= 750-this.getW() && this.getY() >= 450-this.getH()){
            this.setX((int) (Math.random() * (900 - getW())));
            this.setY((int) (Math.random() * (600 - getH())));
        }
    }

    /**
     * Draw the graphic of brick
     *
     * @param g The GraphicsContext used to draw images
     */
    @Override
    public void draw(GraphicsContext g) {
        g.drawImage(this.getI(), this.getX(), this.getY());
    }
}
