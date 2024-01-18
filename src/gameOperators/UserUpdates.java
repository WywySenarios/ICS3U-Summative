package gameOperators;

import gameElements.Card;

public interface UserUpdates {
	public String getCommand(String message);
	
	// display an Entity taking damage
	public void entityDamage(int lane, boolean evil, int damage);

	// display an Entity dying
	public void entityDeath(int lane, boolean evil);

	// display an Entity being placed or summoned
	public void summonEntity(int lane, boolean evil);

	// display a Player taking damage
	public void playerDamage(boolean evil, int damage);

	// display a Player dying
	public void playerDeath(boolean evil);

	// display a Player being summoned
	public void summonPlayer(boolean evil);

	// display game end messages
	public void gameEnd();

	// display a Card being removed
	public void inventoryRemoveCard(Card givenCard);

	// display a Card being added
	public void inventoryAddCard(Card givenCard);
	
	// pregame stuff. XD
	// this is just for the GUI users.
	public void pregame();
}
