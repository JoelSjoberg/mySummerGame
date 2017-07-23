import java.awt.Graphics2D;
import java.util.ArrayList;

public class BattleSystem implements GameCore{

	
	Player player;
	ArrayList<GameObject> gameObjects;
	boolean paused = false;
	
	public BattleSystem() {
		
		gameObjects = new ArrayList<GameObject>();
		player = new Player(0, 0);
		gameObjects.add(player);
		player.maxSpeed = 4;
		player.acc = 0.5;
	}
	
	boolean up, down , left, right;
	@Override
	public void takeInput() {
		left = KeyBoard.key[37]; up = KeyBoard.key[38]; right = KeyBoard.key[39]; down = KeyBoard.key[40];
		if(left) player.acceleration.x = -player.acc;
		if(up) player.acceleration.y = -player.acc;
		if(right) player.acceleration.x = player.acc;
		if(down) player.acceleration.y = player.acc;

		if(!left && !right && !up && !down){
			player.velocity.x = 0;
			player.velocity.y = 0;
			player.acceleration.x = 0;
			player.acceleration.y = 0;
		}
	}

	@Override
	public void loop() {
		takeInput();
		player.doAction();
	}

	@Override
	public void pause() {
		this.paused = !paused;
		
	}

	@Override
	public void draw(Graphics2D g) {
		for(int i = 0; i < gameObjects.size(); i++){
			gameObjects.get(i).Draw(g);
		}
		
	}

}