package ics3u.summative;

public abstract class Ability {
	
	public final String TYPE;
	
	public Ability(String TYPE_) {
		this.TYPE = TYPE_;
	}
	
	public void trigger(Entity attacker, Board b) {}
}
