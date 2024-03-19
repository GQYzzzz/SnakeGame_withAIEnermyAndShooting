import dms.game.*;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Point2D;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.CountDownLatch;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for Bullet class.
 *
 * @author Qianyun Gong
 * @version 1.0
 */

public class BulletTest {
    /** Simulate the initialization of the JavaFX environment before the test */
    @Before
    public void beforeAll(){
        new JFXPanel();
    }

    /** Test the getInstance method of Bullet */
    @Test
    public void testGetInstance(){
        assertNotNull(Bullet.getInstance());
    }

    /** Test the getInstanceBrick1 method of Brick */
    @Test
    public void testSetPos() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // test in the JavaFX thread
        Platform.runLater(() -> {
            MySnake.getInstance().setHeadPoint(new Point2D(100,100));
            Play.mySnake = MySnake.getInstance();
            Bullet.getInstance().setPos();
            assertEquals(100,Bullet.getInstance().getX());
            assertEquals(100,Bullet.getInstance().getY());

            // count reduce 1
            latch.countDown();
        });

        // wait for finish JavaFX thread
        latch.await();
    }

    /** Test the move method of Bullet and check whether it could shoot the enemy */
    @Test
    public void testMove() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // test in the JavaFX thread
        Platform.runLater(() -> {
            MySnake.getInstance().setBodyPoints(new Point2D(200,200));
            Play.mySnake = MySnake.getInstance();
            Bullet.getInstance().setDirection(2);
            Bullet.getInstance().setX(200);
            Bullet.getInstance().setY(200);
            Enemy.getInstance().setX(200);
            Enemy.getInstance().setY(210);
            Bullet.getInstance().move();
            assertEquals(200,Bullet.getInstance().getX());
            assertEquals(210,Bullet.getInstance().getY());
            assertFalse(Enemy.getInstance().getL());

            // count reduce 1
            latch.countDown();
        });

        // wait for finish JavaFX thread
        latch.await();
    }

    /** Test the setDirection method of Bullet */
    @Test
    public void testSetDirection(){
        Bullet.getInstance().setDirection(1);
        assertEquals(1,Bullet.getInstance().getDirection());
    }
}
