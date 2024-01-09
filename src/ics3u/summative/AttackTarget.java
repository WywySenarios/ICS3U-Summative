package ics3u.summative;

public class AttackTarget extends Move implements ChoiceMove {
	public int damage;
	public String[] statusEffects;
	
	public AttackTarget(int damage_, String[] statusEffects_, boolean evil_) {
		super("AttackTarget", evil_);
		this.damage = damage_;
		this.statusEffects = statusEffects_;
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
		if (selection <= 0) {
			throw new NullPointerException("Invalid target, (the selected target was 0-)");
		} else if (selection < 5) { // attack a evil unit
			if (b.evilEntities[selection] != null) { // if there is a valid target,
				new AttackDirect(this.damage, this.statusEffects, this.evil).move(attacker, b, selection);
			}
		} else if (selection < 10) { // attack a good unit
			if (b.goodEntities[selection] != null) { // if there is a valid target,
				new AttackDirect(this.damage, this.statusEffects, this.evil).move(attacker, b, selection - 5);
			}
		} else if (selection == 11) { // attack the evil leader
			new AttackLeader(this.damage, this.evil).move(attacker, b, 0);
		} else if (selection == 12) { // attack good leader
			new AttackLeader(this.damage, this.evil).move(attacker, b, 1);
		} else {
			throw new NullPointerException("Invalid target (the selected target was 13+)");
		}
	}
}
