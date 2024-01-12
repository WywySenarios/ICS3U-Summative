package gameOperators;

import java.util.Scanner;

import gameElements.Deck;
import gameElements.Entity;
import gameElements.Environment;
import gameElements.Player;

public abstract class User implements UI {

	private String uiType;
	protected Server server;
	protected boolean evil;
	protected String gameStatus;
	protected Scanner console = new Scanner(System.in);

	protected Player evilPlayer;
	protected Player goodPlayer;
	protected Entity[] evilEntities = new Entity[5];
	protected int[] evilAttacks = new int[5];
	protected Entity[] goodEntities = new Entity[5];
	protected int[] goodAttacks = new int[5];
	protected Environment[] environments = new Environment[5];
	protected Deck evilDeck;
	protected Deck goodDeck;

	public User(String uiType_, boolean evil_) {
		this.uiType = uiType_;
		this.evil = evil_;
		this.gameStatus = "ongoing";
	}

	/*
	 * the functions from the UI interface are mainly focused on changing the
	 * current displayed information so that GUI systems can show animations for
	 * changing the stuff one at a time.
	 */
	
	public String getUiType() {
		return uiType;
	}
	
	public String getCommand() {
		return null;
	}
	
	public String getCommand(String message) {
		return null;
	}
}
