package dms.game;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Handle key events for controlling the game.
 *
 * <p>This class implements the {@code EventHandler<KeyEvent>} interface to handle
 * key events, specifically for controlling the player's snake in the game.</p>
 *
 * <p>The key events trigger actions in the {@link Play} class to control the movement
 * and actions of the snake.</p>
 *
 * @see EventHandler
 * @see KeyCode
 * @see KeyEvent
 * @see Play
 * @author Qianyun Gong
 * @version 1.0
 */

public class KeyControl implements EventHandler<KeyEvent> {
    /**
     * Handles the key events and triggers corresponding actions in the game.
     *
     * <p> Invoke the corresponding key event is it is a key pressed event </p>
     *
     * @param event The KeyEvent triggered by a key press.
     */
    @Override
    public void handle(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        if(event.getEventType() == KeyEvent.KEY_PRESSED) {
            Play.mySnake.keyPressed(keyCode);
        }
    }
}
