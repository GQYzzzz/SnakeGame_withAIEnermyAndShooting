import dms.game.MySnake;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for MySnake class.
 *
 * @author Qianyun Gong
 * @version 1.0
 */

public class MySnakeTest {
    /** Simulate the initialization of the JavaFX environment before the test */
    @Before
    public void beforeAll(){
        new JFXPanel();
    }

    /** Test the getInstance method of MySnake */
    @Test
    public void testGetInstance() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // test in the JavaFX thread
        Platform.runLater(() -> {
            assertNotNull(MySnake.getInstance());

            // count reduce 1
            latch.countDown();
        });

        // wait for finish JavaFX thread
        latch.await();
    }

    /** Test the refreshSnake method of MySnake */
    @Test
    public void testRefreshSnake() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // test in the JavaFX thread
        Platform.runLater(() -> {
            MySnake.getInstance().setX(100);
            MySnake.getInstance().setY(100);
            MySnake.getInstance().refreshSnake(200,300);
            assertEquals(200,MySnake.getInstance().getX());
            assertEquals(300,MySnake.getInstance().getY());

            // count reduce 1
            latch.countDown();
        });

        // wait for finish JavaFX thread
        latch.await();
    }

    /** Test the keyPressed method of MySnake */
    @Test
    public void testKeyPressed() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // test in the JavaFX thread
        Platform.runLater(() -> {
            MySnake.getInstance().keyPressed(KeyCode.UP);
            assertTrue(MySnake.getInstance().isUp());

            // count reduce 1
            latch.countDown();
        });

        // wait for finish JavaFX thread
        latch.await();
    }

    /** Test the move method of MySnake */
    @Test
    public void testMove() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // test in the JavaFX thread
        Platform.runLater(() -> {
            MySnake.getInstance().setX(100);
            MySnake.getInstance().setY(100);
            MySnake.getInstance().move();
            assertEquals(105,MySnake.getInstance().getX());
            assertEquals(100,MySnake.getInstance().getY());

            // count reduce 1
            latch.countDown();
        });

        // wait for finish JavaFX thread
        latch.await();
    }

    /** Test the eatBody method of MySnake */
    @Test
    public void testEatBody() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // test in the JavaFX thread
        Platform.runLater(() -> {
            MySnake.getInstance().getBodyPoints().add(new Point2D(200,200));
            MySnake.getInstance().getBodyPoints().add(new Point2D(200,200));
            MySnake.getInstance().eatBody();
            assertFalse(MySnake.getInstance().getL());

            // count reduce 1
            latch.countDown();
        });

        // wait for finish JavaFX thread
        latch.await();
    }

    /** Test the outOfBounds method of MySnake */
    @Test
    public void testOutOfBounds() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // test in the JavaFX thread
        Platform.runLater(() -> {
            MySnake.getInstance().setX(950);
            MySnake.getInstance().setY(100);
            MySnake.getInstance().outofBounds();
            assertFalse(MySnake.getInstance().getL());

            // count reduce 1
            latch.countDown();
        });

        // wait for finish JavaFX thread
        latch.await();
    }
}
