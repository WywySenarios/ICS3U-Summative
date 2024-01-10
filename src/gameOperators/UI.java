package gameOperators;

public interface UI {
void updateEntity(String[] args, int entityUpdated);
void updatePlayer(String[] args, boolean evil);
void updateGameStatus(String[] args);
}
