package dms.game;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The Player class represents a player in the Snake game.
 *
 * <p> It contains the player's information and preference choices.
 * The class also provides functionality for storing and retrieving player data.</p>
 *
 * @author Qianyun Gong - modified
 * @version 1.0
 */

public class Player {
    private String name;
    private String feedback;
    private int coolMode;   // 0 for normal mode, 1 for cool mode
    private List<String> myScore = new ArrayList<>();   // record the score history of this player
    private final List<String> playerName = new ArrayList<>();  // record all the players name that once played
    private final List<String> rankList = new ArrayList<>();    // record the ranked players' names and scores
    private List<String> allPlayerScore = new ArrayList<>();    // record all the players' names and their score history
    private List<String> allTimeList = new ArrayList<>();   // record the real-time ranked scores and names

    private int mode;
    private int theme;
    private int humanVoice;
    private int snakeAppearance;
    private int musicType;
    private int logOut = 0;     // record whether the player clicks log out
    private int isContain = 0;  // record whether the player's name is in the name list of all players
    private int lastMax = -1;   // record the previous highest score of the player (used to update the all-time rank board)
    private int exceedTimes = 0;    // record whether the player's score this time break the record
    public static Player player = new Player();
    private boolean isNameChanged = false;
    private final Path scoreHistoryFile = Paths.get("src/main/resources/dms/data/scoreHistory.txt");
    private final Path ranListFile = Paths.get("src/main/resources/dms/data/rankList.txt");
    private final Path namesFile = Paths.get("src/main/resources/dms/data/names.txt");
    private final Path feedbackFile = Paths.get("src/main/resources/dms/data/feedback.txt");

    /**
     * Get an instance of the player.
     *
     * @return An instance of Player.
     */
    public static Player getInstance(){ return player; }

