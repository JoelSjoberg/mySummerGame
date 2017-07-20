import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

public class View extends Canvas{
	
	int width, height;
	BufferStrategy bs;
	public View(int width, int height)
	{
		this.width = width;
		this.height = height;
		setSize(new Dimension(width, height));
		setVisible(true);
		setBackground(Color.black);
	}
	
	Graphics2D g;
	void render(boolean paused)
	{
		bs = this.getBufferStrategy();
		g = (Graphics2D) bs.getDrawGraphics();
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		
		// Here we can draw the game state
		if(!paused) {
			
			g.setColor(Color.red);
			g.fillRect(50, 50, width, height);
		}
		else{
			// Draw pause screen
		}
		
		
		bs.show();
		g.dispose();
	}
	
}
