import dms.game.*;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.CountDownLatch;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for Brick class.
 *
 * @author Qianyun Gong
 * @version 1.0
 */

public class BrickTest{
    /** Simulate the initialization of the JavaFX environment before the test */
    @Before
    public void beforeAll(){
        new JFXPanel();
    }

    /** Test the getInstanceBrick1 method of Brick */
    @Test
    public void testGetInstanceBrick1(){
        assertNotNull(Brick.getInstanceBrick1());
    }

    /** Test the getInstanceBrick2 method of Brick */
    @Test
    public void testGetInstanceBrick2(){
        assertNotNull(Brick.getInstanceBrick2());
    }

    /** Test whether the snake will crash the brick if the coordinate is overlapped */
    @Test
    public void testCrash() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // test in the JavaFX thread
        Platform.runLater(() -> {
            Brick brick = Brick.getInstanceBrick1();
            brick.setX(100);
            brick.setY(100);

            brick.crash(MySnake.getInstance());
            assertFalse(MySnake.getInstance().getL());

            // count reduce 1
            latch.countDown();
        });

        // wait for finish JavaFX thread
        latch.await();
    }
}
