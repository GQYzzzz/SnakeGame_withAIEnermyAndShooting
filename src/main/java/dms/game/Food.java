package dms.game;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

import java.util.Random;

/**
 * Represents a food object in the game.
 *
 * <p>The food object is randomly positioned in the game area. It provides
 * methods to check for overlap with the snake's body, check if the snake eats
 * the food, and draw the graphic representation of the food.</p>
 *
 * @author Qianyun Gong - modified
 * @version 1.0
 */

public class Food extends Object
{
	public static Food food = new Food();

	/**
	 * Get the food instance.
	 *
	 * @return An instance of food.
	 */
	public static Food getInstance(){ return food; }

	/**
	 * Set the initial properties of the food
	 */
	private Food()	{
		refreshFood();
	}

	/**
	 * Check whether the food overlaps with the snake's body
	 *
	 * @return True if overlapped, false if not overlapped
	 */
	public Boolean checkOverlap(){
		for(Point2D point : Play.mySnake.getBodyPoints()){
			if(this.getRectangle().getBoundsInParent().intersects(getBodyPointRectangle(
					(int)point.getX(),(int)point.getY(),Play.mySnake.getW(), Play.mySnake.getH()).getBoundsInParent())){
				return true;
			}
		}
		return false;
	}

	/**
	 * Get the rectangle of the food
	 *
	 * @param x The x coordinate of the food
	 * @param y The y coordinate of the food
	 * @param w The width of the food
	 * @param h The height of the food
	 * @return The rectangle of the food instance
	 */
	public Rectangle getBodyPointRectangle (int x, int y, int w, int h){
		return new Rectangle(x, y, w, h);
	}

	/**
	 * Check whether the snake eats the food and update the game states accordingly
	 *
	 * @param mySnake An instance of MySnake
	 */
	public void eaten(MySnake mySnake)	{
		// check whether the snake's head touches the food
		if (mySnake.getRectangle().intersects(this.getRectangle().getBoundsInParent()) && this.getL() && mySnake.getL()){
			MusicPlayer.getMusicPlayOnce("src/main/resources/dms/game/music/eat-music.mp3");
			if(Player.getInstance().getHumanVoice()==1) {
				MusicPlayer.getMusicPlayOnce("src/main/resources/dms/game/music/score100.mp3");
			}
			this.setL(false);
			mySnake.changeLength(mySnake.getLength() + 1);
			mySnake.setScore(mySnake.getScore() + 100);
		}
	}

	/**
	 * Refresh the random food image and position
	 */
	public void refreshFood(){
		this.setL(true);

		// randomly select an image for food
		this.setI(ImageUtil.images.get(String.valueOf(new Random().nextInt(17))));

		this.setW((int) getI().getWidth());
		this.setH((int) getI().getHeight());

		// set the random position of the food
		this.setX((int) (Math.random() * (900 - getW())));
		this.setY((int) (Math.random() * (600 - getH())));

		// avoid intersects with the brick, the refreshed enemy and the snake's body
		if(Player.getInstance().getMode()==1 || Player.getInstance().getMode()==2){
			while (checkOverlap() || this.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick1().getRectangle().getBoundsInParent())
					|| this.getRectangle().intersects(Brick.getInstanceBrick2().getRectangle().getBoundsInParent()) || this.getX()==800 && this.getY()==500){
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
	 * Draw the graphic of food
	 *
	 * @param g The GraphicsContext used to draw images
	 */
	@Override
	public void draw(GraphicsContext g)
	{
		g.drawImage(this.getI(), this.getX(), this.getY());
	}
}
