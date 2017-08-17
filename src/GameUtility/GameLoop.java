package GameUtility;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
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
	ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	int height = 500; int width = height * 16/9;
	public GameLoop()
	{
		game = new BattleSystem(width, height);
		view = new View(width, height);
		view.createBufferStrategy(3);
		// if the frame is resized, change the height and width variables
		view.frame.addComponentListener(new ComponentListener (){
			@Override
			public void componentResized(ComponentEvent arg0) {
				// resize the gameobjects to the current resoulution 
				width = view.getWidth();
				height = view.getHeight();
				game.setSize(width, height);
				game.resizeAll();
				game.repositionAll();
			}

			@Override
			public void componentHidden(ComponentEvent arg0) {
				// not in use
			}		
			@Override
			public void componentMoved(ComponentEvent arg0) {
				// not in use
			}
			@Override
			public void componentShown(ComponentEvent arg0) {
				// not in use
			}
		});	
	}
	
	private void run ()
	{
		long lastRender = System.nanoTime();
		long lastUpdate = System.nanoTime();
		int ups = 0; int fps = 0;
		int updatesPerSecond = 100;
		int rendersPerSecond = 144;
		int timeBetweenUpdates = 1000000000 / updatesPerSecond;
		int timeBetweenRenders = 1000000000 / rendersPerSecond;

		long time = System.nanoTime();
		view.initView();
		while(running)
		{
			// run the game
			
			// Update the game logic <updatesPerSecond> times
			if(System.nanoTime() >= timeBetweenUpdates + lastUpdate) {
				lastUpdate = System.nanoTime();				
				ups++;
				game.loop();
			}
			
			
			// render the game <rendersPerSecond> times
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
