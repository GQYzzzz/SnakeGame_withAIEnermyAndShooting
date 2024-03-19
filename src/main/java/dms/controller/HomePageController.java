package dms.controller;

import dms.game.ImageUtil;
import dms.game.MyFrame;
import dms.game.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Controller of the home page that is linked to homepage-view.fxml
 *
 * @author Qianyun Gong
 * @version 1.0
 */

public class HomePageController {
    @FXML private TextField nameField;
    @FXML private Label prompt;
    @FXML private RadioButton coolMode;
    @FXML private ImageView homeBackground;

    /** Initialize the background image */
    @FXML
    private void initialize() {
        if (Player.getInstance().getCoolMode() == 0) {
            homeBackground.setImage(ImageUtil.images.get("home-background"));
        } else {
            homeBackground.setImage(ImageUtil.images.get("home-cool-background"));
            coolMode.setSelected(true);
        }
    }

    /** Exit the program when clicking 'exit' button */
    @FXML
    protected void onButtonExitClick() {
        System.exit(0);
    }

    /** Go to the menu page when clicking 'start' button */
    @FXML
    protected void onStartClick() {
        if(nameField.getText().isEmpty()) {
            prompt.setText("Please enter your name before start!");
        } else {
            Player.getInstance().setName(nameField.getText());
            MyFrame.getInstance().toMenuPage();
        }
    }

    /** Go to the feedback page when clicking 'feedback & help' button */
    @FXML
    protected void onFeedbackClick() {
        MyFrame.getInstance().toFeedbackPage();
    }

    /** Go to the rank board page when clicking 'rank' button */
    @FXML
    protected void onRankClick() {
        MyFrame.getInstance().toRankBoardPage();
    }

    /** Change the mode */
    @FXML
    protected void onCoolModeClick() {
        if(coolMode.isSelected()){
            Player.getInstance().setCoolMode(1);
            homeBackground.setImage(ImageUtil.images.get("home-cool-background"));
        } else {
            Player.getInstance().setCoolMode(0);
            homeBackground.setImage(ImageUtil.images.get("home-background"));
        }
    }
}

