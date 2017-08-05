import java.awt.Graphics2D;

public interface GameCore {
	void loop();
	void takeInput();
	void draw(Graphics2D g);
	void setSize(int width, int height);
	void pause(boolean keyIsPressed);
}
