package ics3u.summative;

import gameOperators.LocalConsoleUser;
import gameOperators.LocalGUIUser;
import gameOperators.Server;

/**
 *
 * @author pc
 */
@SuppressWarnings("unused")
public class ICS3USummative {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws Exception {
		Server s = new Server(new LocalGUIUser(true, true, 1000), new LocalGUIUser(false, true, 1000), "exampleEvilDeck", "exampleGoodDeck", "logs\\exampleGame.txt", 1000);
		s.play();
	}

}
