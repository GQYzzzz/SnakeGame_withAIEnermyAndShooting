package dms.controller;

import dms.game.MyFrame;
import dms.game.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * Controller of the feedback page that is linked to feedback-view.fxml
 *
 * @author Qianyun Gong
 * @version 1.0
 */

public class FeedbackController {
    @FXML private TextArea feedback;
    @FXML private Label prompt;

    /** Back to the home page when clicking 'back' button */
    @FXML
    protected void onBackClick() {
        MyFrame.getInstance().toHomePage();
    }

    /** Store the data into the file and back to the home page when clicking 'submit' button */
    @FXML
    protected void onSubmitClick() {
        if(feedback.getText().isEmpty()){
            prompt.setText("Please enter before submit!");
        } else {
            Player.getInstance().setFeedback(feedback.getText());
            Player.getInstance().storeFeedBack();
            MyFrame.getInstance().toHomePage();
        }
    }
}
