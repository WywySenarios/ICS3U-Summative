package ics3u.summative;

import gameOperators.Server;
import gameOperators.User;

/**
 *
 * @author pc
 */
public class ICS3USummative {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws Exception {
		@SuppressWarnings("unused")
		Server s = new Server("console", new User("console", true), new User("console", false), "exampleEvilDeck", "exampleGoodDeck", "exampleGame");
	}

}
