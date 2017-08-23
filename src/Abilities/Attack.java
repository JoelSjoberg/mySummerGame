package Abilities;
import Game.Player;
import GameUtility.Vector;

public class Attack implements Ability{

	int cost = 50;
	int eventNumber;
	int damage = 0;
	boolean excecuting = false;
	String name = "Attack";

	public void begin()
	{
		// Setup the execution process
		System.out.println(this.name);
		this.excecuting = true;
		this.eventNumber = 0;
	}
	
	@Override
	public boolean execute(Player player)
	{	
		switch(eventNumber){
			case(0): // Move to target at the start of execution
				player.acceleration = new Vector(player.target.getX() - player.location.getX(), player.target.getY() - player.location.getY());
				player.acceleration.normalize();
				//Check that acceleration and velocity have the same degree to prevent infinity loop where player continuously rotate around target 
				if(Math.abs(player.acceleration.getDegree() - player.velocity.getDegree()) > player.getWidth()/2) player.velocity.div(1.5);
				player.sprite.attackAnimation();
				if(player.collide(player.target)) {
					eventNumber++;
				}
				break;
				
			case(1): //  stun the enemy and deal damage
				// TODO LATER
				// this still needs more thought and effort, what if there are multiple attack and defend abilities?
				// if the target is also attacking(physical), deal no damage, clash and restore a bit or energy and hp
				/*if(player.target.attacking()){
				 	player.hp += (player.getMaxHp() - player.getHp) / 10;
				 	player.energy += (player.getMaxEnergy() - player.getEnergy) / 20;
				 }
				 
				// Else if target is blocking, do not stagger and deal reduced damage
					if(player.target.blocking){
						damage = (int) (player.getStrength() * player.velocity.length());
						damage = (int) (damage / Math.pow(player.target.getDefence() + 0.5, 3));
					}
				*/
				
				// Count and deal damage(((strength * velocity) / ((target.defence) ** 3))

				damage = (int) (player.getStrength() * player.velocity.length());
				damage = (int) (damage / Math.pow(player.target.getDefence(), player.getAgility()) * player.getDefence());
			
				// damage should be reduced if the target is blocking and ignored if the player clash with the target
				player.target.sufferDamage(damage);
				// should be ignored if target is blocking and if the player clash with the target
				player.target.stagger();

				// Move back from the enemy
				player.velocity.rotate(180);
				player.acceleration.mult(0);
				player.sprite.slashAnimation();
				eventNumber++;
				break;
				
			case(2): // fly away from the target to emulate a "clash"
				player.sprite.bounce();
				if(player.collide(player.target)){	//fly away from target after collision, should not touch target after this!
					player.maxSpeed = 75;
					player.velocity.mult(75);
					
				}else{	// Slow down to allow for more input and eventually move away
					player.velocity.div(1.2);					
				}
				if(player.velocity.length() <= 0.006) eventNumber++;
				break;
				
			case(3): // After bounce decrease velocity to zero(after traveling for a while), then exit the method and return to anchor point
				player.velocity.mult(0);
				this.excecuting = false;
				eventNumber = 0;
				player.goBack();
				return true;
			}
		return false;
	}
	
	@Override
	public void interrupt() {
		this.excecuting = false;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	public int getCost(){
		return this.cost;
	}
	
	public boolean getExecuting(){
		return this.excecuting;
	}
}