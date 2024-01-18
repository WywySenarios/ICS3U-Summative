package ics3u.summative;

import gameOperators.LocalConsoleUser;
import gameOperators.LocalGUIUser;
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
		Server s = new Server(new LocalGUIUser(true, true), new LocalConsoleUser(false, 1000), "exampleEvilDeck", "exampleGoodDeck", "exampleGame.txt");
		s.play();
	}

}
