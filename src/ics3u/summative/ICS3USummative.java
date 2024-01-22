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
		//Server s = new Server(new LocalConsoleUser(true, 1000), new LocalConsoleUser(false, 1000), "exampleEvilDeck", "exampleGoodDeck", "logs\\exampleGame.txt", 1000);
		Server s = new Server(new LocalGUIUser(true, true, 1000), new LocalConsoleUser(false, 1000), "exampleEvilDeck", "exampleGoodDeck", "logs\\exampleGame.txt", 1000);
		//Server s = new Server(new LocalGUIUser(true, true, 1000), new LocalGUIUser(false, true, 1000), "exampleEvilDeck", "exampleGoodDeck", "logs\\exampleGame.txt", 1000);
		s.play();
	}

}
