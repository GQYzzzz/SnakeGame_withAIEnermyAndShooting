package dms.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 * The Play class represents the main gameplay logic and UI elements for the Snake game.
 * <p> It initializes the game components and manages the game loop. </p>
 *
 * @author Qianyun Gong - modified
 * @version 1.0
 */

public class Play
{
	private final Canvas canvas = new Canvas(MyFrame.WIDTH, MyFrame.HEIGHT);
	private final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
	public static MySnake mySnake;
	public Brick brick1;
	public Brick brick2;
	public Enemy enemy;
	public static Bullet bullet;
	public static Boolean isBullet = false;
	public static Food food;
	public static ScoreReduce scoreReduce;
	private KeyControl keyControl;
	private final Image cloudBackground = ImageUtil.images.get("cloud-background");
	private final Image iceBackground = ImageUtil.images.get("ice-background");
	private final Image redBackground = ImageUtil.images.get("red-background");
	private final Timeline timeline = new Timeline();
	private final Label prompt = new Label("(Press  'Space'  to pause/continue and  'E'  to end the snake.game.)");
	private final Label shootPrompt = new Label("(Press  'S'  to shoot.)");
	private final Label title = new Label("RANK LIST");
	private final Button pause = new Button("Pause/Continue");
	private final ListView<String> board = new ListView<>();	// used to display the rank list

