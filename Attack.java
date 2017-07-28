
public class Attack implements Ability{

	int cost = 0;
	int eventNumber;
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
		if (excecuting)
		{
			switch(eventNumber){
			case(0): // Move to target at the start of execution
				player.acceleration = new Vector(player.target.location.x - player.location.x, player.target.location.y - player.location.y);
				player.acceleration.normalize();
				//Check that acceleration and velocity have the same degree to prevent infinity loop where player continuously rotate around target 
				if(Math.abs(player.acceleration.getDegree() - player.velocity.getDegree()) > player.width/2) player.velocity.div(1.5);
				if(player.collide(player.target)) {
					eventNumber++;
				}
				break;
				
			case(1): //  stun the enemy and deal damage but if the enemy was attacking you simultaneously
					// then you both fly backwards
// IMPLEMENT DAMAGE DEALING AND OTHER NON ANIMATION STUFF
				//if(player.target.attacking())player.velocity.rotate(7);
				player.velocity.rotate((int)(Math.random()* 5) + 1);
				player.acceleration.mult(0);
				eventNumber++;
				break;
				
			case(2): // fly away from the target to emulate a "clash"
				if(player.collide(player.target)){
					//fly away from target after collision, should not touch target after this!
					player.maxSpeed = 25;
					player.velocity.mult(25);
				}else{
					// Slow down to allow for more input and eventually move away
					player.velocity.div(1.9);					
				}
				if(player.velocity.length() <= 1) eventNumber++;
				break;
				
			case(3): // After bounce decrease velocity to zero(after traveling for a while), then exit the method and return to anchor point
				player.velocity.mult(0);
				this.excecuting = false;
				eventNumber = 0;
				player.goBack();
				return true;
			}
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