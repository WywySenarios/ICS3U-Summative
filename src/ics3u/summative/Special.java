package ics3u.summative;

public class Special {
	
	public int charges;
	public int chargeRegen;
	public String sacrificial;
	public Move move;
	public Ability[] abilities;

	public Special(int charges_, int chargeRegen_, String sacrificial_, Move move_, Ability[] abilities_) {
		this.charges = charges_;
		this.chargeRegen = chargeRegen_;
		this.sacrificial = sacrificial_;
		this.move = move_;
		this.abilities = abilities_;
	}

}
