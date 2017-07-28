import java.awt.Graphics2D;

public interface GameObject {
	void Draw(Graphics2D g);
	void doAction();
	void resize(int width, int height, int originWidth, int originHeight);
	void reposition(int width, int height, int originWidth, int originHeight);
}