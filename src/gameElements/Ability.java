package gameElements;

public abstract class Ability implements Duplicable {
	
	public final String TYPE;
	public boolean triggeredInInventory;
	
	public Ability(String TYPE_, boolean triggeredInInventory_) {
		this.TYPE = TYPE_;
		this.triggeredInInventory = triggeredInInventory_;
	}
	
	public String toString() {
		return TYPE;
	}
	
	public void trigger(Object abilityHolder) {}
	public void trigger(Object abilityHolder, Entity e) {}
	public void trigger(Object abilityHolder, Entity e, int potency) {}
}
