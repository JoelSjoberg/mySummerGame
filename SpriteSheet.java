import java.awt.Graphics2D;

public interface SpriteSheet {
	void draw(Graphics2D g, int x, int y, int width, int height);
	void idleAnimation();
	void attackAnimation();
	void slashAnimation();
	void bounce();
	void backingOff();
}
