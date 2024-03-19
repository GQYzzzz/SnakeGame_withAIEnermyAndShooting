package dms.game;

import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for managing images in the game.
 *
 * <p>This class provides a map of image names,
 * making it easy to retrieve images throughout the game.</p>
 *
 * @author Qianyun Gong - modified
 * @version 1.0
 */

public class ImageUtil
{
	public static Map<String, Image> images = new HashMap<>();

	// A map that stores images with their corresponding names.
	static
	{
		// snake
		images.put("snake-head-right", GameUtil.getImage("dms/game/snake/snake-head-right.png"));
		images.put("snake-body", GameUtil.getImage("dms/game/snake/snake-body.png"));
		images.put("snake-yellow-head", GameUtil.getImage("dms/game/snake/snake-yellow-head.png"));
		images.put("snake-yellow-body", GameUtil.getImage("dms/game/snake/snake-yellow-body.png"));
		images.put("snake-yellow-tail", GameUtil.getImage("dms/game/snake/snake-yellow-tail.png"));
		images.put("snake-logo",GameUtil.getImage("dms/game/snake/snake-logo.png"));
		// objects
		images.put("enemy", GameUtil.getImage("dms/game/objects/enemy.png"));
		images.put("brick", GameUtil.getImage("dms/game/objects/brick.png"));
		images.put("bullet", GameUtil.getImage("dms/game/objects/bullet.png"));
		images.put("score-reduce", GameUtil.getImage("dms/game/objects/score-reduce.png"));
		images.put("0", GameUtil.getImage("dms/game/food/food-kiwi.png"));
		images.put("1", GameUtil.getImage("dms/game/food/food-lemon.png"));
		images.put("2", GameUtil.getImage("dms/game/food/food-litchi.png"));
		images.put("3", GameUtil.getImage("dms/game/food/food-mango.png"));
		images.put("4", GameUtil.getImage("dms/game/food/food-apple.png"));
		images.put("5", GameUtil.getImage("dms/game/food/food-banana.png"));
		images.put("6", GameUtil.getImage("dms/game/food/food-blueberry.png"));
		images.put("7", GameUtil.getImage("dms/game/food/food-cherry.png"));
		images.put("8", GameUtil.getImage("dms/game/food/food-durian.png"));
		images.put("9", GameUtil.getImage("dms/game/food/food-grape.png"));
		images.put("10", GameUtil.getImage("dms/game/food/food-grapefruit.png"));
		images.put("11", GameUtil.getImage("dms/game/food/food-peach.png"));
		images.put("12", GameUtil.getImage("dms/game/food/food-pear.png"));
		images.put("13", GameUtil.getImage("dms/game/food/food-orange.png"));
		images.put("14", GameUtil.getImage("dms/game/food/food-pineapple.png"));
		images.put("15", GameUtil.getImage("dms/game/food/food-strawberry.png"));
		images.put("16", GameUtil.getImage("dms/game/food/food-watermelon.png"));
		// backgrounds
		images.put("cloud-background", GameUtil.getImage("dms/game/background/UI-background-cloud.png"));
		images.put("ice-background", GameUtil.getImage("dms/game/background/UI-background-ice.jpg"));
		images.put("red-background", GameUtil.getImage("dms/game/background/UI-background-red.jpg"));
		images.put("feedback-background", GameUtil.getImage("dms/game/background/UI-background-feedback.jpeg"));
		images.put("home-background", GameUtil.getImage("dms/game/background/snake-background.jpg"));
		images.put("home-cool-background", GameUtil.getImage("dms/game/background/UI-background-home-cool.png"));
		images.put("menu-background", GameUtil.getImage("dms/game/background/UI-background-menu.jpeg"));
		images.put("menu-cool-background", GameUtil.getImage("dms/game/background/UI-background-menu-cool.png"));

	}
}
