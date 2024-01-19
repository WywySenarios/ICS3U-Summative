package Move;

import gameElements.Board;
import gameElements.ChoiceMove;
import gameElements.Entity;
import gameElements.Move;

public class AttackDirect extends Move implements ChoiceMove {
	public int damage;
	public String[] statusEffects;

	public AttackDirect(int damage_, String[] statusEffects_, boolean evil_) {
		super("AttackDirect", evil_);
		this.damage = damage_;
		this.statusEffects = statusEffects_;
	}

	@Override
	public Object duplicate() {
		// duplicate statusEffects
		String[] outputStatusEffects = new String[this.statusEffects.length];
		int currentIndex = 0;
		for (String i : this.statusEffects) {
			outputStatusEffects[currentIndex++] = i;
		}

		return new AttackDirect(this.damage, outputStatusEffects, super.evil);
	}

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

		Entity attackedEntity;

		if (this.evil) { // if the attacker is EVIL,
			attackedEntity = b.goodEntities[selection];
		} else { // if the attacker is GOOD,
			attackedEntity = b.evilEntities[selection];
		}

		if (attackedEntity == null) { // if the given lane is empty,
			if (this.evil) {
				new AttackLeader(this.damage, this.evil).move(attacker, b, 1);
			} else {
				new AttackLeader(this.damage, this.evil).move(attacker, b, 0);
			}
		} else { // if the given lane is NOT empty,
			
			int healthChange = attackedEntity.health;
			
			// apply damage
			attackedEntity.receiveDamage(attacker, damage);
			
			healthChange -= attackedEntity.health;
			//healthChange *= -1;

			// add status effects if necessary
			if (statusEffects != null) {
				int tempIndex = 0;
				String[] temp = new String[attackedEntity.statusEffects.length + statusEffects.length];
				for (String i : attackedEntity.statusEffects) {
					temp[tempIndex++] = i;
				}

				for (String i : statusEffects) {
					temp[tempIndex++] = i;
				}

				attackedEntity.statusEffects = temp;
			}

			String[] args = { "damage" , "" + healthChange};
			b.server.updateEntity(args, selection, ! this.evil);
		}
	}
}
