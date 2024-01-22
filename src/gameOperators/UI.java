package gameOperators;

/**
 *
 * @author pc
 */
public interface UI {

    /**
     *
     * @param args
     * @param entityUpdated
     * @param evil
     */
    void updateEntity(String[] args, int entityUpdated, boolean evil);

    /**
     *
     * @param args
     * @param evil
     */
    void updatePlayer(String[] args, boolean evil);

    /**
     *
     * @param args
     */
    void updateGameStatus(String[] args);
}
