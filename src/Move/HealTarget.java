package Move;

import gameElements.Board;
import gameElements.ChoiceMove;
import gameElements.Entity;
import gameElements.Move;

/**
 *
 * @author pc
 */
public class HealTarget extends Move implements ChoiceMove {

    /**
     *
     */
    public int health;

    /**
     *
     */
    public int target;
	
    /**
     *
     * @param health_
     * @param target_
     * @param evil_
     */
    public HealTarget(int health_, int target_, boolean evil_) {
		super("HealTarget", evil_);
		this.health = health_;
		this.target = target_;
	}

    /**
     *
     * @return
     */
    @Override
	public Object duplicate() {
		// TODO Auto-generated method stub
		return new HealTarget(this.health, this.target, this.evil);
	}

    /**
     *
     * @param attacker
     * @param b
     * @param selection
     */
    @Override
	public void move(Entity attacker, Board b, int selection) {
		String[] args = {"damage", "" + (this.health * -1)};
		
		if (target < 0) {
			throw new NullPointerException("Invalid target, (the selected target was smaller than 0)");
		} else if (target < 5) { // heal an Entity
			if (this.evil) { // heal an evil unit
				if (b.evilEntities[target] != null) { // if there is a valid target,
					b.evilEntities[target].health += this.health;
					b.server.updateEntity(args, target, evil);
				}
			} else { // heal a good unit
				if (b.goodEntities[target] != null) { // if there is a valid target,
					b.goodEntities[target].health += this.health;
					b.server.updateEntity(args, target, evil);
				}
			}
		} else if (target == 5) { // heal a leader
			if (this.evil) { // heal the evil leader
				b.evilPlayer.health += this.health;
				b.server.updatePlayer(args, evil);
			} else { // heal the good leader
				b.goodPlayer.health += this.health;
				b.server.updatePlayer(args, evil);
			}
		} else {
			throw new NullPointerException("Invalid target (the selected target was 6+)");
		}
	}

}
