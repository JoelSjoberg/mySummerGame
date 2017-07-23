import java.awt.Graphics2D;

public interface GameCore {
	void loop();
	void pause();
	void takeInput();
	void draw(Graphics2D g);
}
