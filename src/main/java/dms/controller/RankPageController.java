package dms.controller;

import dms.game.MyFrame;
import dms.game.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller of the rank page that is linked to rankpage-view.fxml
 *
 * @author Qianyun Gong
 * @version 1.0
 */

public class RankPageController {
    @FXML
    private ListView<String> rankBoard;

    /** Set the rank board content */
    @FXML
    private void initialize(){
        Path rankFile = Paths.get("src/main/resources/dms/data/rankList.txt");
        List<String> rankList = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(rankFile);
            rankList.addAll(lines);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(rankList.size()!=0) {
            rankList.sort(new Player.scoreCompare());
            ObservableList<String> rankScore = FXCollections.observableArrayList(rankList);
            rankBoard.setItems(rankScore);
            rankBoard.setStyle("-fx-font-size: 18px;");
        }
    }

    /** Back to the home page when clicking 'back' button */
    @FXML
    protected void onBackClick() {
        MyFrame.getInstance().toHomePage();
    }
}
