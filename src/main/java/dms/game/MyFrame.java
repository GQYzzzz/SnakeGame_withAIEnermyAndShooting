package dms.game;

import dms.scene.*;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;

/**
 * The main frame for the game.
 *
 * <p>This class initializes the main stage and provides methods to navigate
 * between different scenes in the game.</p>
 *
 * @author Qianyun Gong - modified
 * @version 1.0
 */

public class MyFrame
{
	public static final double WIDTH = 900, HEIGHT = 600;
	public static MyFrame instance = new MyFrame();
	private Stage stage;
	public Image logo = ImageUtil.images.get("snake-logo");

	/**
	 * Gets the instance of MyFrame.
	 *
	 * @return The instance of MyFrame.
	 */
	public static MyFrame getInstance() { return instance; }

	/**
	 * Initialize the primary stage and set up the initial Home Page scene.
	 *
	 * @param primaryStage The primary stage of the game.
	 */
	public void init(Stage primaryStage) {
		AnchorPane root = new AnchorPane();

		Scene scene = new Scene(root, WIDTH, HEIGHT);
		primaryStage.setTitle("Snake Yipee");
		primaryStage.getIcons().add(logo);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(event -> System.exit(0));

		primaryStage.setWidth(900);
		primaryStage.setHeight(628);
		primaryStage.setResizable(false);

		stage = primaryStage;
		primaryStage.show();

		toHomePage();
	}

	/**
	 * Navigates to the Home Page scene.
	 */
	public void toHomePage() { HomePageScene.load(stage); }

	/**
	 * Navigates to the Menu Page scene.
	 */
	public void toMenuPage() { MenuPageScene.load(stage); }

	/**
	 * Navigates to the End Page scene.
	 */
	public void toEndPage() { EndPageScene.load(stage); }

	/**
	 * Navigates to the Feedback Page scene.
	 */
	public void toFeedbackPage() { FeedbackScene.load(stage); }

	/**
	 * Navigates to the rank board Page scene.
	 */
	public void toRankBoardPage() { RankPageScene.load(stage); }

	/**
	 * Start the game by creating a new Play instance.
	 */
	public void gameStart() {
		new Play().init(stage);
	}

}
