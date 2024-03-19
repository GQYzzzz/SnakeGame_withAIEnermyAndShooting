import dms.game.Player;
import javafx.embed.swing.JFXPanel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * JUnit test for Player class.
 *
 * @author Qianyun Gong
 * @version 1.0
 */

public class PlayerTest {
    /** Simulate the initialization of the JavaFX environment before the test */
    @Before
    public void beforeAll(){
        new JFXPanel();
    }

    /** Test the getInstance method of Player */
    @Test
    public void testGetInstance(){
        assertNotNull(Player.getInstance());
    }

    /** Test the addScoreToHistory method of Player */
    @Test
    public void testAddScoreToHistory(){
        Player.getInstance().addScoreToHistory(100);
        assertEquals(1,Player.getInstance().getScoreHistory().size());
    }

    /** Test the addToRankList method of Player */
    @Test
    public void testAddToRankList(){
        Player.getInstance().addScoreToHistory(100);
        Player.getInstance().addToRankList();
        // name and score is two elements
        assertEquals(2,Player.getInstance().getRankList().size());
    }

    /** Test the setName method of Player */
    @Test
    public void testSetName(){
        Player.getInstance().setName("Betty");
        assertEquals("Betty",Player.getInstance().getName());
    }
}
