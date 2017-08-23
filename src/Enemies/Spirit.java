package Enemies;
import java.awt.Graphics2D;
import Game.GameObject;
import GameUtility.Vector;
import Sprites.EnemySprite;

public class Spirit implements GameObject{

	Vector location, velocity, acceleration;
	String name = "Spirit";
	
	// for resizing and position controll
	final int originWidth, originHeight;
	final int originX, originY;
	int width, height;
	double anchorX, anchorY;

// ------------------------------Status variables------------------------------//
	
	boolean staggered = false;
	long staggerStart, staggerTime = 500000000;
	
	int originalMaxSpeed = 8;
	public int maxSpeed = originalMaxSpeed;
	
	int maxHp = 125;
	int hp = maxHp;
	int maxEnergy = 60;
	double energy = maxEnergy / 3;
	double energyChangeRate = 0.074;	

	int strength = 3, defence = 1, agility = 1;

	
	public Spirit(int x, int y) {
		
		originX = x; originY = y;
		anchorX = x; anchorY = y;
		
		this.location = new Vector(x, y);
		this.acceleration = new Vector(0, 0);
		this.velocity = new Vector(0, 0);
		
		width = 50; height = 50;
		originWidth = width; originHeight = height;
	}
	
	
	EnemySprite sprite = new EnemySprite("Spirit2.png");
	@Override
	public void Draw(Graphics2D g) {
		//Draw the player
			sprite.draw(g, (int)location.getX() - width / 2, (int)location.getY() - height / 2, this.width, this.height);
		
	}

	@Override
	public void doAction() {
		if(!staggered){
			sprite.idleAnimation();			
		}
		else{
			beStaggered();
		}
		
	}
	
	@Override
	public void stagger() {
		this.staggerStart = System.nanoTime();
		this.staggered = true;
	}
	
	public void beStaggered(){
		if(System.nanoTime() <= staggerStart + staggerTime)
		{
			sprite.stagger();
			this.location.setX(this.location.getX() + Math.cos(System.nanoTime() / 1000) * 0.54);
		}else
		{
			staggered = false;
		}
	}
	public boolean collide(GameObject target) {
		return Math.sqrt(Math.pow(this.location.getX() - target.getX(), 2) + Math.pow(this.location.getY() - target.getY(), 2)) <= this.width;
	}
	
	@Override
	public void startAnimation() {
		sprite.startAnimation();
	}

	public void sufferDamage(int damage){
		this.hp -= damage;
		System.out.println(damage);
	}
	
// ------------------------Methods for resizing and repositioning relative to the screens size--------------------------//
	public void resize(int currWidth, int currHeight, int originW, int originH){
		// resize object with game resolution
		this.width = (originWidth * currWidth) / originW;
		this.height = (originHeight * currHeight) / originH;
	}	
	public void reposition(int currWidth, int currHeight, int originW, int originH){
		// reposition game object to maintain distance from the middle of the screen
		this.location.setX(originX * currWidth / originW);
		this.location.setY(originY * currHeight / originH);
		anchorX = this.location.getX();
		anchorY = this.location.getY();
	}
	
	// ------------------------Getters--------------------------//
	
	@Override
	public int getHp() {
		return this.hp;
	}
	
	@Override
	public double getEnergy() {
		return this.energy;
	}
	
	@Override
	public double getX() {
		return this.location.getX();
	}

	@Override
	public double getY() {
		return this.location.getY();
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	@Override
	public int getMaxHp() {
		return this.maxHp;
	}

	@Override
	public int getMaxEnergy() {
		return this.maxEnergy;
	}

	@Override
	public int getStrength() {
		return this.strength;
	}

	@Override
	public int getDefence() {
		return this.defence;
	}

	@Override
	public int getAgility() {
		return this.agility;
	}

	@Override
	public String getName() {
		return this.name;
	}
}
