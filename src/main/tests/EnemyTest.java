import dms.game.*;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Point2D;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.CountDownLatch;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for Enemy class.
 *
 * @author Qianyun Gong
 * @version 1.0
 */

public class EnemyTest {
    /** Simulate the initialization of the JavaFX environment before the test */
    @Before
    public void beforeAll(){
        new JFXPanel();
    }

    /** Test the getInstance method of Enemy */
    @Test
    public void testGetInstance() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // test in the JavaFX thread
        Platform.runLater(() -> {
            Play.mySnake = MySnake.getInstance();
            assertNotNull(Enemy.getInstance());

            // count reduce 1
            latch.countDown();
        });

        // wait for finish JavaFX thread
        latch.await();
    }

    /** Check whether the checkOverlap method will return true if overlaps */
    @Test
    public void testCheckOverlap() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // test in the JavaFX thread
        Platform.runLater(() -> {
            MySnake.getInstance().setBodyPoints(new Point2D(200,200));
            Play.mySnake = MySnake.getInstance();
            Enemy.getInstance().setX(200);
            Enemy.getInstance().setY(200);
            assertTrue(Enemy.getInstance().checkOverlap());

            // count reduce 1
            latch.countDown();
        });

        // wait for finish JavaFX thread
        latch.await();
    }

    /** Test the move method of Enemy by check the next movement */
    @Test
    public void testMove() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // test in the JavaFX thread
        Platform.runLater(() -> {
            Brick.getInstanceBrick1().setX(700);
            Brick.getInstanceBrick1().setY(100);
            Brick.getInstanceBrick2().setX(400);
            Brick.getInstanceBrick2().setY(200);
            Play.mySnake = MySnake.getInstance();
            Food.getInstance().setX(600);
            Food.getInstance().setY(300);
            Play.food = Food.getInstance();
            Enemy.getInstance().setX(800);
            Enemy.getInstance().setY(500);
            Enemy.getInstance().move();
            // check whether the enemy will move to left first to get closer to the food
            assertEquals(797,Enemy.getInstance().getX());
            assertEquals(500,Enemy.getInstance().getY());

            // count reduce 1
            latch.countDown();
        });

        // wait for finish JavaFX thread
        latch.await();
    }

    /** Check whether the checkOverlap method will return true if overlaps */
    @Test
    public void testCrash() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // test in the JavaFX thread
        Platform.runLater(() -> {
            MySnake.getInstance().setX(200);
            MySnake.getInstance().setY(200);
            MySnake.getInstance().setBodyPoints(new Point2D(200,200));
            Play.mySnake = MySnake.getInstance();

            Enemy.getInstance().setX(200);
            Enemy.getInstance().setY(200);

            Enemy.getInstance().crash(Play.mySnake);
            assertFalse(Play.mySnake.getL());

            // count reduce 1
            latch.countDown();
        });

        // wait for finish JavaFX thread
        latch.await();
    }

}
