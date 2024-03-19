package dms.controller;

import dms.game.MusicPlayer;
import dms.game.MyFrame;
import dms.game.Play;
import dms.game.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * Controller of the end page that is linked to endpage-view.fxml

 * @author Qianyun Gong
 * @version 1.0
 */

public class EndPageController {
    @FXML private Label scoreLabel;
    @FXML private Label highestScore;
    @FXML private ListView<String> rankList;
    @FXML private ListView<String> scoreHistory;
    ObservableList<String> myScore = FXCollections.observableArrayList(Player.getInstance().getScoreHistory());
    ObservableList<String> rankScore = FXCollections.observableArrayList(Player.getInstance().getRankList());

    /** Set the style of score display items */
    @FXML
    private void initialize(){
        scoreLabel.setText("SCORE : " + Play.mySnake.getScore());
        scoreHistory.setItems(myScore);
        scoreHistory.setStyle("-fx-font-size: 18px;");
        rankList.setItems(rankScore);
        rankList.setStyle("-fx-font-size: 18px;");
        highestScore.setText("Your highest score :  " + Player.getInstance().getMyHighestScore());
    }

    /** Exit the program when clicking 'exit' button */
    @FXML
    protected void onExitClick() {
        System.exit(0);
    }

    /** Back to the home page when clicking 'log out' button */
    @FXML
    protected void onLogOutClick() {
        Player.getInstance().setLogOut(1);
        MusicPlayer.stopPlayingOnce();
        MyFrame.getInstance().toHomePage();
        MusicPlayer.getMusicPlayLoop("src/main/resources/dms/game/music/home-music.mp3");
    }

    /** Back to the menu page when clicking the 'menu' button */
    @FXML
    protected void onMenuClick() {
        MusicPlayer.stopPlayingOnce();
        MyFrame.getInstance().toMenuPage();
        MusicPlayer.getMusicPlayLoop("src/main/resources/dms/game/music/home-music.mp3");
    }

    /** Load the game again when clicking the 'again' button */
    @FXML
    protected void onAgainClick() {
        MusicPlayer.stopPlayingOnce();
        MyFrame.getInstance().gameStart();
    }
}
