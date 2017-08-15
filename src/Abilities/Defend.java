package Abilities;
import Game.Player;

public class Defend implements Ability {

	int cost = 0;
	long executionTime = 1500000000;
	boolean excecuting;
	String name = "Defend";
	@Override
	public boolean execute(Player p) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public int getCost() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean getExecuting() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void interrupt() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void begin() {
		// TODO Auto-generated method stub
		
	}

}
