package dms.game;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

/**
 * Utilize the music playing in the game.
 *
 * <p>This class provides methods to play music either once or in a loop.</p>
 *
 * @author Qianyun Gong - modified
 * @version 1.0
 */

public class MusicPlayer extends Thread
{
	private final String filename;
	public static Player player;
	public static Player playerOnce;
	private static int isEnd = 0;
	private static MusicPlayer musicPlayer;

	/**
	 * Initialize the music player with the specified filename
	 *
	 * @param filename The path to the music file.
	 */
	public MusicPlayer(String filename)
	{
		this.filename = filename;
	}

	/**
	 * Play the music once in a separate thread.
	 */
	public void playOnce()
	{
		new Thread()
		{
			@Override
			public void run()
			{
				super.run();
				try {
					playerOnce = new Player(new BufferedInputStream(new FileInputStream(filename)));
					playerOnce.play();
				} catch (Exception e) {
					System.out.println("Could not play the music once!");
				}

			}
		}.start();
	}

	/**
	 * Play the music in a loop until stopped.
	 */
	public void playLoop()
	{
		new Thread()
		{
			@Override
			public void run()
			{
				super.run();
				isEnd = 0;
				while(isEnd == 0) {
					try {
						player = new Player(new BufferedInputStream(new FileInputStream(filename)));
						player.play();
					} catch (Exception e) {
						System.out.println("Could not play the music in a loop!");
					}
				}
			}
		}.start();
	}

	/**
	 * Stop playing the music in a loop.
	 */
	public static void stopPlayingLoop() {
		isEnd = 1;
		if (player != null) {
			player.close();
		}
	}

	/**
	 * Stop playing the music played once.
	 */
	public static void stopPlayingOnce() {
		isEnd = 1;
		if (playerOnce != null) {
			playerOnce.close();
		}
	}

	/**
	 * Play the music of the specified filename once.
	 *
	 * @param filename The path to the music file.
	 */
	public static void getMusicPlayOnce(String filename)
	{
		musicPlayer = new MusicPlayer(filename);
		musicPlayer.playOnce();
	}

	/**
	 * Play the music of the specified filename in a loop.
	 *
	 * @param filename The path to the music file.
	 */
	public static void getMusicPlayLoop(String filename)
	{
		musicPlayer = new MusicPlayer(filename);
		musicPlayer.playLoop();
	}

	/**
	 * Get the current musicPlayer.
	 *
	 * @return The musicPlayer
	 */
	public static MusicPlayer getMusicPlayer(){
		return musicPlayer;
	}
}
