package gameElements;

/**
 *
 * @author pc
 */
public interface HasAbility {

    /**
     * This method attempts to trigger all of the Abilities this Object holds.
     * @param command
     */
    public void triggerAbilities(String command);

    /**
     * This method attempts to trigger all of the Abilities this Object holds, specifically when this Entity is being acted upon.
     * @param command
     * @param e denotes the Entity acting upon this Object.
     */
    public void triggerAbilities(String command, Entity e);

    /**
     * This method attempts to trigger all of the Abilities this Object holds, specifically when this Entity is being acted upon.
     * @param command
     * @param e denotes the Entity acting upon this Object.
     * @param potency denotes the potency of the acts against this Object.
     */
    public void triggerAbilities(String command, Entity e, int potency);
}
