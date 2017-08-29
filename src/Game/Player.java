package Game;
import java.awt.Color;
import java.awt.Graphics2D;
import Abilities.Ability;
import Abilities.Attack;
import Abilities.Defend;
import Abilities.Heal;
import Abilities.Spark;
import GameUtility.Vector;
import Sprites.PlayerSprite;

public class Player implements GameObject{

	 public Vector location, velocity, acceleration;
	 String name = "Jack";

	// for resizing and position controll
	int width, height;
	double anchorX, anchorY;
	
// ------------------------------Status variables------------------------------//
	
	
	boolean staggered;
	long staggerStart, staggerTime = 1500000000;

	int originalMaxSpeed = 10;
	public int maxSpeed = originalMaxSpeed;
	

	public int strength = 2, defence = 1, agility = 2;
	
	int maxHp = 100;
	int hp = maxHp;
	int maxEnergy = 300;
	double energy = maxEnergy / 3;
	double rate = 0.75;
	double energyChangeRate = rate * agility;	

	int currentAction;
	static boolean idle;

	public GameObject target;
	static Ability[] abilities;
	
	int experience = 0;
	
	public Player(int x, int y){
		anchorX = x; anchorY = y;
		
		this.location = new Vector(x, y);
		this.acceleration = new Vector(0, 0);
		this.velocity = new Vector(0, 0);
		
		width = 100; height = 100;
		target = null;
		idle = true; staggered = false;
		abilities = new Ability[4];
		abilities[0] = new Attack();
		abilities[1] = new Spark();
		abilities[2] = new Defend();
		abilities[3] = new Heal();
	}

// ------------------------------Main Draw method------------------------------//
	public PlayerSprite sprite = new PlayerSprite("PlayerSprite.png");
	public void Draw(Graphics2D g)
	{	
		//Draw the player
		sprite.draw(g, (int)location.getX() - width / 2, (int)location.getY() - height / 2, this.width, this.height);
		
		if(target != null){
			// draw an indicator on the targeted enemy
			g.setColor(Color.black);
			g.drawRect((int)target.getX() - target.getWidth()/2 - 5, (int)target.getY() - target.getHeight()/2 - 5, target.getWidth() + 10, target.getHeight() + 10);			
		}
	}

// ------------------------------ Main Behavior method ------------------------------//
	public void doAction()
	{
		// always move if told
		this.velocity.limit(maxSpeed);
		this.velocity.addVector(this.acceleration);
		this.location.addVector(this.velocity);
		this.maxSpeed = originalMaxSpeed;
		// In case an ability changes maxSpeed, redefine it
		
		if (!staggered && idle)
		{
			if(goingBack){
				// Go back to initial position if the action required movement
				if(collide(new Player((int)anchorX, (int)anchorY))){
					goingBack = false;
					acceleration.mult(0);
					velocity.mult(0);
				}else{
					goBack();
				}
			}
			else
			{
				// if the player is idle with no input, animate it
				doIdleAnimation();
				//startAnimation();
			}
		}
		else if (!staggered)
		{
			// if action is given, do action until finished; then idle
			idle = abilities[currentAction].execute(this);
		}
		else
		{
			// if staggered, cease all actions and stagger
			ceaseActivity();
			beStaggered();
		}
	}

	
	
// ------------------------------ Private behavior methods ------------------------------//
	private void doIdleAnimation(){
		sprite.idleAnimation();
	}
	
	
	private void ceaseActivity() {
		for(int i = 0; i < abilities.length; i++){
			abilities[i].interrupt();
		}
	}
	
//------------------------------ Callable behavior methods ------------------------------//	
	@Override
	public void startAnimation() {
		this.sprite.startAnimation();
	}
	
	public void act(int index)
	{
		if(!staggered && energy >= abilities[index].getCost() && !abilities[index].getExecuting()){
			ceaseActivity();
			idle = false;
			energy -= abilities[index].getCost();
			currentAction = index;
			abilities[index].begin();
			acceleration.mult(0);
			velocity.mult(0);
		}
	}
	
	public void stagger()
	{
		staggerStart = System.nanoTime();
		this.staggered = true;
	}	
	
	public void beStaggered()
	{
		if(System.nanoTime() <= staggerStart + staggerTime)
		{
			this.sprite.stagger();
			this.location.setX(this.location.getX() + Math.cos(System.nanoTime() / 1000) * 0.54);
		}else
		{
			staggered = false;
		}
	}
	
	public boolean collide(GameObject target) {
		return Math.sqrt(Math.pow(this.location.getX() - target.getX(), 2) + Math.pow(this.location.getY() - target.getY(), 2)) <= this.width;
	}
	
	public void increaseEnergy() {
		if(energy < maxEnergy){
			energy += energyChangeRate;
		}
	}
	
	@Override
	public void sufferDamage(int damage){
		this.hp -= damage;
	}
	
	boolean goingBack = false;
	public void goBack()
	{
		// Return player to the original position
		goingBack = true;
		acceleration = new Vector(anchorX - location.getX(), anchorY - location.getY());
		maxSpeed = 3;
		acceleration.normalize();
		sprite.backingOff();
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