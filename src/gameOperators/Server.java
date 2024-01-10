package gameOperators;

import gameElements.Board;

public class Server implements UI {

	private String uiType;
	private String logPath;
	private User evilUser;
	private User goodUser;
	protected Board b;
	private String gameStatus;

	public Server(String uiType_, User user1_, User user2_, String evilDeckPath, String goodDeckPath, String logPath_) throws Exception {
		this.uiType = uiType_;
		this.logPath = logPath_;
		
		this.b = new Board(true, evilDeckPath, goodDeckPath, "evilPlayerUsername", "stickmanLeader",
				"goodPlayerUsername", "wywyLeader");
		if(! this.b.addServer(this)) {
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
		while (! gameStatus.equals("end")) {
			// pre-turn
			b.preturns();
			
			// evil turn
			switch (evilUser.getCommand()) {
			default:
				break;
			}
			
			// good turn
			switch (goodUser.getCommand()) {
			default:
				break;
			}
			
			// post-turn
			b.endTurn();
		}
	}
	
	public String getUiType() {
		return uiType;
	}
}
