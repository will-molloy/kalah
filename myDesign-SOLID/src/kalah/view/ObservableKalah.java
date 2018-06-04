package kalah.view;

import kalah.model.House;
import kalah.model.Score;
import kalah.model.Store;

import java.util.List;

public interface ObservableKalah {

    int numPlayers();

    List<House> getHouses(int playerNum);

    Store getStore(int playerNum);

    int currentTurnsPlayer();

    List<Score> getScores();

}
