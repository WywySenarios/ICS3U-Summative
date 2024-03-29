package Move;

import gameElements.Board;
import gameElements.ChoiceMove;
import gameElements.Entity;
import gameElements.Move;
import gameElements.Player;

/**
 * Attacks a Player.
 * @author pc
 */
public class AttackLeader extends Move implements ChoiceMove {

    /**
     *
     */
    public int damage;

    /**
     *
     * @param damage_
     * @param evil_
     */
    public AttackLeader(int damage_, boolean evil_) {
		super("AttackLeader", evil_);
		this.damage = damage_;
	}

    /**
     *
     * @return
     */
    @Override
	public Object duplicate() {
		return new AttackLeader(this.damage, super.evil);
	}

    /**
     *
     * @param attacker
     * @param b
     * @param selection
     */
    @Override
	public void move(Entity attacker, Board b, int selection) {

		/*
		 * Attacking an entity consist of two primary parts: 1) damage 2) adding new
		 * status effects
		 * 
		 * Remember that REGENERATION, ENVIRONMENTAL HAZARDS, and STATUS EFFECTS only
		 * apply AFTER BOTH TURNS REMEMBER THAT NEW STATUS EFFECTS DON'T TAKE EFFECT YET
		 * Remember that STATUS EFFECTS don't affect PLAYERS Remember that KILLING
		 * ENTITIES is NOT a part of this method's functionality.
		 */

		Player attackedPlayer;
		boolean evil; // this variable is for knowing which Player to broadcast to update.

		if (selection == 0) { // attack the evil leader
			attackedPlayer = b.evilPlayer;
			evil = true;
		} else if (selection == 1) { // attack the good leader
			attackedPlayer = b.goodPlayer;
			evil = false;
		} else {
			throw new NullPointerException("Invalid target (the selected target was not 0 or 1)");
		}

		// apply damage
		attackedPlayer.health -= damage;

		// broadcast damage
		String[] args = { "damage", "" + damage };
		b.server.updatePlayer(args, evil);

		if (attackedPlayer.health <= 0) {
			args = new String[2];
			args[0] = "end";

			// args[1] is the String value for who won.
			if (evil) {
				args[1] = "false";
			} else {
				args[1] = "true";
			}
			b.server.updateGameStatus(args);
		}
	}
}
