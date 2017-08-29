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
	boolean aa = true;
	BufferStrategy bs;
	JFrame frame;
	Graphics2D g;
	Font pauseFont = new Font("Helvetica", Font.BOLD, 25);
	Font runFont = new Font("Helvetica", Font.BOLD, 15);
	RenderingHints rh;
	public View(int width, int height)
	{
		addKeyListener(new KeyBoard());
		setFocusable(true);
		setSize(new Dimension(width, height));
		setVisible(true);
		this.height = height;
		this.width = width;
		
		frame = new JFrame();
		//frame.setUndecorated(true);
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	void initView(){
		this.createBufferStrategy(3);
		this.bs = this.getBufferStrategy();
		this.g = (Graphics2D) bs.getDrawGraphics();
		this.g = (Graphics2D) bs.getDrawGraphics();
		rh = new RenderingHints(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}

	double scaleWidth = 1, scaleHeight = 1;
	void render(GameCore game, boolean paused)
	{
		this.g = (Graphics2D) bs.getDrawGraphics();
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		if(aa) g.setRenderingHints(rh);
		
		// Scale the graphics with the window size
		scaleWidth = (this.getWidth() * 100 /width);
		scaleHeight = (this.getHeight() * 100 /height);
		scaleWidth /= 100;
		scaleHeight /= 100;
		g.scale(scaleWidth, scaleHeight);
		
		
		// Draw the game here
		if(paused) drawPauseMenu();
		g.setFont(runFont);
		game.draw(g);
		
		bs.show();
		g.dispose();
	}
	
	public void drawPauseMenu() {
		g.setFont(pauseFont);
		g.drawString("Pause", width - 100, 30);
		g.drawString("Options", width / 2, 50);
		g.drawString("Resolution: 		" + getWidth() + " x " + getHeight(), width / 5, 120);
		g.drawString("Anti-Aliasing: 	" + aa, width / 5, 150);
	}
}
