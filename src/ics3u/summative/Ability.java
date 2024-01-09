package ics3u.summative;

public abstract class Ability {
	
	public final String TYPE;
	public boolean triggeredInInventory;
	public Board b;
	
	public Ability(String TYPE_, boolean triggeredInInventory_, Board b_) {
		this.TYPE = TYPE_;
		this.triggeredInInventory = triggeredInInventory_;
		this.b = b_;
	}
	
	public void trigger(Object abilityHolder) {}
	public void trigger(Object abilityHolder, Entity e) {}
	public void trigger(Object abilityHolder, Entity e, int potency) {}
}
