package ics3u.summative;

public class Board extends Data {
	
	public static String fileLocation = "C:\\Java\\ICS3U Summative";
	private Player evilPlayer;
	private Player goodPlayer;
	private Entity[] evilEntities = new Entity[5];
	private Entity[] goodEntities = new Entity[5];
	private Deck evilDeck;
	private Deck goodDeck;

	public Board(boolean experimentalDeck, String evilDeckName, String goodDeckName) {
		super("Board", findFilePath(experimentalDeck));
		evilDeck = new Deck(this, fileLocation + "\\DeckData\\" + evilDeckName + ".JSON");
		//goodDeck = new Deck(this, fileLocation + "\\DeckData\\" + goodDeckName + ".JSON");
		
		System.out.println(evilDeck.originalDeckToString());
		System.out.println(evilDeck.currentDeckToString());
	}
	
	private static String findFilePath(boolean experimentalDeck) {
		// this function is a workaround to the requirement of using the "super" keyword in the first line of the constructor.
		if (experimentalDeck) {
			return fileLocation + "\\ExampleCData.JSON";
		} else {
			return fileLocation + "\\CData.JSON";
		}
	}
}
