package ics3u.summative;

public class AttackLane implements ChoiceMove {
	public int damage;
	public String[] statusEffects;
	public boolean evil;

	public AttackLane(int damage_, String[] statusEffects_, boolean evil_) {
		this.damage = damage_;
		this.statusEffects = statusEffects_;
		this.evil = evil_;
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
		
		try {
			if (this.evil) { // if the target is good,
				new AttackTarget(this.damage, this.statusEffects, this.evil).move(attacker, b, selection + 5);
			} else { // if the target is evil,
				new AttackTarget(this.damage, this.statusEffects, this.evil).move(attacker, b, selection);
			}
		} catch (java.lang.NullPointerException e) {
		}
	}
}
