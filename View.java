import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class View extends Canvas{
	

	private static final long serialVersionUID = 1L;
	int width, height;
	BufferStrategy bs;
	JFrame frame = new JFrame();
	public View(int width, int height)
	{
		this.width = width;
		this.height = height;
		frame.pack();
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(width, height));
		frame.setFocusable(true);
		frame.setVisible(true);
		addKeyListener(new KeyBoard());
		setSize(new Dimension(width, height));
		setFocusable(true);
		setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	Graphics2D g;
	void render(GameCore game)
	{
		bs = this.getBufferStrategy();
		g = (Graphics2D) bs.getDrawGraphics();
		RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_TEXT_ANTIALIASING,
	             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    g.setRenderingHints(rh);
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		
		// Here we can draw the game state			
		game.draw(g);

		bs.show();
		g.dispose();
	}
}
