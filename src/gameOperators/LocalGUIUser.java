package gameOperators;

public class LocalGUIUser extends User {
	
	public GUI gui;

	public LocalGUIUser(boolean evil_, boolean developerMode, int DELAY_) {
		super("LocalGUI", evil_, DELAY_);
		
		this.gui = new GUI(this, developerMode, "CData\\", DELAY_);
	}

	@Override
	public void entityDamage(int lane, boolean evil, String damage) {
		this.gui.entityDamage(lane, evil, damage);
	}

	@Override
	public void entityDeath(int lane, boolean evil) {
		this.gui.entityDeath(lane, evil);
	}

	@Override
	public void summonEntity(int lane, boolean evil) {
		this.gui.summonEntity(lane, evil);
	}

	@Override
	public void playerDamage(boolean evil, String damage) {
		this.gui.playerDamage(evil, damage);
	}

	@Override
	public void playerDeath(boolean evil) {
		this.gui.playerDeath(evil);
	}

	@Override
	public void summonPlayer(boolean evil) {
		this.gui.summonPlayer(evil);
	}

	@Override
	public void gameEnd() {
		this.gui.gameEnd();
	}

	@Override
	public void inventoryRemoveCard(int inventoryIndex, boolean evil) {
		this.gui.inventoryRemoveCard(inventoryIndex, evil);
	}

	@Override
	public void inventoryAddCard(int inventoryIndex, boolean evil) {
		this.gui.inventoryAddCard(inventoryIndex, evil);
		
	}

	@Override
	public void pregame() {
		this.gui.pregame();
	}

	@Override
	public String getCommand(String message) {
		return this.gui.getCommand(message);
	}
}