	/**
	 * Initialize the game components and set up the UI.
	 *
	 * @param stage The JavaFX stage for the game.
	 */
	public void init(Stage stage){
		AnchorPane root = new AnchorPane(canvas);
		// draw the background image according to the players' choice
		if(Player.getInstance().getTheme()==0) {
			graphicsContext.drawImage(cloudBackground, 0, 0, 900, 600);
		} else if (Player.getInstance().getTheme()==1) {
			graphicsContext.drawImage(iceBackground, 0, 0, 900, 600);
		} else {
			graphicsContext.drawImage(redBackground, 0, 0, 900, 600);
		}

		// set up the stage with the root
		stage.getScene().setRoot(root);
		stage.setWidth(900);
		stage.setHeight(628);
		stage.setResizable(false);

		keyControl = new KeyControl();
		stage.getScene().setOnKeyPressed(keyControl);

		// set the different background music according to the preference choice of player
		if(Player.getInstance().getMusicType()==0) {
			MusicPlayer.getMusicPlayLoop("src/main/resources/dms/game/music/frogger.mp3");
		} else {
			MusicPlayer.getMusicPlayLoop("src/main/resources/dms/game/music/soft.mp3");
		}

		// create new objects of snake, brick, food, scoreReduce and enemy
		mySnake = MySnake.getInstance();
		mySnake.refreshSnake(100,100);


		if(Player.getInstance().getMode()==1 || Player.getInstance().getMode()==2){
			brick1 = Brick.getInstanceBrick1();
			brick2 = Brick.getInstanceBrick2();
			// refresh the position of each brick
			brick1.refreshPos();
			brick2.refreshPos();
			// avoid overlap between two bricks
			while(brick1.getRectangle().getBoundsInParent().intersects(brick2.getRectangle().getBoundsInParent())){
				brick2.refreshPos();
			}

		}

		food = Food.getInstance();
		scoreReduce = ScoreReduce.getInstance();

		if(Player.getInstance().getMode()==2){
			enemy = Enemy.getInstance();
			enemy.setL(true);
			enemy.setX(800);
			enemy.setY(500);
		}

		// add the prompt label to the root
		prompt.setLayoutX(265);
		prompt.setLayoutY(20);
		Font font = new Font(16);
		prompt.setFont(font);
		prompt.setStyle("-fx-text-fill: white;");
		root.getChildren().add(prompt);

		if(Player.getInstance().getMode() == 2){
			shootPrompt.setLayoutX(390);
			shootPrompt.setLayoutY(40);
			Font font3 = new Font(16);
			shootPrompt.setFont(font3);
			shootPrompt.setStyle("-fx-text-fill: white;");
			root.getChildren().add(shootPrompt);
		}

		title.setLayoutX(767);
		title.setLayoutY(8);
		Font font1 = new Font(16);
		title.setFont(font1);
		title.setStyle("-fx-text-fill: yellow;");
		root.getChildren().add(title);

		// add the rank board to the root
		board.setOpacity(0.3);
		board.setLayoutX(740);
		board.setLayoutY(30);
		board.setPrefHeight(130);
		board.setPrefWidth(130);
		ObservableList<String> rank = FXCollections.observableArrayList(Player.getInstance().allTimeRank());
		board.setItems(rank);
		board.setFocusTraversable(false);

		// set the style of the rank board
		board.setCellFactory(new Callback<>() {
			@Override
			public ListCell<String> call(ListView<String> param) {
				return new ListCell<>() {
					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);

						// judge whether ListCell is empty
						if (empty || item == null) {
							setText(null);
							setStyle(""); // clear the style
						} else {
							setText(item);
							setStyle("-fx-text-fill: blue; -fx-font-size: 18px;");
						}
					}
				};
			}
		});
		root.getChildren().add(board);

		// add the pause or continue button to the root
		pause.setLayoutX(30);
		pause.setLayoutY(50);
		Font font2 = new Font(13);
		pause.setFont(font2);
		pause.setOpacity(0.5);
		pause.setFocusTraversable(false);
		root.getChildren().add(pause);

		pause.setOnAction(e1 -> mySnake.setRunning(!mySnake.getRunning()));
		root.requestFocus();

		// set timeline to infinite loop
		timeline.setCycleCount(Timeline.INDEFINITE);

		KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.03), event -> {
			// paint every 0.03 second if it is not paused
			if(mySnake.getRunning()) {
				paint(graphicsContext);
				stage.getScene().setOnKeyPressed(keyControl);
				ObservableList<String> rank1 = FXCollections.observableArrayList(Player.getInstance().allTimeRank());
				board.setItems(rank1);
			}
		});
		// set up the timeline
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();

		// reset the enemy
		Enemy.getInstance().setIsStart(0);
		Enemy.getInstance().setIsEqualX(0);
	}

	/**
	 * Draw the objects' images according to the current states of each object
	 *
	 * @param g The GraphicsContext used to draw images
	 */
	public void paint(GraphicsContext g)
	{
		// draw the background image
		if(Player.getInstance().getTheme()==0) {
			g.drawImage(cloudBackground,0,0, 900,600);
		} else if (Player.getInstance().getTheme()==1) {
			g.drawImage(iceBackground, 0, 0, 900, 600);
		} else {
			g.drawImage(redBackground, 0, 0, 900, 600);
		}

		// draw the bricks
		if(Player.getInstance().getMode()==1 || Player.getInstance().getMode()==2) {
			brick1.draw(g);
			brick2.draw(g);
			brick1.crash(mySnake);
			brick2.crash(mySnake);
		}

		// draw the enemy
		if(Player.getInstance().getMode()==2 && enemy.getL()){
			enemy.draw(g);
			enemy.move();
			enemy.crash(mySnake);
		}

		// draw the bullet
		if(isBullet){
			bullet.draw(g);
			bullet.move();
		}

		// draw the snake, food and scoreReduce
		if (mySnake.getL())
		{
			mySnake.draw(g);
			if (food.getL())
			{
				food.draw(g);
				food.eaten(mySnake);
			} else {
				food.refreshFood();
				if(Player.getInstance().getMode()==2) {
					Enemy.getInstance().setIsEqualX(0);
					Enemy.getInstance().setIsStart(0);
					// if the enemy is shot during this round, then it will appear again in the next round
					if (!enemy.getL()) {
						enemy.setL(true);
						// set the position of the refreshed enemy
						enemy.setX(800);
						enemy.setY(500);
						while (enemy.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick1().getRectangle().getBoundsInParent())
								|| enemy.getRectangle().getBoundsInParent().intersects(Brick.getInstanceBrick2().getRectangle().getBoundsInParent())) {
							enemy.setX(enemy.getX() - 5);
						}
					}
				}
			}

			// draw the scoreReduce
			if(scoreReduce.getL()){
				scoreReduce.draw(g);
				scoreReduce.crash(mySnake);
			} else {
				// if eaten, then generate a new scoreReduce
				scoreReduce.refreshScoreReduce();
			}
		} else
		{
			// if snake's visible state is false, then the game will end
			timeline.stop();
			Player.getInstance().addScoreToHistory(mySnake.getScore());
			MusicPlayer.stopPlayingLoop();
			MyFrame.getInstance().toEndPage();
			MusicPlayer.getMusicPlayOnce("src/main/resources/dms/game/music/end-music.mp3");
		}
		// display the real-time score
		drawScore(g);
	}

	/**
	 * Draw the current score
	 *
	 * @param g The GraphicsContext used to draw score
	 */
	public void drawScore(GraphicsContext g)
	{
		g.setFont(new Font("Arial",30));
		g.setFill(Color.MAGENTA);
		g.fillText("SCORE : " + mySnake.getScore(), 20, 40);
	}

}
