import dms.game.MusicPlayer;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * JUnit test for MusicPlayer class.
 *
 * @author Qianyun Gong
 * @version 1.0
 */

public class MusicPlayerTest {
    /** Test the getMusicPlayOnce method */
    @Test
    public void testGetMusicPlayOnce(){
        MusicPlayer.getMusicPlayOnce("src/main/resources/dms/game/music/key-press.mp3");
        assertNotNull(MusicPlayer.getMusicPlayer());
    }

    /** Test the getMusicPlayLoop method */
    @Test
    public void testGetMusicPlayLoop(){
        MusicPlayer.getMusicPlayLoop("src/main/resources/dms/game/music/key-press.mp3");
        assertNotNull(MusicPlayer.getMusicPlayer());
    }
}
