package Game;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import Enemies.Spirit;
import GameUtility.GameCore;
import GameUtility.KeyBoard;
import Sprites.SpriteScreen;

public class BattleSystem implements GameCore{

	Player player;
	ArrayList<GameObject> Enemies;
	
	State state = State.Start;
	boolean paused = false;
	int width, height;
	long pauseStart;
	long pauseFinish;
	static long startTime;
	
	public BattleSystem(int width, int height) {
		
		// for resize method
		this.width = width;
		this.height = height;
		
		Enemies = new ArrayList<GameObject>();
		Enemies.add(new Spirit(170, 220));
		Enemies.add(new Spirit(135, 390));
		player = new Player(width - 200, height - 200);
		player.target = Enemies.get(0);
		
		startTime = System.currentTimeMillis();
	}
	
//-------------------------- Input and pause--------------------------//
	boolean w, s , a, d, up, down, left, right, pauseKey;
	boolean goUp = false;
	boolean goDown = false;
	@Override
	public void takeInput() {
		// all the different input values
		a = KeyBoard.key[65]; w = KeyBoard.key[87]; d = KeyBoard.key[68]; s = KeyBoard.key[83];
		up = KeyBoard.key[38]; down = KeyBoard.key[40]; left = KeyBoard.key[37]; right = KeyBoard.key[39];
		
		// Take input normally when not paused
		if (!paused){
			if (a) player.act(0);
			if (w) player.act(1);
			if (d) player.act(2);
			if (s) player.act(3);
			
			// Lock on to a specific enemy
			// iterate trough the enemies when the button has been pressed and don't change before it is pressed again!

			//iterate up
			int targetIndex = Enemies.indexOf(player.target);
			if ((up || left) && !goUp){
				// Modulo from negative numbers give negative output, so do not use modulo here
				if (--targetIndex < 0) targetIndex = Enemies.size() - 1;
				player.target = Enemies.get(targetIndex);	
				goUp = true;
			}
			
			// iterate down
			if ((down || right) && !goDown){
				player.target = Enemies.get((targetIndex + 1) % Enemies.size());				
				goDown = true;
			}

			// Toggle the boolean(s) to stop the lock on box
			if ((!up && !left) && goUp){
				goUp = false;
			}
			if ((!down && !right) && goDown){
				goDown = false;
			}			
		}
	}

//-------------------------- Draw every object and the game world --------------------------//
	int energyBarLen = 100;
	int energyLen = 0;
	int hpBarLen = 100;
	int hpLen = 0;
	SpriteScreen animatedBackground = new SpriteScreen("StartAnimation.png");
	@Override
	public void draw(Graphics2D g) {

		// Draw the objects
		g.setFont(g.getFont().deriveFont(18));
		player.Draw(g);
		for(int i = 0; i < Enemies.size(); i++){
			Enemies.get(i).Draw(g);
		}
		
		hpBarLen = 100;
		energyBarLen = 100;
		
		// Draw player hp and energy
		energyLen = (int) (player.energy * energyBarLen/player.maxEnergy);
		g.setColor(Color.cyan);
		g.fillRect(width - energyBarLen - 20,  height - 20, energyLen, 7);
		g.setColor(Color.black);
		g.drawRect(width - energyBarLen - 20, height - 20, energyBarLen, 7);
		
		hpLen = player.hp * hpBarLen / player.maxHp;
		g.setColor(Color.green);
		g.fillRect(width - hpBarLen - 30, height - 30, hpLen, 10);
		g.setColor(Color.black);
		g.drawRect(width - hpBarLen - 30, height - 30, hpBarLen, 10);
		
		
		hpBarLen = 75;
		energyBarLen = 75;
		
		
		// Draw target hp, energy and name
		g.drawString(player.target.getName(), 10, 15);
		energyLen = (int) (player.target.getEnergy() * energyBarLen/player.target.getMaxEnergy());
		g.setColor(Color.cyan);
		g.fillRect(20, 27, energyLen, 5);
		g.setColor(Color.black);
		g.drawRect(20, 27, energyBarLen, 5);
		
		hpLen = player.target.getHp() * hpBarLen / player.target.getMaxHp();
		g.setColor(Color.red);
		g.fillRect(10, 20, hpLen, 5);
		g.setColor(Color.black);
		g.drawRect(10, 20, hpBarLen, 5);
		
		
		//draw states
		switch(state){
		case Start:
			//animatedBackground.draw(g, 0, 0, width, height);
			animatedBackground.idleAnimation();
			if(startTime + 1600 < System.currentTimeMillis()) state = State.Midbattle;
			player.startAnimation();
			for(int i = 0; i < Enemies.size(); i++){
				Enemies.get(i).startAnimation();
			}
			break;
		
		case PlayerVictory:
			break;
		case PlayerDeath:
			break;
		default:
			break;
		}
			
	}
//-------------------------- Main loop --------------------------//
	@Override
	public void loop() {
		
		switch(state){
		
		case Start:
			
			break;
		
		case Midbattle:
			takeInput();
			if(!paused){
				player.doAction();
				player.increaseEnergy();
				for(int i = 0; i < Enemies.size(); i++){
					Enemies.get(i).doAction();
				}
				if (player.hp <= 0) state = State.PlayerDeath; 
			}
			break;
			
		case PlayerDeath:
			break;
		case PlayerVictory:
			break;
		}
	}
//-------------------------- State enums for transition --------------------------//
	public enum State{
		Start,
		Midbattle,
		PlayerDeath,
		PlayerVictory;
	}
}