package gameOperators;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import gameElements.Card;

public class GUI extends javax.swing.JFrame implements UserUpdates {

	// Variables declaration
	private int width;
	private int height;
	private double segment; // describes the length of 1/8th of the height
	private double variableSegment;
	private JLabel background = new JLabel();
	private JLabel turnCounter = new JLabel();
	private JLabel prompt = new JLabel();
	private JLabel evilPlayerHealth = new JLabel();
	private JLabel evilPlayer = new JLabel();
	private JButton[] evilCards;
	private JButton[] evilEntities = new JButton[5];
	private JLabel goodPlayerHealth = new JLabel();
	private JLabel goodPlayer = new JLabel();
	private JButton[] goodCards;
	private JButton[] goodEntities = new JButton[5];
	private JTextField developerConsole = new JTextField();
	private boolean developerMode = false;
	private User givenUser;
	private volatile String lastUserInput;
	private final int DELAY = 3;

	// I'm letting Eclipse IDE generate me a serialVersionUID.
	private static final long serialVersionUID = 6998556482722372610L;

	/**
	 * Creates new form GUI
	 */
	public GUI(User user_, boolean developerMode) {
		this.givenUser = user_;

		for (int i = 0; i < 5; i++) {
			evilEntities[i] = new JButton();
			goodEntities[i] = new JButton();
		}
		
		this.developerMode = developerMode;
	}

