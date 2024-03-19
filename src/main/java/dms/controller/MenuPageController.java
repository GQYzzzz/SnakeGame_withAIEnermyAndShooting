package dms.controller;

import dms.game.ImageUtil;
import dms.game.MusicPlayer;
import dms.game.MyFrame;
import dms.game.Player;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;

/**
 * Controller of the menu page that is linked to menupage-view.fxml
 *
 * @author Qianyun Gong
 * @version 1.0
 */

public class MenuPageController {
    @FXML private Label hiName;
    @FXML private ImageView menuBackground;
    @FXML private CheckBox cloud;
    @FXML private CheckBox ice;
    @FXML private CheckBox red;
    @FXML private CheckBox original;
    @FXML private CheckBox pikachu;
    @FXML private CheckBox easy;
    @FXML private CheckBox median;
    @FXML private CheckBox hard;
    @FXML private CheckBox upbeat;
    @FXML private CheckBox soft;
    @FXML private RadioButton humanVoice;

    /** Initialize items and group checkboxes per line */
    @FXML
    private void initialize(){
        hiName.setText("Hi, " + Player.getInstance().getName() + "!");

        if(Player.getInstance().getHumanVoice()==1){
            humanVoice.setSelected(true);
        }

        if(Player.getInstance().getCoolMode()==0){
            menuBackground.setImage(ImageUtil.images.get("menu-background"));
        } else {
            menuBackground.setImage(ImageUtil.images.get("menu-cool-background"));
        }

        cloud.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                ice.setSelected(false);
                red.setSelected(false);
            }
        });
        ice.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                cloud.setSelected(false);
                red.setSelected(false);
            }
        });
        red.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                ice.setSelected(false);
                cloud.setSelected(false);
            }
        });
        original.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                pikachu.setSelected(false);
            }
        });
        pikachu.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                original.setSelected(false);
            }
        });
        easy.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                median.setSelected(false);
                hard.setSelected(false);
            }
        });
        median.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                easy.setSelected(false);
                hard.setSelected(false);
            }
        });
        hard.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                easy.setSelected(false);
                median.setSelected(false);
            }
        });
        upbeat.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                soft.setSelected(false);
            }
        });
        soft.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                upbeat.setSelected(false);
            }
        });
    }

    /** Start the game scene when clicking 'play' button */
    @FXML
    protected void onPlayClick() {
        MusicPlayer.stopPlayingLoop();
        MyFrame.getInstance().gameStart();
    }

    /** Back to the home page when clicking 'log out' button */
    @FXML
    protected void onLogOutClick() {
        MyFrame.getInstance().toHomePage();
    }

    /** Set the cloud background when clicking 'cloud' checkbox */
    @FXML
    protected void onCloudThemeClick() {
        Player.getInstance().setTheme(0);
    }

    /** Set the ice background when clicking 'ice' checkbox */
    @FXML
    protected void onIceThemeClick() {
        Player.getInstance().setTheme(1);
    }

    /** Set the red background when clicking 'red' checkbox */
    @FXML
    protected void onRedThemeClick() {
        Player.getInstance().setTheme(2);
    }

    /** Set the original snake appearance when clicking 'original' checkbox */
    @FXML
    protected void onSnakeOriginalClick() {
        Player.getInstance().setSnakeAppearance(0);
    }

    /** Set the Pikachu snake appearance when clicking 'pikachu' checkbox */
    @FXML
    protected void onSnakePikachuClick() {
        Player.getInstance().setSnakeAppearance(1);
    }

    /** Set the upbeat background music when clicking 'upbeat' checkbox */
    @FXML
    protected void onUpbeatClick() {
        Player.getInstance().setMusicType(0);
    }

    /** Set the soft background music when clicking 'soft' checkbox */
    @FXML
    protected void onSoftClick() {
        Player.getInstance().setMusicType(1);
    }

    /** Set the easy mode when clicking 'easy' checkbox */
    @FXML
    protected void onEasyClick() {
        Player.getInstance().setMode(0);
    }

    /** Set the median mode when clicking 'median' checkbox */
    @FXML
    protected void onMedianClick() {
        Player.getInstance().setMode(1);
    }

    /** Set the hard mode when clicking 'hard' checkbox */
    @FXML
    protected void onHardClick() {
        Player.getInstance().setMode(2);
    }

    /** Set whether turn on or off the human voice */
    @FXML
    protected void onHumanVoiceClick() {
        if(humanVoice.isSelected()) {
            Player.getInstance().setHumanVoice(1);
        } else {
            Player.getInstance().setHumanVoice(0);
        }
    }
}
