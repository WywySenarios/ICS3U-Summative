package ics3u.summative;

import gameOperators.LocalConsoleUser;
import gameOperators.Server;

/**
 *
 * @author pc
 */
public class ICS3USummative {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws Exception {
		Server s = new Server("console", new LocalConsoleUser(true), new LocalConsoleUser(false), "exampleEvilDeck", "exampleGoodDeck", "exampleGame.txt");
		s.play();
	}

}
