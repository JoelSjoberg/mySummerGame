import java.util.ArrayList;

public class GameLoop
{
	public static void main(String[] args){
		GameLoop gameLoop = new GameLoop();
		gameLoop.start();
	}
	
	private View view;
	private BattleSystem game;
	
	private boolean running = false;
	ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	public GameLoop()
	{
		game = new BattleSystem();
		view = new View(500, 500);
		view.createBufferStrategy(3);
		
	}
	
	private void run ()
	{
		int ups = 0;int fps = 0;
		int updatesPerSecond = 100;
		int rendersPerSecond = 60;
		int timeBetweenUpdates = 1000000000 / updatesPerSecond;
		int timeBetweenRenders = 1000000000 / rendersPerSecond;

		long time = System.nanoTime();
		long lastUpdate = System.nanoTime();
		long lastRender = System.nanoTime();
		while(running)
		{
			// run the game
			
			
			// Update the game logic <updatesPreSecond> times
			if(System.nanoTime() >= timeBetweenUpdates + lastUpdate) {
				lastUpdate = System.nanoTime();				
				ups++;
				game.loop();
			}
			
			// render the game <rendersPreSecond> times
			if (System.nanoTime() >= timeBetweenRenders + lastRender) {
				lastRender = System.nanoTime();
				view.render(game);
				fps++;
			}
			
			// Once a second update the fps and ups counter, set them as the title
			if(System.nanoTime() >= time + 1000000000){
				view.frame.setTitle("ups: " + ups + ", fps: " + fps);
				ups = 0;
				fps = 0;
				time = System.nanoTime();
			}
		}
	}
	
	private void start ()
	{
		running = true;
		run();
	}
}
