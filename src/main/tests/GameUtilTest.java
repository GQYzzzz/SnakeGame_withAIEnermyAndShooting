import dms.game.GameUtil;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import org.junit.Before;
import org.junit.Test;


import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * JUnit test for GameUtil class.
 *
 * @author Qianyun Gong
 * @version 1.0
 */

public class GameUtilTest {
    /** Simulate the initialization of the JavaFX environment before the test */
    @Before
    public void beforeAll(){
        new JFXPanel();
    }

    /** Test the getImage method */
    @Test
    public void testGetImage(){
        assertNotNull(GameUtil.getImage("dms/game/snake/snake-head-right.png"));
    }

    /** Test the rotateImage method */
    @Test
    public void testRotateImage() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // test in the JavaFX thread
        Platform.runLater(() -> {
            Image i = new Image("dms/game/snake/snake-head-right.png");
            assertNotNull(GameUtil.rotateImage(i,90));

            // count reduce 1
            latch.countDown();
        });

        // wait for finish JavaFX thread
        latch.await();
    }
}
