import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoard implements KeyListener{

	public static boolean key[] = new boolean[127];
	
	public void keyPressed(KeyEvent k) {
		key[k.getKeyCode()] = true;
		//System.out.println(k.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent k) {
		key[k.getKeyCode()] = false;
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

}
