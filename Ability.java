
public interface Ability {

	// returns true when done executing
	boolean execute(Player p);
	int getCost();
	String getName();
	boolean getExecuting();
	void interrupt();
	void begin();
}
