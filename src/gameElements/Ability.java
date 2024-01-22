package gameElements;

/**
 *
 * @author pc
 */
public abstract class Ability implements Duplicable {
	
    /**
     *
     */
    public final String TYPE;

    /**
     *
     */
    public boolean triggeredInInventory;
	
    /**
     *
     * @param TYPE_
     * @param triggeredInInventory_
     */
    public Ability(String TYPE_, boolean triggeredInInventory_) {
		this.TYPE = TYPE_;
		this.triggeredInInventory = triggeredInInventory_;
	}
	
    /**
     *
     * @return
     */
    public String toString() {
		return TYPE;
	}
	
    /**
     *
     * @param abilityHolder
     */
    public void trigger(Object abilityHolder) {}

    /**
     *
     * @param abilityHolder
     * @param e
     */
    public void trigger(Object abilityHolder, Entity e) {}

    /**
     *
     * @param abilityHolder
     * @param e
     * @param potency
     */
    public void trigger(Object abilityHolder, Entity e, int potency) {}
}
