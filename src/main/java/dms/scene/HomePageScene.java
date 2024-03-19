package dms.scene;

import dms.Main;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The home page scene in the Snake game.
 *
 * @author Qianyun Gong
 * @version 1.0
 */

public class HomePageScene {
    public static void load(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/dms/controller/javafx/homepage-view.fxml"));
            stage.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
