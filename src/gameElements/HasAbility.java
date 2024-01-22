package gameElements;

/**
 *
 * @author pc
 */
public interface HasAbility {

    /**
     *
     * @param command
     */
    public void triggerAbilities(String command);

    /**
     *
     * @param command
     * @param e
     */
    public void triggerAbilities(String command, Entity e);

    /**
     *
     * @param command
     * @param e
     * @param potency
     */
    public void triggerAbilities(String command, Entity e, int potency);
}
