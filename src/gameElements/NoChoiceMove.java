package gameElements;

/**
 *
 * @author pc
 */
public interface NoChoiceMove {

    /**
     *This method executes a move, given the user's choice.
     * @param attacker denotes which Entity is doing this move.
     * @param b denotes the Board in which this action is taking place on.
     */
    public void move(Entity attacker, Board b);
}
