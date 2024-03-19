package dms.game;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

/**
 * Utility class for obtaining and manipulating images in the game.
 *
 * <p>This class provides methods for retrieving images from resource paths
 * and rotating images by a specified degree.</p>
 *
 * @author Qianyun Gong - modified
 * @version 1.0
 */

public class GameUtil
{
	/**
	 * Get an image form the specified path
	 *
	 * @param imagePath A string which represents the path of the image
	 * @return An image of type Image, or null if the image is not found.
	 */
	public static Image getImage(String imagePath)
	{
		Image i = null;
		try {
			i = new Image(imagePath);
		} catch (Exception e)
		{
			System.err.println("No Image Found!\n");
			e.printStackTrace();
		}

		return i;
	}

	/**
	 * Rotate the given image by the specified degree.
	 *
	 * @param image The image to be rotated.
	 * @param degree The degree by which the image should be rotated.
	 * @return The rotated image.
	 */
	public static Image rotateImage(final Image image, final int degree)
	{
		// rotate the image in the imageView
		ImageView imageView = new ImageView(image);
		Rotate rotate = new Rotate();
		rotate.setAngle(degree);
		imageView.getTransforms().add(rotate);

		// transform the background of snapshot image to be transparent
		SnapshotParameters snapshotParams = new SnapshotParameters();
		snapshotParams.setFill(Color.TRANSPARENT.invert());

		return  imageView.snapshot(snapshotParams,null);
	}
}