    /**
     * Add the player's score to the score history and updates the player's name in the record list.
     *
     * @param score The player's score to be added.
     */
    public void addScoreToHistory(int score){
        myScore.add(Integer.toString(score));

        if(isNameChanged) {
            // if the user enters the same name that is entered before, name will be re-added
            playerName.remove(name);
            // if the user enters another name, add to the record list
            allPlayerScore.add(name);
            playerName.add(name);
        }
        isNameChanged = false;

        // store player names in the file
        try{
            // clear the file first
            Files.write(namesFile, "".getBytes());
            try (BufferedWriter writer = Files.newBufferedWriter(namesFile)) {
                // rewrite to the file
                for (String item : playerName) {
                    writer.write(item);
                    if(playerName.size()!=playerName.indexOf(item)+1) writer.newLine();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        addToRankList();
    }

    /**
     * Store all the names and score history into the file and get the current player's score history list.
     *
     * @return The list of player's score history.
     */
    public List<String> getScoreHistory(){
        // store score history in the file
        allPlayerScore.addAll(myScore);
        try{
            // clear the file first
            Files.write(scoreHistoryFile, "".getBytes());
            try (BufferedWriter writer = Files.newBufferedWriter(scoreHistoryFile)) {
                // rewrite to the file
                for (String item : allPlayerScore) {
                    writer.write(item);
                    if(allPlayerScore.size()!=allPlayerScore.indexOf(item)+1) writer.newLine();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return myScore;
    }

    /**
     * Get the player's highest score from the score history.
     *
     * @return The highest score.
     */
    public int getMyHighestScore(){
        int max = 0;
        for(String i : myScore){
            if(Integer.parseInt(i) > max) max = Integer.parseInt(i);
        }
        return max;
    }

    /**
     * Add the player's name and highest score to the rank list for display.
     */
    public void addToRankList(){
        // if already in the rankList, then remove it and re-add it
        if(playerName.contains(name) && rankList.size()!=0) {
            for (String ss : rankList) {
                String getName = "";
                for (int i = 0; i < ss.length()-1; i++) {
                    // get the name of each item
                    if (!(ss.charAt(i) == ':' && ss.charAt(i + 1) == ' ')) {
                        getName += ss.charAt(i);
                    } else {
                        break;
                    }
                }
                // compare the name of the item with the current player's name
                if (getName.equals(name)) {
                    // if the player's name already exist, then remove the previous information
                    rankList.remove(ss);
                    break;
                }
            }
        }

        // add or re-add the player's name and highest score
        rankList.add(name + ": " + getMyHighestScore());
    }

    /**
     * Sort the rank list in descending order based on the highest scores.
     *
     * @return The sorted rank list.
     */
    public List<String> getRankList(){
        // store rankList in the file
        try{
            // clear the file first
            Files.write(ranListFile, "".getBytes());
            try (BufferedWriter writer = Files.newBufferedWriter(ranListFile)) {
                // rewrite to the file
                for (String item : rankList) {
                    writer.write(item);
                    if(rankList.size()!=rankList.indexOf(item)+1) writer.newLine();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // sort the rankList for display in order
        List<String> sortedRankList = rankList;
        sortedRankList.sort(new scoreCompare());
        // set lastMax and exceedTimes to initial values
        lastMax = -1;
        exceedTimes = 0;

        return sortedRankList;
    }

    /**
     * Get the player's all-time rank list.
     *
     * @return The current all-time rank list.
     */
    public List<String> allTimeRank(){
        // check whether the name is already in the list
        for(String i : allTimeList){
            String iName = i.split(":")[0];
            if (iName.equals(name)) {
                isContain = 1;
                break;
            }
        }
        // if name already exists, then remove and re-add the name and score information
        if(isContain == 1){
            for(String item : allTimeList){
                String itemName = item.split(":")[0];
                int itemScore = Integer.parseInt(item.split(":")[1].trim());

                if(itemName.equals(name)){
                    if(Play.mySnake.getScore() > itemScore){
                        if(exceedTimes == 0) lastMax = itemScore;
                        exceedTimes = 1;
                        allTimeList.remove(item);
                        allTimeList.add(name + ": " + Play.mySnake.getScore());
                    }
                    else if (Play.mySnake.getScore() < itemScore && lastMax != -1){
                        allTimeList.remove(item);
                        allTimeList.add(name + ": " + lastMax);
                        lastMax = -1;
                        exceedTimes = 0;
                    }
                    break;
                }
            }
        }
        // if name not in the list, then add the name and score into the list
        else {
            allTimeList.add(name + ": " + Play.mySnake.getScore());
        }

        //sort allTimeList
        List<String> sortedAllTimeList = allTimeList;
        sortedAllTimeList.sort(new scoreCompare());

        return sortedAllTimeList;
    }

    /**
     * Comparator class for comparing the highest score in the rank list.
     */
    public static class scoreCompare implements Comparator<String> {
        /**
         * Transform the string that contains the scores into integers and compare them.
         */
        @Override
        public int compare(String str1, String str2) {
            int num1, num2;
            if(str2.equals("")){
                num1 = Integer.parseInt(str1.split(":")[1].trim());
                num2 = 0;
            } else {
                num1 = Integer.parseInt(str1.split(":")[1].trim());
                num2 = Integer.parseInt(str2.split(":")[1].trim());
            }

            // sort from largest to smallest
            return Integer.compare(num2, num1);
        }
    }

    /**
     * Get the selected theme.
     *
     * @return An integer representing different themes
     */
    public int getTheme() {
        return theme;
    }

    /**
     * Set the theme.
     *
     * @param theme An integer of the theme to set
     */
    public void setTheme(int theme) {
        this.theme = theme;
    }

    /**
     * Get the selected music.
     *
     * @return An integer representing different music types
     */
    public int getMusicType() {
        return musicType;
    }

    /**
     * Set the music type.
     *
     * @param musicType An integer of the music type to set
     */
    public void setMusicType(int musicType) {
        this.musicType = musicType;
    }

    /**
     * Get the selected snake appearance.
     *
     * @return An integer representing different snake appearances
     */
    public int getSnakeAppearance() {
        return snakeAppearance;
    }

    /**
     * Set the snake appearance.
     *
     * @param snakeAppearance An integer of the snake appearance to set
     */
    public void setSnakeAppearance(int snakeAppearance) {
        this.snakeAppearance = snakeAppearance;
    }

    /**
     * Get the player's name
     *
     * @return A string of player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Set new name if the player enter another username
     *
     * @param name A string of the new name
     */
    public void setName(String name) {
        isNameChanged = true;
        this.name = name;

        // load the previous rankList from the file
        allPlayerScore = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(scoreHistoryFile);
            allPlayerScore.addAll(lines);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // load the previous player names from the file
        if(logOut == 0) {
            try {
                List<String> lines = Files.readAllLines(namesFile);
                playerName.addAll(lines);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // refresh the score history for the new username
        myScore = new ArrayList<>();

        // if the new name has been used before, then get the score history and remove the previous one(will be re-added later)
        if(playerName.contains(name)){
            int idx = allPlayerScore.indexOf(name);
            allPlayerScore.remove(idx);
            while(!playerName.contains(allPlayerScore.get(idx))){
                myScore.add(allPlayerScore.get(idx));
                allPlayerScore.remove(idx);
                if(allPlayerScore.size() < idx+1) break;
            }
        }

        // read rankList from the file
        if(logOut == 0) {
            try {
                List<String> lines = Files.readAllLines(ranListFile);
                rankList.addAll(lines);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        logOut = 0;
        allTimeList = rankList;
        isContain = 0;
    }

    /**
     * Set the 'log out' information.
     *
     * @param logOut An integer of the 'log out' information to set
     */
    public void setLogOut(int logOut) {
        this.logOut = logOut;
    }

    /**
     * Get the selected mode.
     *
     * @return An integer of the selected mode
     */
    public int getMode() {
        return mode;
    }

    /**
     * Set the mode.
     *
     * @param mode An integer of the mode to set
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

    /**
     * Store the feedback into the file.
     */
    public void storeFeedBack(){
        try (BufferedWriter writer = Files.newBufferedWriter(feedbackFile)) {
            // write to the file
            writer.write(feedback);
            writer.newLine();
        } catch (Exception e) {
        e.printStackTrace();
        }
    }

    /**
     * Set the feedback.
     *
     * @param feedback A string of the feedback content to set
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * Get whether current is cool mode.
     * @return An integer representing the current mode
     */
    public int getCoolMode() {
        return coolMode;
    }

    /**
     * Set whether in cool mode.
     *
     * @param coolMode An ineger representing the new mode
     */
    public void setCoolMode(int coolMode) {
        this.coolMode = coolMode;
    }

    /**
     * Get whether turn on or off the human voice prompt.
     * @return An integer representing turning on or off the voice
     */
    public int getHumanVoice() {
        return humanVoice;
    }

    /**
     * Set whether trun on or off the human voice prompt.
     *
     * @param humanVoice An ineger representing turning on or off the voice
     */
    public void setHumanVoice(int humanVoice) {
        this.humanVoice = humanVoice;
    }
}
