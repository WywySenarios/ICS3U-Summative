package gameOperators;

/**
 *
 * @author pc
 */
public interface UserUpdates {

    /**
     *
     * @param message
     * @return
     */
    public String getCommand(String message);
	
	// display an Entity taking damage

    /**
     *
     * @param lane
     * @param evil
     * @param damage
     */
	public void entityDamage(int lane, boolean evil, String damage);

	// display an Entity dying

    /**
     *
     * @param lane
     * @param evil
     */
	public void entityDeath(int lane, boolean evil);

	// display an Entity being placed or summoned

    /**
     *
     * @param lane
     * @param evil
     */
	public void summonEntity(int lane, boolean evil);

	// display a Player taking damage

    /**
     *
     * @param evil
     * @param damage
     */
	public void playerDamage(boolean evil, String damage);

	// display a Player dying

    /**
     *
     * @param evil
     */
	public void playerDeath(boolean evil);

	// display a Player being summoned

    /**
     *
     * @param evil
     */
	public void summonPlayer(boolean evil);

	// display game end messages

    /**
     *
     */
	public void gameEnd();

	// display a Card being removed

    /**
     *
     * @param inventoryIndex
     * @param evil
     */
	public void inventoryRemoveCard(int inventoryIndex, boolean evil);

	// display a Card being added

    /**
     *
     * @param inventoryIndex
     * @param evil
     */
	public void inventoryAddCard(int inventoryIndex, boolean evil);
	
	// pregame stuff. XD
	// this is just for the GUI users.

    /**
     *
     */
	public void pregame();
}
