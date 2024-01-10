package gameElements;

public interface HasAbility {
public void triggerAbilities(String command);
public void triggerAbilities(String command, Entity e);
public void triggerAbilities(String command, Entity e, int potency);
}
