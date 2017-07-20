import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class Logic extends JFrame implements KeyListener{

	
	public Logic(int width, int height)
	{
		pack();
		setFocusable(true);
		setSize(new Dimension(width, height));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(this);
		setVisible(true);
		
	}
	
	void update(boolean paused){
		
		for(int i = 0; i < keys.length; i++){
			if(keys[i]) System.out.println(i);
		}

		if(!paused){
			// Do game logic
			
		}else{
			// Do pause logic
		}
	}
	
	boolean[] keys = new boolean[127];
	@Override
	public void keyPressed(KeyEvent k) {
		keys[k.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent k) {
		keys[k.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

}
