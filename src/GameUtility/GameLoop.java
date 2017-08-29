package GameUtility;
import java.util.ArrayList;
import Game.BattleSystem;
import Game.GameObject;

public class GameLoop
{
	public static void main(String[] args){
		GameLoop gameLoop = new GameLoop();
		gameLoop.start();
	}
	
	private View view;
	private GameCore game;
	private boolean running = false;
	private boolean paused = false;
	ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	int height = 500; int width = height * 16/9;
	public GameLoop()
	{
		game = new BattleSystem(width, height);
		view = new View(width, height);
	}
	
	private void run ()
	{
		long second = System.nanoTime();
		long lastRender = System.nanoTime();
		long lastUpdate = System.nanoTime();
		int ups = 0; int fps = 0;
		int updatesPerSecond = 100;
		int rendersPerSecond = 60;
		int timeBetweenUpdates = 1000000000 / updatesPerSecond;
		int timeBetweenRenders = 1000000000 / rendersPerSecond;

		long currentTime;
		view.initView();
		// run the game
		while(running)
		{
			currentTime = System.nanoTime();
			// Pause if the player presses 'p'
			pause(KeyBoard.key[80]);
			
			// Update the game logic <updatesPerSecond> times
			if(currentTime >= timeBetweenUpdates + lastUpdate && !paused) {
				lastUpdate = currentTime;				
				ups++;
				game.loop();
			}
			
			
			// render the game <rendersPerSecond> times
			if (currentTime >= timeBetweenRenders + lastRender) {
				lastRender = currentTime;
				view.render(game, paused);
				fps++;
			}
			
			// Once a second update the fps and ups counter, set them as the title
			if(currentTime >= second + 1000000000){
				view.frame.setTitle("ups: " + ups + ", fps: " + fps);
				ups = 0;
				fps = 0;
				second = currentTime;
			}
		}
	}
	
	private void start ()
	{
		running = true;
		run();
	}
	boolean pauseKeyDown = false;
	private void pause(boolean keyIsPressed) {
		if(keyIsPressed && !pauseKeyDown)
		{
			this.paused = !paused;
			pauseKeyDown = true;
		}
		if(!keyIsPressed){
			pauseKeyDown = false;
		}
	}
	
}
