import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class BattleSystem implements GameCore{

	
	Player player;
	AIgent enemy1;
	AIgent enemy2;
	ArrayList<AIgent> Enemies;
	
	State state = State.Start;
	boolean paused = false;
	int width, height;
	int originHeight, originWidth;
	long pauseStart;
	long pauseFinish;
	public BattleSystem(int width, int height) {
		
		// for resize method
		this.width = width;
		this.height = height;
		this.originWidth = width;
		this.originHeight = height;
		
		Enemies = new ArrayList<AIgent>();
		Enemies.add(new AIgent(170, 220));
		Enemies.add(new AIgent(135, 390));
		player = new Player(width - 200, height - 200);
		player.target = Enemies.get(0);
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
		pauseKey = KeyBoard.key[80];
		
		// pause if the key is pressed and save the amount of time paused!
		pause(pauseKey);
		
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
	
	boolean pauseKeyDown = false;
	@Override
	public void pause(boolean keyIsPressed) {
		if(keyIsPressed && !pauseKeyDown)
		{
			this.paused = !paused;
			pauseKeyDown = true;
			if(paused) pauseStart = System.nanoTime();
			else pauseFinish = System.nanoTime() - pauseStart;
		}
		if(!keyIsPressed){
			pauseKeyDown = false;
		}
	}

//-------------------------- Utility methods --------------------------//
	@Override
	public void setSize(int width, int height){
		this.width = width;
		this.height = height;
	}
	public void resizeAll() {
		for(int i = 0; i < Enemies.size(); i++){
			Enemies.get(i).resize(width, height, originWidth, originHeight);
		}
		player.resize(width, height, originWidth, originHeight);
	}
	public void repositionAll() {
		for(int i = 0; i < Enemies.size(); i++){
			Enemies.get(i).reposition(width, height, originWidth, originHeight);
		}
		player.reposition(width, height, originWidth, originHeight);
	}
	

//-------------------------- Draw every object and the game world --------------------------//
	@Override
	public void draw(Graphics2D g) {
		g.setBackground(Color.BLACK);
		g.setFont(new Font("helvetica", Font.BOLD, 38));
		if(paused){
			
			g.setColor(Color.orange);
			g.drawString("Pause", width - 150, 40);	
		}
		for(int i = 0; i < Enemies.size(); i++){
			Enemies.get(i).Draw(g);
		}
		player.Draw(g);
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