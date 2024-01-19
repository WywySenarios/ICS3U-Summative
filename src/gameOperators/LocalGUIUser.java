package gameOperators;

import gameElements.Card;

public class LocalGUIUser extends User {
	
	public GUI gui;

	public LocalGUIUser(boolean evil_, boolean developerMode) {
		super("LocalGUI", evil_);
		
		this.gui = new GUI(this, developerMode);
	}

	@Override
	public void entityDamage(int lane, boolean evil, int damage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void entityDeath(int lane, boolean evil) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonEntity(int lane, boolean evil) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerDamage(boolean evil, int damage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerDeath(boolean evil) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonPlayer(boolean evil) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inventoryRemoveCard(Card givenCard) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inventoryAddCard(Card givenCard) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pregame() {
		this.gui.pregame();
	}

	@Override
	public String getCommand(String message) {
		// TODO Auto-generated method stub
		return this.gui.getCommand(message);
	}
}