	public void actionPerformed(ActionEvent action) { // in the event that the user presses anything,
		lastUserInput = action.getActionCommand();

		switch (action.getActionCommand()) {

		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	public void initComponents() {
		/*
		 * goodPlayerNameLabel.setText("You");
		 * 
		 * jLabel4.setText("The ENEMY");
		 * 
		 * jLabel6.setText("jLabel6");
		 * 
		 * javax.swing.GroupLayout layout = new
		 * javax.swing.GroupLayout(getContentPane());
		 * getContentPane().setLayout(layout);
		 * layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.
		 * Alignment.LEADING)
		 * .addGroup(layout.createSequentialGroup().addContainerGap().addComponent(
		 * goodPlayerNameLabel)
		 * .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
		 * javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		 * .addComponent(jLabel4).addContainerGap())
		 * .addGroup(layout.createSequentialGroup().addGap(187, 187,
		 * 187).addComponent(jLabel5) .addContainerGap(113, Short.MAX_VALUE))
		 * .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
		 * layout.createSequentialGroup()
		 * .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		 * .addComponent(jLabel6).addGap(62, 62, 62)));
		 * layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.
		 * Alignment.LEADING) .addGroup(layout.createSequentialGroup().addGap(16, 16,
		 * 16) .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.
		 * BASELINE) .addComponent(goodPlayerNameLabel).addComponent(jLabel4))
		 * .addGap(39, 39, 39).addComponent(jLabel5)
		 * .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).
		 * addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 80,
		 * javax.swing.GroupLayout.PREFERRED_SIZE) .addContainerGap(43,
		 * Short.MAX_VALUE)));
		 */
		pack();
	}

	// PLEASE, when updating single parts of the screen, RECALCULATE SEGMENT LENGTH

	private void updateLengthValues() {
		this.segment = ((double) this.height) / 8;
		System.out.println(segment);
		/*
		 * the width is made of: 2 segment part for the Good Player the length of the
		 * lanes (variableSegment) 2 segment part for the Evil Player
		 */
		this.variableSegment = this.width - (this.segment * 4);
		System.out.println(variableSegment);
	}

	// PLEASE, when updating single parts of the screen, RECALCULATE SEGMENT LENGTH

	private void updateEntireScreen() {
		this.updateLengthValues();
		this.updateAllEntities();
		this.updateTurn();
		this.updateAllPlayers();
		this.updateAllCards();
		this.updateDev();

		this.pack();
	}

	// PLEASE, when updating single parts of the screen, RECALCULATE SEGMENT LENGTH

	private void updateAllEntities() {
		// all five lanes take up 75% (3/4) of the Height
		// there are 5 lanes, so the lane size is 15% (3/20) of the Height
		// the entity JLabel is 75% of the lane size
		// so the entity JLabel is 11.25% (9/80) of the Height
		double laneHeight = calculateDistance(3.0 / 20);
		int cardLength = (int) calculateDistance(9.0 / 80);

		int x = (int) segment * 2;

		// PRAY TO FUCKING GOD “i” IS A REFERENCE TO THE JLABELS OF THIS CLASS
		for (int i = 0; i < 5; i++) {

			try {
				this.evilEntities[i].setText(givenUser.evilEntities[i].toString());
			} catch (NullPointerException e) {
				this.evilEntities[i].setText(null);
			}

			try {
				this.goodEntities[i].setText(givenUser.goodEntities[i].toString());
			} catch (NullPointerException e) {
				this.goodEntities[i].setText(null);
			}

			/*
			 * FOR THE HEIGHT 12.5% (1/8) of the Height is spent on the evil Player's Cards
			 * for each Card, there is 15% (3/20) of the Height, as calculated previously.
			 */

			this.evilEntities[i].setBounds(x, (int) calculateDistance(1.0 / 8 + laneHeight), cardLength, cardLength);
			this.goodEntities[i].setBounds(x, (int) (segment * 2 + laneHeight * i), cardLength, cardLength);
		}
	}

	// PLEASE, when updating single parts of the screen, RECALCULATE SEGMENT LENGTH

	private void updateTurn() {
	}

	// PLEASE, when updating single parts of the screen, RECALCULATE SEGMENT LENGTH

	private void updateAllPlayers() {
	}

	// PLEASE, when updating single parts of the screen, RECALCULATE SEGMENT LENGTH

	private void updateAllCards() {
	}

	private void updateDev() {
		if (developerMode) {
			System.out.println((int) (this.segment * 2 + this.variableSegment));
			//this.developerConsole.setBounds(this.width / 50, this.width / 50, (int) (this.width - this.segment * 2), 0);
			//this.developerConsole.setBounds(0,0,1000,1000);
			this.developerConsole.setBounds((int) (this.segment * 2 + this.variableSegment) / 3, 0, this.width / 10, this.width / 10);
			this.developerConsole.setText("");
		}
		
		this.developerConsole.setVisible(true);
		//this.developerConsole.setVisible(developerMode);
	}

	private double calculateDistance(double percentHeight) { // this calculates the distance based on a fraction of the
																// Height
		return (double) this.height * percentHeight;
	}

	private void setBounds(JLabel givenObject, double percentHeightx, double percentHeighty) {
		givenObject.setBounds((int) this.calculateDistance(percentHeightx),
				(int) this.calculateDistance(percentHeighty), givenObject.getWidth(), givenObject.getHeight());
	}

	@Override
	public void entityDamage(int lane, boolean evil, int damage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void entityDeath(int lane, boolean evil) {
		// TODO Auto-generated method stub

	}

	@Override
	public void summonEntity(int lane, boolean evil) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playerDamage(boolean evil, int damage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playerDeath(boolean evil) {
		// TODO Auto-generated method stub

	}

	@Override
	public void summonPlayer(boolean evil) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gameEnd() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inventoryRemoveCard(Card givenCard) {
		// TODO Auto-generated method stub

	}

	@Override
	public void inventoryAddCard(Card givenCard) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pregame() {
		// configure the entire window
		this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(true);
		this.setTitle("Wywy's Card Game!");
		//this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		width = 1500;
				height = 1000;
		this.setBounds(0, 0, width, height);
		this.setPreferredSize(new java.awt.Dimension(width, height));
		System.out.println(this.getBounds());
		//this.setPreferredSize(new java.awt.Dimension(width = (3000 /2), height = (2000 /2)));
		//this.setExtendedState(MAXIMIZED_BOTH);
		getAccessibleContext().setAccessibleDescription("Wywy's Card Game! It's my CS Summative.");
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent componentEvent) {
				//updateEntireScreen();
			}
		});

		// ;lkasdf
		this.updateLengthValues();
		this.updateAllEntities();
		for (int i = 0; i < 5; i++) {
			this.add(evilEntities[i]);
			this.add(goodEntities[i]);
		}
		this.updateTurn();
		this.updateAllPlayers();
		this.updateAllCards();
		this.updateDev();
		this.add(this.developerConsole);

		// update user information

		this.setVisible(true); // make the game Frame visible!

		this.updateEntireScreen();
	}

	@Override
	public String getCommand(String message) {
		try {
			Thread.sleep(DELAY);
		} catch (InterruptedException e) {
		}
		return null;
	}

}