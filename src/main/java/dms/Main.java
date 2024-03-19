package dms;

import dms.game.MusicPlayer;
import dms.game.MyFrame;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main entry for the game
 *
 * <p> A class that extends Application which is used to run the game
 * The Main class initializes the game frame and starts the background
 * music upon application launch.</p>
 *
 * @author Qianyun Gong
 * @version 1.0
 */

public class Main extends Application {
    /**
     * Call the init method in MyFrame class and start the music loop
     *
     * <p> This method is overridden according to the start method in Application
     *
     * @param stage The stage of the game.
     */
    @Override
    public void start(Stage stage) {
        MyFrame.getInstance().init(stage);
        MusicPlayer.getMusicPlayLoop("src/main/resources/dms/game/music/home-music.mp3");
    }

    /**
     * The main method, launches the JavaFX application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch();
    }
}
