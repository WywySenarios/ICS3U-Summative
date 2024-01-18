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

		if (this.b.evilPlayer.inventory[0] == null) {
			throw new Exception("yay the inventory slot is null...");
		}

		if (!gameStatus.equals("ongoing")) {
			throw new Exception("Game already started");
		}

		// when Board initializes, they already hand out starting hands, so no need to
		// worry about drawing Cards here

		// Players
		this.evilUser.evilPlayer = (Player) this.b.evilPlayer.duplicate();
		this.goodUser.evilPlayer = (Player) this.b.evilPlayer.duplicate();

		this.evilUser.goodPlayer = (Player) this.b.goodPlayer.duplicate();
		this.goodUser.goodPlayer = (Player) this.b.goodPlayer.duplicate();

		// Entities
		Entity[] outputEvilEntities = new Entity[5];
		Entity[] outputGoodEntities = new Entity[5];

		for (int i = 0; i < 5; i++) {
			try {
				outputEvilEntities[i] = (Entity) this.b.evilEntities[i].duplicate();
			} catch (NullPointerException e) {
			}
			try {
				outputGoodEntities[i] = (Entity) this.b.goodEntities[i].duplicate();
			} catch (NullPointerException e) {
			}
		}

		// evil Entities
		this.evilUser.evilEntities = outputEvilEntities;
		this.goodUser.evilEntities = outputEvilEntities;

		// good Entities
		this.evilUser.goodEntities = outputGoodEntities;
		this.goodUser.goodEntities = outputGoodEntities;

		// Moves
		// N/A, the Moves are private lmao

		// N/A, the selections are private lmao

		// Environments
		Environment[] outputEnvironments = new Environment[5];

		for (int i = 0; i < 5; i++) {
			try {
				outputEnvironments[i] = (Environment) this.b.environments[i].duplicate();
			} catch (NullPointerException e) {
			}
		}

		this.evilUser.environments = outputEnvironments;
		this.goodUser.environments = outputEnvironments;

		// broadcast pregames
		this.evilUser.pregame();
		this.goodUser.pregame();
		
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
				evilCommand = evilUser.getCommand(null);
				if (evilCommand != null) {
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
				}
			} while (thinking);

			// good turn
			do {
				goodCommand = goodUser.getCommand(null);
				if (goodCommand != null) {
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
				}
			} while (thinking);

			// fighting
			b.fight();

			// post-turn
			b.endTurn();
		}
	}

	private void distributeObjects(Object givenObject) {
		Object output;

		// duplicate and distribute given objects.
		if (givenObject instanceof Duplicable) {
			output = ((Duplicable) givenObject).duplicate();
		} else {
			output = givenObject;
		}

		evilUser.lastReceivedObject = output;
		goodUser.lastReceivedObject = output;
	}
}
