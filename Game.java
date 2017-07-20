import java.util.ArrayList;

public class Game
{
	public static void main(String[] args){
		Game game = new Game();
	}
	
	private View view;
	private Logic logic;
	private boolean running = false;
	private boolean paused = false;
	ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	public Game()
	{
		logic = new Logic(500, 500);
		view = new View(500, 500);
		logic.add(view);
		view.addKeyListener(logic);
		view.createBufferStrategy(3);
		running = true;
		start();
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
			}
			logic.update(paused);					
			ups++;
			
			// render the game <rendersPreSecond> times
			if (System.nanoTime() >= timeBetweenRenders + lastRender) {
				lastRender = System.nanoTime();
				view.render(paused);
				fps++;
			}
			
			// Once a second update the fps and ups counter, set them as the title
			if(System.nanoTime() >= time + 1000000000){
				logic.setTitle("ups: " + ups + ", fps: " + fps);
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
	
	private void stop (){
		running = false;
		System.exit(0);
	}
}
