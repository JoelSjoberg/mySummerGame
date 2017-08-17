package GameUtility;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class View extends Canvas{

	private static final long serialVersionUID = 1L;
	int width, height;
	BufferStrategy bs;
	JFrame frame;
	Graphics2D g;
	public View(int width, int height)
	{
		addKeyListener(new KeyBoard());
		setFocusable(true);
		setSize(new Dimension(width, height));
		setVisible(true);
		this.height = height;
		this.width = width;
		
		frame = new JFrame();
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		
	}
	
	void initView(){
		this.bs = this.getBufferStrategy();
		this.g = (Graphics2D) bs.getDrawGraphics();
		this.g.setFont(new Font("helvetica", Font.BOLD, 38));
		this.g = (Graphics2D) bs.getDrawGraphics();
		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHints(rh);
	}
	void render(GameCore game)
	{
		this.g = (Graphics2D) bs.getDrawGraphics();
		g.clearRect(0, 0, this.getWidth(), this.getHeight());

		// Draw the game here		
		game.draw(g);
		frame.repaint();
		
		bs.show();
		g.dispose();
	}
}
