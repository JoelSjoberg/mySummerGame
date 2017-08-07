import java.awt.Color;
import java.awt.Graphics2D;

public class Player implements GameObject{

	Vector location, velocity, acceleration;

	// for resizing and position controll
	final int originWidth, originHeight;
	final int originX, originY;
	int width, height;
	double anchorX, anchorY;
	
	// Gameplay variables
	int originalMaxSpeed = 8;
	int maxSpeed = originalMaxSpeed;
	int maxEnergy = 300;
	int maxHp = 300;
	int experience = 0;
	int currentAction;
	double energyChangeRate = 0.4;
	long staggerStart, staggerTime = 1500000000;
	Color color = new Color(22, 22, 22);
	Sprite sprite = new Sprite("Spirit.png");
	
	
	//static state variables to be used by ai, abilities and system
	int hp = 300;
	static int strength, defence, accuracy;
	static double energy = 150;
	static boolean idle;

	boolean staggered;
	AIgent target;
	static Ability[] abilities;
	
	
	public Player(int x, int y){
		originX = x; originY = y;
		anchorX = x; anchorY = y;
		
		this.location = new Vector(x, y);
		this.acceleration = new Vector(0, 0);
		this.velocity = new Vector(0, 0);
		
		width = 50; height = 50;
		originWidth = width; originHeight = height;
		idle = true; staggered = true;
		abilities = new Ability[4];
		abilities[0] = new Attack();
		abilities[1] = new Spark();
		abilities[2] = new Defend();
		abilities[3] = new Heal();
		target = null;
	}

	// ------------------------------Main Draw method------------------------------//
	public void Draw(Graphics2D g)
	{	
		g.setColor(this.color);
		//g.fillOval((int)location.x - width / 2, (int)location.y - height / 2, this.width, this.height);
		if(target != null){
			// draw an indicator on which enemy is targeted
			g.drawRect((int)target.location.x - target.width/2 - 5, (int)target.location.y - target.height/2 - 5, target.width + 10, target.height + 10);			
		}
		// draw the energy gauge
		g.setColor(Color.cyan);
		g.fillRect((int)location.x + width / 2, (int)location.y + height / 2, 10, (int)energy);
		g.setColor(Color.black);
		g.drawRect((int)location.x + width / 2, (int)location.y + height / 2, 10, 100);
		sprite.draw(g, (int)location.x - width / 2, (int)location.y - height / 2, this.width, this.height);
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
				if(collide(new AIgent((int)anchorX, (int)anchorY))){
					goingBack = false;
					acceleration.mult(0);
					velocity.mult(0);
				}
			}
			else
			{
				// if the player is idle with no input, animate it
				doIdleAnimation();				
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
			for(int i = 0; i < abilities.length; i++){
				abilities[i].interrupt();
			}
			beStaggered();
		}
	}

// ------------------------------ Private behavior methods ------------------------------//
	private void doIdleAnimation()
	{
		//this.location.y += Math.sin(System.nanoTime() / 100000000);
		sprite.idleAnimation();
	}
	private void beStaggered()
	{
		if(System.nanoTime() <= staggerStart + staggerTime)
		{
			this.location.x += Math.cos(System.nanoTime() / 1000) * 0.54;
		}else
		{
			staggered = false;
		}
	}
	private void ceaseActivity() {
		for(int i = 0; i < abilities.length; i++){
			abilities[i].interrupt();
		}
	}
	
//------------------------------ Callable behavior methods ------------------------------//	
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
	public boolean collide(AIgent target) {
		return Math.sqrt(Math.pow(this.location.x - target.location.x, 2) + Math.pow(this.location.y - target.location.y, 2)) <= this.width;
	}
	public void increaseEnergy() {
		if(energy < maxEnergy){
			energy += energyChangeRate;
		}
	}
	boolean goingBack = false;
	public void goBack()
	{
		// Return player to the original position
		goingBack = true;
		acceleration = new Vector(anchorX - location.x, anchorY - location.y);
		acceleration.normalize();
		sprite.backingOff();
	}
	
// ------------------------Methods for resizing and repositioning relative to the screens size--------------------------//
	public void resize(int currWidth, int currHeight, int originW, int originH){
		// resize object with game resolution
		this.width = (originWidth * currWidth) / originW;
		this.height = (originHeight * currHeight) / originH;
	}	
	public void reposition(int currWidth, int currHeight, int originW, int originH){
		// reposition game object to maintain distance from the middle of the screen
		
		this.location.x = originX * currWidth / originW;
		this.location.y = originY * currHeight / originH;
		anchorX = this.location.x;
		anchorY = this.location.y;
	}
}