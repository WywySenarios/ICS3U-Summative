package gameOperators;

public interface UI {
void updateEntity(String[] args, int entityUpdated, boolean evil);
void updatePlayer(String[] args, boolean evil);
void updateGameStatus(String[] args);
}
