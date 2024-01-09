package Move;

import ics3u.summative.Board;
import ics3u.summative.ChoiceMove;
import ics3u.summative.Entity;
import ics3u.summative.Move;
import ics3u.summative.Player;

public class AttackLeader extends Move implements ChoiceMove {
	public int damage;
	
	public AttackLeader(int damage_, boolean evil_) {
		super("AttackLeader", evil_);
		this.damage = damage_;
	}
	
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
		
		if (selection == 0) { // attack the evil leader
			attackedPlayer = b.evilPlayer;
		} else if (selection == 1) { // attack the good leader
			attackedPlayer = b.goodPlayer;
		} else {
			throw new NullPointerException("Invalid target (the selected target was not 0 or 1)");
		}

		// apply damage
		attackedPlayer.health -= damage;

		// if (attackedPlayer.health < 0) { gameEnd() }
	}
}
