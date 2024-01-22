package gameElements;

/**
 *
 * @author pc
 */
public abstract class Move implements Duplicable {

    /**
     * This stores what type of Move this Object holds, NOT WHAT TYPE IT IS. For example, this can store "AttackDirect", but it CANNOT store "wywy".
     */
    public String type;

    /**
     *
     */
    public boolean evil;
	
    /**
     *
     * @param type_
     * @param evil_
     */
    public Move(String type_, boolean evil_) {
		this.type = type_;
		this.evil = evil_;
	}
	
    /**
     *
     * @return
     */
    public String toString() {
		return type;
	}
	
}
