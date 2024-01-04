package ics3u.summative;

public class Board extends Data {

	public Board(boolean experimentalDeck) {
		super("Board", findFilePath(experimentalDeck));
	}
	
	private static String findFilePath(boolean experimentalDeck) {
		// this function is a workaround to the requirement of using the "super" keyword in the first line of the constructor.
		if (experimentalDeck) {
			return "C:\\Java\\ICS3U Summative\\ExampleCData";
		} else {
			return "C:\\Java\\ICS3U Summative\\CData";
		}
	}
}
