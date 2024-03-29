package Move;

import gameElements.Board;
import gameElements.ChoiceMove;
import gameElements.Entity;
import gameElements.Move;

/**
 * Attacks a specific, opposing target.
 * @author pc
 */
public class AttackTarget extends Move implements ChoiceMove {

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
    public AttackTarget(int damage_, String[] statusEffects_, boolean evil_) {
		super("AttackTarget", evil_);
		this.damage = damage_;
		this.statusEffects = statusEffects_;
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

		return new AttackTarget(this.damage, outputStatusEffects, super.evil);
	}

    /**
     *
     * @param attacker
     * @param b
     * @param selection denotes the target of this attack. Selections between 0--4 inclusive target lanes while a 5 will target a Player.
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
		if (selection <= 0) {
			throw new NullPointerException("Invalid target, (the selected target was 0-)");
		} else if (selection < 5) { // attack an Entity
			if (this.evil) { // attack a good unit
				if (b.goodEntities[selection] != null) { // if there is a valid target,
					new AttackDirect(this.damage, this.statusEffects, this.evil).move(attacker, b, selection);
				}
			} else { // attack an evil unit
				if (b.evilEntities[selection] != null) { // if there is a valid target,
					new AttackDirect(this.damage, this.statusEffects, this.evil).move(attacker, b, selection);
				}
			}
		} else if (selection == 5) { // attack a leader
			if (this.evil) { // attack the good leader
				new AttackLeader(this.damage, this.evil).move(attacker, b, 1);
			} else { // attack the evil leader
				new AttackLeader(this.damage, this.evil).move(attacker, b, 0);
			}
		} else {
			throw new NullPointerException("Invalid target (the selected target was 6+)");
		}
	}
}
