package gameElements;

/**
 *
 * @author pc
 */
public interface ChoiceMove {

    /**
     *
     * @param attacker
     * @param b
     * @param selection
     */
    public void move(Entity attacker, Board b, int selection);
}
