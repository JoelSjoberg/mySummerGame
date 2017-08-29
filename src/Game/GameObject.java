package Game;
import java.awt.Graphics2D;

public interface GameObject {
	void Draw(Graphics2D g);
	void doAction();
	void startAnimation();
	void stagger();
	void beStaggered();
	void sufferDamage(int damage);
	boolean collide(GameObject target);
	
	int getHp();
	int getMaxHp();
	
	double getEnergy();
	int getMaxEnergy();
	
	int getStrength();
	int getDefence();
	int getAgility();
	
	
	String getName();
	double getX();
	double getY();
	int getWidth();
	int getHeight();
}