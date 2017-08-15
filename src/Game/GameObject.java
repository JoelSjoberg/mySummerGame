package Game;
import java.awt.Graphics2D;

public interface GameObject {
	void Draw(Graphics2D g);
	void doAction();
	void startAnimation();
	void resize(int width, int height, int originWidth, int originHeight);
	void reposition(int width, int height, int originWidth, int originHeight);
	double getX();
	double getY();
	int getWidth();
	int getHeight();
}