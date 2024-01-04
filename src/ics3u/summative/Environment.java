package ics3u.summative;

public class Environment {
	
	public Move[] moves;
	public Ability[] abilities;
	public final boolean PERMANENT;

	public Environment(Move[] moves_, Ability[] abilities_, boolean PERMANENT_) {
		this.moves = moves_;
		this.abilities = abilities_;
		this.PERMANENT = PERMANENT_;
	}

}
