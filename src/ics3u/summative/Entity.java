package ics3u.summative;

public class Entity {

	public int health;
	public int hpr;
	public int attackModifier;
	public int shield;
	public boolean aggressive;
	public String[] statusEffects = new String[0];
	//public int[] statusPotency = new int[0];
	public Move[] moves;
	public Ability[] abilities;
	
	public Entity(int health_, int hpr_, int shield_, boolean aggressive_, Move[] moves_, Ability[] abilities_) {
		this.health = health_;
		this.hpr = hpr_;
		this.attackModifier = 1;
		this.aggressive = aggressive_;
		this.moves = moves_;
		this.abilities = abilities_;
	}
	
	public boolean isAlive() {
		if (health > 0) {
			return true;
		} else {
			return false;
		}
	}

}
