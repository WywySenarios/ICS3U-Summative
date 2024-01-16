package gameOperators;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import gameElements.Board;
import gameElements.Duplicable;
import gameElements.Entity;
import gameElements.Environment;
import gameElements.Player;
import gameElements.Special;

public class Server implements UI {

	private String logPath;
	private User evilUser;
	private User goodUser;
	protected Board b;
	private String gameStatus = "ongoing";

	public Server(User user1_, User user2_, String evilDeckPath, String goodDeckPath, String logPath_)
			throws Exception {
		this.logPath = logPath_;

		this.b = new Board(true, evilDeckPath, goodDeckPath, "evilPlayerUsername", "stickmanLeader",
				"goodPlayerUsername", "wywyLeader");
		if (!this.b.addServer(this)) {
			throw new Exception("Unsuccessful registry of server on the Board class.");
		}

		if (user1_.evil == user2_.evil) {
			if (user1_.evil == true) {
				throw new Exception("Invalid input: both users are \"evil\".");
			} else {
				throw new Exception("Invalid input: both users are \"good\".");
			}
		} else if (user1_.evil) {
			this.evilUser = user1_;
			this.goodUser = user2_;
		} else {
			this.evilUser = user2_;
			this.goodUser = user1_;
		}
		
		// create a new area for Log to happen
		File logFile = new File(logPath);
		logFile.createNewFile();
	}

	public void updateEntity(String[] args, int entityUpdated, boolean evil) {
		if (evil) {
			distributeObjects(b.evilEntities[entityUpdated]);
		} else {
			distributeObjects(b.goodEntities[entityUpdated]);
		}
		
		evilUser.updateEntity(args, entityUpdated, evil);
		goodUser.updateEntity(args, entityUpdated, evil);
		log("Updated Entity at location \"" + entityUpdated + "\".");
	}

	public void updatePlayer(String[] args, boolean evil) {
		if (evil) {
			distributeObjects(b.evilPlayer);
		} else {
			distributeObjects(b.goodPlayer);
		}
		
		evilUser.updatePlayer(args, evil);
		goodUser.updatePlayer(args, evil);
		if (evil) {
			log("Updated \"evil\" Player.");
		} else {
			log("Updated \"good\" Player.");
		}
	}

	public void updateGameStatus(String[] args) {
		evilUser.updateGameStatus(args);
		goodUser.updateGameStatus(args);

		if (args[0].equals("end")) {
			this.gameStatus = "end";
		}

		String logOutput = "Updated Game Status to \"[";
		for (String i : args) {
			logOutput += i + ",";
		}
		logOutput = logOutput.substring(0, logOutput.length() - 1) + "]\"";

		log(logOutput);
	}

	private boolean log(String logInfo) {
		try {
			FileWriter fileWriter = new FileWriter(logPath);
			PrintWriter output = new PrintWriter(fileWriter, true);
			
			output.print(logInfo);
			output.close();
			fileWriter.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public void play() throws Exception {
		
		if (! gameStatus.equals("ongoing")) {
			throw new Exception("Game already started");
		}

		// when Board initializes, they already hand out starting hands, so no need to
		// worry about drawing Cards here

		// immediately broadcast who has what

		/*
		 * 
		 * private Deck evilDeck; private Deck goodDeck;
		 */

		// Players
		// evil Player
		evilUser.evilPlayer = this.b.evilPlayer;
		goodUser.evilPlayer = this.b.evilPlayer;

		// good Player
		evilUser.goodPlayer = this.b.goodPlayer;
		goodUser.goodPlayer = this.b.goodPlayer;

		// Entities
		// evil Entities
		evilUser.evilEntities = this.b.evilEntities;
		goodUser.evilEntities = this.b.evilEntities;

		// good Entities
		evilUser.goodEntities = this.b.goodEntities;
		goodUser.goodEntities = this.b.goodEntities;

		// Moves
		// N/A, the Moves are private lmao

		// N/A, the selections are private lmao

		// Environments
		evilUser.environments = this.b.environments;
		goodUser.environments = this.b.environments;

		// Decks
		// N/A, the Decks are private lmao

		boolean thinking = false;
		String evilCommand;
		String goodCommand;
		while (!gameStatus.equals("end")) {
			// pre-turn
			b.preturns();

			// evil turn
			do {
				evilCommand = evilUser.getCommand();
				switch (evilCommand.toLowerCase()) {
				case "place", "placecard":
					try {
						b.placeCard(Integer.parseInt(evilUser.getCommand("inventorySlot")),
								Integer.parseInt(evilUser.getCommand("lane")), true);
					} catch (ClassCastException e) {
					} catch (NumberFormatException e) {
						// from the evilUser.getCommand()s inside the b.placeCard() method parameter
						// input
					}

					break;
				default:
					break;
				}
			} while (thinking);

			// good turn
			do {
				goodCommand = goodUser.getCommand();
				switch (goodCommand.toLowerCase()) {
				case "place", "placecard":
					try {
						b.placeCard(Integer.parseInt(goodUser.getCommand("inventorySlot")),
								Integer.parseInt(goodUser.getCommand("lane")), false);
					} catch (ClassCastException e) {
					} catch (NumberFormatException e) {
						// from the evilUser.getCommand()s inside the b.placeCard() method parameter
						// input
					}

					break;
				default:
					break;
				}
			} while (thinking);

			// fighting
			b.fight();

			// post-turn
			b.endTurn();
		}
	}
	
	private void distributeObjects(Object givenObject) {
		Object output = null;
		
		// duplicate and distribute given objects.
		if (givenObject instanceof Duplicable) {
			output = ((Duplicable) givenObject).duplicate();
		} else if (givenObject instanceof Entity) {
			Entity givenEntity = (Entity) givenObject;
			output = new Entity(givenEntity.type, givenEntity.health, givenEntity.hpr, givenEntity.shield, givenEntity.aggressive, null, null);
		} else if (givenObject instanceof Environment) {
			Environment givenEnvironment = (Environment) givenObject;
			output = new Environment(givenEnvironment.type, givenEnvironment.moves, givenEnvironment.abilities, givenEnvironment.PERMANENT);
		} else if (givenObject instanceof Special) {
			Special givenSpecial = (Special) givenObject;
			output = new Special(givenSpecial.type, givenSpecial.charges, givenSpecial.chargeRegen, givenSpecial.sacrificial, givenSpecial.move, givenSpecial.abilities);
		} else if (givenObject instanceof Player) {
			Player givenPlayer = (Player) givenObject;
			output = new Player(givenPlayer.username, givenPlayer.name, givenPlayer.inventory, givenPlayer.type);
			((Player) output).health = givenPlayer.health;
		} else {
			output = 1;
		}
		
		evilUser.lastReceivedObject = output;
		goodUser.lastReceivedObject = output;
	}
}
