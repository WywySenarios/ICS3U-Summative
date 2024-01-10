package gameElements;

public abstract class Move {

	public String type;
	public boolean evil;
	
	public Move(String type_, boolean evil_) {
		this.type = type_;
		this.evil = evil_;
	}
	
}
