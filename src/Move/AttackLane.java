package Move;

import gameElements.Board;
import gameElements.ChoiceMove;
import gameElements.Entity;
import gameElements.Move;

/**
 *
 * @author pc
 */
public class AttackLane extends Move implements ChoiceMove {

    /**
     *
     */
    public int damage;

    /**
     *
     */
    public String[] statusEffects;

    /**
     *
     * @param damage_
     * @param statusEffects_
     * @param evil_
     */
    public AttackLane(int damage_, String[] statusEffects_, boolean evil_) {
		super("AttackLane", evil_);
		this.damage = damage_;
		this.statusEffects = statusEffects_;
		this.evil = evil_;
	}

    /**
     *
     * @return
     */
    @Override
	public Object duplicate() {
		// duplicate statusEffects
		String[] outputStatusEffects = new String[this.statusEffects.length];
		int currentIndex = 0;
		for (String i : this.statusEffects) {
			outputStatusEffects[currentIndex++] = i;
		}

		return new AttackLane(this.damage, this.statusEffects, super.evil);
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

		try {
			if (this.evil) { // if the target is good,
				new AttackTarget(this.damage, this.statusEffects, this.evil).move(attacker, b, selection);
			} else { // if the target is evil,
				new AttackTarget(this.damage, this.statusEffects, this.evil).move(attacker, b, selection);
			}
		} catch (java.lang.NullPointerException e) {
		}
	}
}
