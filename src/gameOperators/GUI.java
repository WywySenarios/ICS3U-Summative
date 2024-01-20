package gameOperators;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUI extends javax.swing.JFrame implements UserUpdates, ActionListener {

	// Variables declaration
	// GUI measurements related;
	private final String FILEPATH;
	private int width;
	private int height;
	private double segment; // describes the length of 1/8th of the height
	private double variableSegment;

	// Entity related
	private int cardLength; // this actually refers to the Entity's size. XD
	private double laneHeight;

	// Player related
	private int playerWidth;
	private int playerHeight;

	// Inventory related
	private double cardSectionHeight;
	private double cardSize;
	private double cardSpaceSize;
	private double cardSectionX;
	private double evilCardY1;
	private double evilCardY2;
	private double goodCardY1;
	private double goodCardY2;

	// GUI elements;
	private JLabel background = new JLabel();
	private JLabel turnCounter = new JLabel();
	// private JLabel prompt = new JLabel();
	private JLabel evilUsername = new JLabel();
	private JLabel evilPlayerHealth = new JLabel();
	private JLabel evilPlayer = new JLabel();
	private JButton[] evilCards = { new JButton(), new JButton(), new JButton(), new JButton(), new JButton(),
			new JButton(), new JButton(), new JButton(), new JButton(), new JButton() };
	private JButton[] evilEntities = { new JButton(), new JButton(), new JButton(), new JButton(), new JButton() };
	private JLabel goodUsername = new JLabel();
	private JLabel goodPlayerHealth = new JLabel();
	private JLabel goodPlayer = new JLabel();
	private JButton[] goodCards = { new JButton(), new JButton(), new JButton(), new JButton(), new JButton(),
			new JButton(), new JButton(), new JButton(), new JButton(), new JButton() };
	private JButton[] goodEntities = { new JButton(), new JButton(), new JButton(), new JButton(), new JButton() };
	private JTextField developerConsole = new JTextField();
	private boolean developerMode = false;
	private volatile User givenUser;
	private volatile String[] userInputQueue = new String[0];
	// private volatile String[] consoleInputQueue;

	// constants
	private final int DELAY;
	private final char ENDCHAR = '\\';

	// I'm letting Eclipse IDE generate me a serialVersionUID.
	private static final long serialVersionUID = 6998556482722372610L;

	public GUI(User givenUser, boolean developerMode, String FILEPATH_, int DELAY_) {
		this.givenUser = givenUser;

		this.FILEPATH = FILEPATH_;
		this.DELAY = DELAY_;
		this.developerMode = developerMode;
	}

	public void queueInput(String command) {
		String[] temp = userInputQueue;
		userInputQueue = new String[temp.length + 1];

		for (int i = 0; i < temp.length; i++) {
			userInputQueue[i] = temp[i];
		}

		userInputQueue[temp.length] = command;
	}

	public void removeBottomQueue(int numberOfActions) {
		String[] temp = this.userInputQueue;
		this.userInputQueue = new String[this.userInputQueue.length - numberOfActions];

		for (int i = 0; i < this.userInputQueue.length; i++) {
			this.userInputQueue[i] = temp[i + numberOfActions];
		}
	}

	public void removeQueue(int numberOfActions) {
		int numberOfInputs = this.userInputQueue.length - numberOfActions;
		String[] temp = this.userInputQueue;
		this.userInputQueue = new String[numberOfInputs];

		for (int i = 0; i < numberOfInputs; i++) {
			this.userInputQueue[i] = temp[i];
		}
	}

	public void actionPerformed(ActionEvent action) { // in the event that the user presses anything,
		// lastUserInput = action.getActionCommand();
		String command = action.getActionCommand();

		if (this.userInputQueue.length > 2 && this.userInputQueue[this.userInputQueue.length - 2].equals("place")) {
			if (command.substring(0, 5).equals("place")) {
				this.userInputQueue[this.userInputQueue.length - 1] = command.substring(5);
				return;
			} else {
				try {
					Integer.parseInt(command);
					this.queueInput(command);
					return;
				} catch (Exception e) {
					this.removeQueue(2);
				}
			}
		} else {
			if (command.length() > 4 && command.substring(0, 5).equals("place")) {
				this.queueInput("place");
				this.queueInput(command.substring(5));
				return;
			}
		}

		this.queueInput(command);
	}

	// PLEASE, when updating single parts of the screen, RECALCULATE SEGMENT LENGTH

	private void updateLengthValues() {
		double temp = segment;
		this.segment = ((double) this.height) / 8;

		if (temp == segment) { // if no changes were made,
			return;
		}
		/*
		 * the width is made of: 2 segment part for the Good Player the length of the
		 * lanes (variableSegment) 2 segment part for the Evil Player
		 */
		this.variableSegment = this.width - (this.segment * 4);

		// displaying placed Entities
		// all five lanes take up 50% (4/8) of the Height
		// there are 5 lanes, so the lane size is 10% (4/40) of the Height
		// the entity JLabel is 75% of the lane size
		// so the entity JLabel is 7.5% (12/160) of the Height
		this.cardLength = (int) calculateDistance(12.0 / 160);
		laneHeight = calculateDistance(4.0 / 40);

		// displaying Players
		// the Players will take up 60% of their section’s height and 75% of the width.
		// Their name tags and whatnot will take up the remaining 15%, splitting 5
		// percent into empty space, 5% into a name tag, and 5% into health.
		this.playerWidth = (int) (this.segment * (2.0) * 0.6);
		this.playerHeight = (int) (this.segment * (4.0) * 0.75);

		// displaying Player's inventories (AKA Cards)
		/*
		 * There are four spaces in between the Cards and 5 Cards. (That means 5 spaces
		 * + 5 Cards - 1 space).
		 * 
		 * A Card is 3 times bigger than the space (75% occupation)
		 * 
		 * this.variableSegment = Four spaces + five cards = 4 * ⅓ + 5 * 1 Cards =
		 * (19/3) Cards Card = this.variableSegment / (19/3)
		 * 
		 * ALSO, if the height of the cardSectionHeight limits the Card size, then it
		 * limits the Card Size. the Cards are still placed in the same places but are
		 * just smaller to compensate.
		 */
		this.cardSectionHeight = segment * 2 * 0.75;
		this.cardSize = this.variableSegment / (19.0 / 3);
		this.cardSpaceSize = cardSize / 3;
		if (this.cardSize * 2 + this.cardSpaceSize > this.cardSectionHeight) {
			// card + card + space = 3/3 + 3/3 + 1/3
			// = 7.0 / 3
			this.cardSize = this.cardSectionHeight / (7.0 / 3);
			this.cardSpaceSize = this.cardSize / 3;
		}

		/*
		 * x = 2 segments + respective number of past spaces and cards
		 * 
		 * Evil y = space of the cardSectionHeight [AKA 1 segment * 0.25 / 2]; and +
		 * Card length + space if it’s on the second row
		 * 
		 * Good y = evil y + 6 segments
		 * 
		 */

		this.cardSectionX = this.segment * 2;
		this.evilCardY1 = cardSpaceSize;
		this.evilCardY2 = evilCardY1 + cardSize + cardSpaceSize;
		this.goodCardY1 = evilCardY1 + this.segment * 6;
		this.goodCardY2 = evilCardY2 + this.segment * 6;
	}

	private void updateEntireScreen() {
		this.updateLengthValues();
		this.updateAllEntities();
		this.updateTurn();
		this.updateAllPlayers();
		this.updateAllCards();
		this.updateBackground();
		this.updateDev();

		this.pack();
	}

	private void updateEntity(int lane, boolean evil, String command) {
		if (evil) {
			this.evilEntities[lane].setText(null);
			if (givenUser.evilEntities[lane] == null) {
				this.evilEntities[lane].setIcon(null);
			} else {
				this.evilEntities[lane].setIcon(this.scaleImage(
						this.FILEPATH + givenUser.evilEntities[lane].id + "_" + command + ".png", this.cardLength));
			}
		} else {
			this.goodEntities[lane].setText(null);
			if (givenUser.goodEntities[lane] == null) {
				this.goodEntities[lane].setIcon(null);
			} else {
				this.goodEntities[lane].setIcon(this.scaleImage(
						this.FILEPATH + givenUser.goodEntities[lane].id + "_" + command + ".png", this.cardLength));
			}
		}
	}

	private void updateEntity(int lane, boolean evil, String command, String args[]) {
		this.updateEntity(lane, evil, command); // this handles the pictures

		switch (command) {
		case "damage":
			if (evil) {
				this.evilEntities[lane].setText("-" + args[0] + "HP");
			} else {
				this.goodEntities[lane].setText("-" + args[0] + "HP");
			}
			break;
		}
	}

	private void updateAllEntities() {
		int goodX = (int) segment * 2;
		int evilX = (int) (goodX + this.variableSegment - segment); // the extra "- segment" is to counteract stupid
																	// turd swing's way of saying that coordinates are
																	// top-left based

		for (int i = 0; i < 5; i++) {
			this.updateEntity(i, true, "placed");
			this.updateEntity(i, false, "placed");

			/*
			 * FOR THE HEIGHT 12.5% (1/8) of the Height is spent on the evil Player's Cards
			 * for each Card, there is 15% (3/20) of the Height, as calculated previously.
			 */

			this.evilEntities[i].setBounds(evilX, (int) (segment * 2 + laneHeight * i), cardLength, cardLength);
			this.goodEntities[i].setBounds(goodX, (int) (segment * 2 + laneHeight * i), cardLength, cardLength);
		}
	}

	private void updateTurn() {
		int turnCounterLength = (int) (this.segment * 2.0 * 0.75);

		// the turn counter is on the bottom far right.
		// the formula is: (width OR height) - (player area /2) - (turnCounterLength /2)
		this.turnCounter.setText(this.givenUser.gameStatus);
		this.turnCounter.setBounds((int) (this.width - segment * 2.0 / 2 - turnCounterLength / 2.0),
				(int) (this.height - segment * 2.0 / 2 - turnCounterLength / 2.0), turnCounterLength,
				turnCounterLength);
		// this.turnCounter.setVisible(true);
	}

	private void updatePlayer(boolean evil) {
		if (evil) {
			this.evilPlayer.setIcon(this.scaleImage(this.FILEPATH + this.givenUser.evilPlayer.playerID + "_"
					+ this.givenUser.evilPlayer.status + ".png", playerWidth, playerHeight));
			this.evilPlayer.setBounds((int) (this.segment * 2.4 + this.variableSegment), (int) (segment * 2),
					playerWidth, playerHeight);

			this.evilPlayerHealth.setText("" + this.givenUser.evilPlayer.health);
			this.evilPlayerHealth.setBounds((int) (this.segment * 2.4 + this.variableSegment), (int) (segment * 1.8),
					playerWidth, (int) (segment * 0.1));
			
			if (this.givenUser.evil) {
				this.evilUsername.setText("You");
			} else {
				this.evilUsername.setText(this.givenUser.evilPlayer.username);
			}
			this.evilUsername.setBounds((int) (this.segment * 2.4 + this.variableSegment), (int) (segment * 1.6),
					playerWidth, (int) (segment * 0.1));
		} else {
			this.goodPlayer.setIcon(this.scaleImage(this.FILEPATH + this.givenUser.goodPlayer.playerID + "_"
					+ this.givenUser.goodPlayer.status + ".png", playerWidth, playerHeight));
			this.goodPlayer.setBounds((int) (this.segment * 0.2), (int) (segment * 2), playerWidth, playerHeight);

			this.goodPlayerHealth.setText("" + this.givenUser.goodPlayer.health);
			this.goodPlayerHealth.setBounds((int) (this.segment * 0.2), (int) (segment * 1.8), playerWidth,
					(int) (segment * 0.1));
			
			if (this.givenUser.evil) {
				this.goodUsername.setText(this.givenUser.goodPlayer.username);
			} else {
				this.goodUsername.setText("You");
			}
			this.goodUsername.setBounds((int) (this.segment * 0.2), (int) (segment * 1.6),
					playerWidth, (int) (segment * 0.1));
		}
	}

	private void updatePlayer(boolean evil, String command) {
		this.updatePlayer(evil);
		
		if (evil) {
			this.evilPlayerHealth.setText(this.evilPlayerHealth.getText() + command);
		} else {
			this.goodPlayerHealth.setText(this.goodPlayerHealth.getText() + command);
		}
	}
	
	private void updateAllPlayers() {
		this.updatePlayer(true);
		this.updatePlayer(false);
	}

	private void updateCard(boolean evil, int inventoryIndex) {
		JButton givenCard;
		int givenCardSize = (int) this.cardSize;
		if (evil) {
			givenCard = this.evilCards[inventoryIndex];
		} else {
			givenCard = this.goodCards[inventoryIndex];
		}

		try {
			if (evil) {
				givenCard.setIcon(
						scaleImage(this.FILEPATH + givenUser.evilPlayer.inventory[inventoryIndex].id + "_Card.png",
								givenCardSize));
				givenCard.setVisible(true);
			} else {
				givenCard.setIcon(
						scaleImage(this.FILEPATH + givenUser.goodPlayer.inventory[inventoryIndex].id + "_Card.png",
								givenCardSize));
				givenCard.setVisible(true);
			}
		} catch (ArrayIndexOutOfBoundsException e) {

			givenCard.setIcon(null);
			givenCard.setVisible(false);
		} catch (NullPointerException e) {
			givenCard.setIcon(null);
			givenCard.setVisible(false);
		}
	}

	private void updateCard(boolean evil, int inventoryIndex, int x, int y) {
		JButton givenCard;
		int givenCardSize = (int) this.cardSize;
		if (evil) {
			givenCard = this.evilCards[inventoryIndex];
		} else {
			givenCard = this.goodCards[inventoryIndex];
		}

		try {
			if (evil) {
				givenCard.setIcon(
						scaleImage(this.FILEPATH + givenUser.evilPlayer.inventory[inventoryIndex].id + "_Card.png",
								givenCardSize));
				givenCard.setVisible(true);
			} else {
				givenCard.setIcon(
						scaleImage(this.FILEPATH + givenUser.goodPlayer.inventory[inventoryIndex].id + "_Card.png",
								givenCardSize));
				givenCard.setVisible(true);
			}
		} catch (ArrayIndexOutOfBoundsException e) {

			givenCard.setIcon(null);
			givenCard.setVisible(false);
		} catch (NullPointerException e) {
			givenCard.setIcon(null);
			givenCard.setVisible(false);
		}
		givenCard.setBounds(x, y, givenCardSize, givenCardSize);
	}

	private void updateAllCards() {
		for (int i = 0; i < 5; i++) {
			// top row
			updateCard(true, i,
					(int) (this.cardSectionX + (i + 1) * (cardSize + this.cardSpaceSize) - this.cardSpaceSize),
					(int) this.evilCardY1);
			updateCard(false, i,
					(int) (this.cardSectionX + (i + 1) * (cardSize + this.cardSpaceSize) - this.cardSpaceSize),
					(int) this.goodCardY1);

			// bottom row
			updateCard(true, i + 5,
					(int) (this.cardSectionX + (i + 1) * (cardSize + this.cardSpaceSize) - this.cardSpaceSize),
					(int) this.evilCardY2);
			updateCard(false, i + 5,
					(int) (this.cardSectionX + (i + 1) * (cardSize + this.cardSpaceSize) - this.cardSpaceSize),
					(int) this.goodCardY2);
		}

	}

	private void updateBackground() {
		this.background.setBounds(this.getBounds());
		this.background.setIcon(this.scaleImage("backgrounds\\sampleBackground.png", this.width, this.height));
		// this.background.setVisible(true);
	}

	private void updateDev() {
		if (developerMode) {
			// this.developerConsole.setBounds(this.width / 50, this.width / 50, (int)
			// (this.width - this.segment * 2), 0);
			// this.developerConsole.setBounds(0,0,1000,1000);
			this.developerConsole.setBounds((int) (this.segment * 2 + this.variableSegment), 0, this.width / 10,
					this.width / 10);
			this.developerConsole.setText("");
		}

		// this.developerConsole.setVisible(true);
		// this.developerConsole.setVisible(developerMode);
	}

	private double calculateDistance(double percentHeight) { // this calculates the distance based on a fraction of the
																// Height
		return (double) this.height * percentHeight;
	}

	private ImageIcon scaleImage(String path, int length) {
		return new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(length, length, Image.SCALE_DEFAULT));
	}

	private ImageIcon scaleImage(String path, int width, int height) {
		return new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
	}

	private void entityAnimation(int lane, boolean evil, String command) {

		this.updateEntity(lane, evil, command);
		this.pack();

		try {
			Thread.sleep(DELAY);
		} catch (InterruptedException e) {
		}

		this.updateEntity(lane, evil, "placed");
		this.pack();
	}

	private void entityAnimation(int lane, boolean evil, String command, String[] args) {
		this.updateEntity(lane, evil, command, args);
		this.pack();

		try {
			Thread.sleep(DELAY);
		} catch (InterruptedException e) {
		}

		this.updateEntity(lane, evil, "placed");
		this.pack();
	}

	@Override
	public void entityDamage(int lane, boolean evil, String damage) {
		String[] args = { damage };
		this.entityAnimation(lane, evil, "damage", args);
	}

	@Override
	public void entityDeath(int lane, boolean evil) {
		this.entityAnimation(lane, evil, "death");
	}

	@Override
	public void summonEntity(int lane, boolean evil) {
		this.entityAnimation(lane, evil, "spawn");
	}

	@Override
	public void playerDamage(boolean evil, String damage) {
		this.updatePlayer(evil, damage);
	}

	@Override
	public void playerDeath(boolean evil) {
		this.updatePlayer(evil);
	}

	@Override
	public void summonPlayer(boolean evil) {
		this.updatePlayer(evil);

	}

	@Override
	public void gameEnd() {
		// TODO Auto-generated method stub
		this.pack();
	}

	@Override
	public void inventoryRemoveCard(int inventoryIndex, boolean evil) {
		// this means moving all the cards after the given Card. YAY!
		int inventoryLength;
		if (evil) {
			inventoryLength = this.givenUser.evilPlayer.inventory.length;
		} else {
			inventoryLength = this.givenUser.goodPlayer.inventory.length;
		}

		for (int i = inventoryIndex; i < inventoryLength + 1; i++) {
			this.updateCard(evil, i);
		}
		
		this.pack();
	}

	@Override
	public void inventoryAddCard(int inventoryIndex, boolean evil) {
		// we only need to update the very last card---the one that was just drawn
		this.updateCard(evil, inventoryIndex);
		this.pack();
	}

	@Override
	public void pregame() {
		// configure the entire window
		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(true);
		this.setTitle("Wywy's Card Game!");
		// this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		width = 1280;
		height = 720;
		// this.setBounds(0, 0, width, height);
		this.setPreferredSize(new java.awt.Dimension(width, height));
		// this.setPreferredSize(new java.awt.Dimension(width = (3000 /2), height =
		// (2000 /2)));s
		// this.setExtendedState(MAXIMIZED_BOTH);
		getAccessibleContext().setAccessibleDescription("Wywy's Card Game! It's my CS Summative.");
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent componentEvent) {
				// updateEntireScreen();
			}
		});

		// add all elements to GUI
		// this.updateLengthValues();
		// this.updateAllEntities();
		for (int i = 0; i < 5; i++) {
			if (this.givenUser.evil) {
				this.evilEntities[i].setActionCommand("" + i);
				this.evilEntities[i].addActionListener(this);
			} else {
				this.goodEntities[i].setActionCommand("" + i);
				this.goodEntities[i].addActionListener(this);
			}

			this.add(evilEntities[i]);
			this.add(goodEntities[i]);
		}

		// this.updateTurn();
		this.add(this.turnCounter);

		// this.updateAllPlayers();
		this.add(this.evilPlayer);
		this.add(this.goodPlayer);
		this.add(this.evilPlayerHealth);
		this.add(this.goodPlayerHealth);
		this.add(this.evilUsername);
		this.add(this.goodUsername);

		// this.updateAllCards();
		for (int i = 0; i < 10; i++) {
			if (this.givenUser.evil) {
				this.evilCards[i].setActionCommand("place" + i);
				this.evilCards[i].addActionListener(this);
			} else {
				this.goodCards[i].setActionCommand("place" + i);
				this.goodCards[i].addActionListener(this);
			}

			this.add(this.evilCards[i]);
			this.add(this.goodCards[i]);
		}

		// this.updateBackground();
		this.add(this.background);

		// this.updateDev();
		this.add(this.developerConsole);

		// update user information

		this.setVisible(true); // make the game Frame visible!

		// update the GUI with the correct values
		this.updateEntireScreen();
	}

	@Override
	public String getCommand(String message) {
		String output = this.developerConsole.getText();
		boolean stillRunning = true;

		while (stillRunning) {
			try {
				// this is a really messed up code where the loop keeps on waiting variable
				// 50ms and then checking if the developerConsole ends with variable
				// "ENDCHAR"'s value.
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}

			output = this.developerConsole.getText();

			try {
				stillRunning = output.charAt(output.length() - 1) != this.ENDCHAR;
			} catch (StringIndexOutOfBoundsException e) {
			}

			if (this.userInputQueue.length != 0) {
				output = userInputQueue[0];
				this.removeBottomQueue(1);
				return output;
			}
		}

		this.developerConsole.setText("");
		return output.substring(0, output.length() - 1);
	}

}