import java.awt.Color;
import java.awt.Graphics2D;

public class Player implements GameObject{

	Vector location, velocity, acceleration;
	int width, height;
	int maxSpeed = 2;
	double acc = 0.1;
	Color color = new Color(222, 22, 22);
	public Player(int x, int y){
		this.location = new Vector(x, y);
		this.acceleration = new Vector(0, 0);
		this.velocity = new Vector(0, 0);
		width = 10; height = 10;
	}
	
	public void Draw(Graphics2D g) {
		g.setColor(this.color);
		g.fillRect((int)location.x, (int)location.y, this.width, this.height);
	}
	
	@Override
	public void doAction() {
		
		this.velocity.limit(maxSpeed);
		this.velocity.addVector(this.acceleration);
		this.location.addVector(this.velocity);	
		
	}

}
