import dms.game.ImageUtil;
import javafx.embed.swing.JFXPanel;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * JUnit test for ImageUtil class.
 *
 * @author Qianyun Gong
 * @version 1.0
 */

public class ImageUtilTest {
    @Test
    public void testGetImage(){
        new JFXPanel();
        assertNotNull(ImageUtil.images.get("brick"));
    }
}
