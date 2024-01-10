package gameOperators;

import gameElements.Board;

public class Server implements UI {

	private String uiType;
	private String logPath;
	private User evilUser;
	private User goodUser;
	protected Board b;
	private String gameStatus = "ongoing";

	public Server(String uiType_, User user1_, User user2_, String evilDeckPath, String goodDeckPath, String logPath_)
			throws Exception {
		this.uiType = uiType_;
		this.logPath = logPath_;

		this.b = new Board(true, evilDeckPath, goodDeckPath, "evilPlayerUsername", "stickmanLeader",
				"goodPlayerUsername", "wywyLeader");
		if (!this.b.addServer(this)) {
			throw new Exception("Unsuccessful registry of server on the Board class.");
		}

		user1_.server = this;
		user2_.server = this;

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
	}

	public void updateEntity(String[] args, int entityUpdated) {
		evilUser.updateEntity(args, entityUpdated);
		goodUser.updateEntity(args, entityUpdated);
		log("Updated Entity at location \"" + entityUpdated + "\".");
	}

	public void updatePlayer(String[] args, boolean evil) {
		evilUser.updatePlayer(args, evil);
		goodUser.updatePlayer(args, evil);
		log("Updated \"" + evil + "\" Player.");
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

	private void log(String logInfo) {
		System.out.println(logPath);
	}

	public void play() {
		
		// when Board initializes, they already hand out starting hands, so no need to worry about drawing Cards here
		
		// immediately broadcast who has what
		
		/*
		 * 
	private Deck evilDeck;
	private Deck goodDeck;
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
				switch (evilCommand) {
				case "place1":
					break;
				default:
					break;
				}
			} while (thinking);

			// good turn
			do {
				goodCommand = goodUser.getCommand();
				switch (goodCommand) {
				default:
					break;
				}
			} while (thinking);
			
			// fighting

			// post-turn
			b.endTurn();
		}
	}

	public String getUiType() {
		return uiType;
	}
}
