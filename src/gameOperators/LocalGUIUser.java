package gameOperators;

/**
 *
 * @author pc
 */
public class LocalGUIUser extends User {
	
    /**
     *
     */
    public GUI gui;

    /**
     *
     * @param evil_
     * @param developerMode
     * @param DELAY_
     */
    public LocalGUIUser(boolean evil_, boolean developerMode, int DELAY_) {
		super("LocalGUI", evil_, DELAY_);
		
		this.gui = new GUI(this, developerMode, "CData\\", DELAY_);
	}

    /**
     *
     * @param lane
     * @param evil
     * @param damage
     */
    @Override
	public void entityDamage(int lane, boolean evil, String damage) {
		this.gui.entityDamage(lane, evil, damage);
	}

    /**
     *
     * @param lane
     * @param evil
     */
    @Override
	public void entityDeath(int lane, boolean evil) {
		this.gui.entityDeath(lane, evil);
	}

    /**
     *
     * @param lane
     * @param evil
     */
    @Override
	public void summonEntity(int lane, boolean evil) {
		this.gui.summonEntity(lane, evil);
	}

    /**
     *
     * @param evil
     * @param damage
     */
    @Override
	public void playerDamage(boolean evil, String damage) {
		this.gui.playerDamage(evil, damage);
	}

    /**
     *
     * @param evil
     */
    @Override
	public void playerDeath(boolean evil) {
		this.gui.playerDeath(evil);
	}

    /**
     *
     * @param evil
     */
    @Override
	public void summonPlayer(boolean evil) {
		this.gui.summonPlayer(evil);
	}

    /**
     *
     */
    @Override
	public void gameEnd() {
		this.gui.gameEnd();
	}

    /**
     *
     * @param inventoryIndex
     * @param evil
     */
    @Override
	public void inventoryRemoveCard(int inventoryIndex, boolean evil) {
		this.gui.inventoryRemoveCard(inventoryIndex, evil);
	}

    /**
     *
     * @param inventoryIndex
     * @param evil
     */
    @Override
	public void inventoryAddCard(int inventoryIndex, boolean evil) {
		this.gui.inventoryAddCard(inventoryIndex, evil);
		
	}

    /**
     *
     */
    @Override
	public void pregame() {
		this.gui.pregame();
	}

    /**
     *
     * @param message
     * @return
     */
    @Override
	public String getCommand(String message) {
		return this.gui.getCommand(message);
	}
}