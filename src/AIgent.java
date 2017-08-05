import java.awt.Color;
import java.awt.Graphics2D;

public class AIgent implements GameObject{

	Vector location, velocity, acceleration;
	Color color;
	
	// for resizing and position controll
	final int originWidth, originHeight;
	final int originX, originY;
	int width, height;
	double anchorX, anchorY;
		
	public AIgent(int x, int y) {
		
		originX = x; originY = y;
		anchorX = x; anchorY = y;
		
		this.location = new Vector(x, y);
		this.acceleration = new Vector(0, 0);
		this.velocity = new Vector(0, 0);
		
		width = 50; height = 50;
		originWidth = width; originHeight = height;

		this.color = new Color(222, 22, 22);
	}

	@Override
	public void Draw(Graphics2D g) {
		g.setColor(this.color);
		g.fillOval((int)location.x - width / 2, (int)location.y - height / 2, this.width, this.height);
	}

	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		
	}

	// Methods for resizing and repositioning relative to the screens size
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
