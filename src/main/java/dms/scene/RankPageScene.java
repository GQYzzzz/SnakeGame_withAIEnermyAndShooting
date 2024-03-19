package dms.scene;

import dms.Main;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The rank page scene accessed from home page in the Snake game.
 *
 * @author Qianyun Gong
 * @version 1.0
 */

public class RankPageScene {
    public static void load(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/dms/controller/javafx/rankpage-view.fxml"));
            stage.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
