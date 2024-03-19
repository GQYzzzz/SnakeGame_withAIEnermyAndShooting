import dms.game.Brick;
import dms.game.Food;
import dms.game.MySnake;
import dms.game.Play;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Point2D;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for Food class.
 *
 * @author Qianyun Gong
 * @version 1.0
 */

public class FoodTest {
    /** Simulate the initialization of the JavaFX environment before the test */
    @Before
    public void beforeAll(){
        new JFXPanel();
    }

    /** Test the getInstance method of Food */
    @Test
    public void testGetInstance() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // test in the JavaFX thread
        Platform.runLater(() -> {
            Play.mySnake = MySnake.getInstance();
            assertNotNull(Food.getInstance());

            // count reduce 1
            latch.countDown();
        });

        // wait for finish JavaFX thread
        latch.await();
    }

    /** Test the checkOverlap method of Food  */
    @Test
    public void testCheckOverlap() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // test in the JavaFX thread
        Platform.runLater(() -> {
            MySnake.getInstance().setBodyPoints(new Point2D(100,100));
            Play.mySnake = MySnake.getInstance();
            Food.getInstance().setX(100);
            Food.getInstance().setY(100);
            assertTrue(Food.getInstance().checkOverlap());

            // count reduce 1
            latch.countDown();
        });

        // wait for finish JavaFX thread
        latch.await();
    }

    /** Test the eaten method of Food  */
    @Test
    public void testEaten() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // test in the JavaFX thread
        Platform.runLater(() -> {
            MySnake.getInstance().setHeadPoint(new Point2D(100,100));
            Play.mySnake = MySnake.getInstance();
            Food.getInstance().setX(100);
            Food.getInstance().setY(100);
            Food.getInstance().eaten(Play.mySnake);
            assertFalse(Food.getInstance().getL());

            // count reduce 1
            latch.countDown();
        });

        // wait for finish JavaFX thread
        latch.await();
    }

    /** Test the refreshFood method of Food  */
    @Test
    public void testRefreshFood() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // test in the JavaFX thread
        Platform.runLater(() -> {
            Brick.getInstanceBrick1().setX(700);
            Brick.getInstanceBrick1().setY(500);
            Brick.getInstanceBrick2().setX(500);
            Brick.getInstanceBrick2().setY(400);
            Play.mySnake = MySnake.getInstance();

            Food.getInstance().setX(100);
            Food.getInstance().setY(200);
            Food.getInstance().refreshFood();
            // check if the new position is not the same as the previous one
            assertNotEquals(100,Food.getInstance().getX());
            assertNotEquals(200,Food.getInstance().getY());

            // count reduce 1
            latch.countDown();
        });

        // wait for finish JavaFX thread
        latch.await();
    }

}
