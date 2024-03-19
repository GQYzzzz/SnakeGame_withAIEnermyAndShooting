import dms.game.Brick;
import dms.game.MySnake;
import dms.game.Play;
import dms.game.ScoreReduce;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Point2D;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreReduceTest {
    /** Simulate the initialization of the JavaFX environment before the test */
    @Before
    public void beforeAll(){
        new JFXPanel();
    }

    /** Test the getInstance method of ScoreReduce */
    @Test
    public void testGetInstanceBrick1() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // test in the JavaFX thread
        Platform.runLater(() -> {
            Play.mySnake = MySnake.getInstance();
            assertNotNull(ScoreReduce.getInstance());

            // count reduce 1
            latch.countDown();
        });

        // wait for finish JavaFX thread
        latch.await();
    }

    /** Test the checkOverlap method of ScoreReduce */
    @Test
    public void testCheckOverlap() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // test in the JavaFX thread
        Platform.runLater(() -> {
            MySnake.getInstance().setBodyPoints(new Point2D(200,300));
            Play.mySnake = MySnake.getInstance();
            ScoreReduce.getInstance().setX(200);
            ScoreReduce.getInstance().setY(300);
            assertTrue(ScoreReduce.getInstance().checkOverlap());

            // count reduce 1
            latch.countDown();
        });

        // wait for finish JavaFX thread
        latch.await();
    }

    /** Test the crash method of ScoreReduce */
    @Test
    public void testCrash() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // test in the JavaFX thread
        Platform.runLater(() -> {
            MySnake.getInstance().setX(300);
            MySnake.getInstance().setY(300);
            Play.mySnake = MySnake.getInstance();
            ScoreReduce.getInstance().setX(300);
            ScoreReduce.getInstance().setY(300);
            ScoreReduce.getInstance().crash(Play.mySnake);
            assertFalse(ScoreReduce.getInstance().getL());

            // count reduce 1
            latch.countDown();
        });

        // wait for finish JavaFX thread
        latch.await();
    }

    /** Test the refreshScoreReduce method of ScoreReduce */
    @Test
    public void testRefreshScoreReduce() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // test in the JavaFX thread
        Platform.runLater(() -> {
            Brick.getInstanceBrick1().setX(700);
            Brick.getInstanceBrick1().setY(500);
            Brick.getInstanceBrick2().setX(500);
            Brick.getInstanceBrick2().setY(400);
            Play.mySnake = MySnake.getInstance();
            ScoreReduce.getInstance().setX(350);
            ScoreReduce.getInstance().setY(400);
            ScoreReduce.getInstance().refreshScoreReduce();
            assertNotEquals(350,ScoreReduce.getInstance().getX());
            assertNotEquals(400,ScoreReduce.getInstance().getY());

            // count reduce 1
            latch.countDown();
        });

        // wait for finish JavaFX thread
        latch.await();
    }
}
