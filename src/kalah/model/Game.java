package kalah.model;

import java.util.ArrayList;
import java.util.List;

import static kalah.model.Move.*;

public class Game {

    private final List<Player> players;
    private final int numHouses;
    private final int numInitialSeeds;
    private Player currentTurnsPlayer;

    public Game(int numHouses, int numInitialSeeds, int numPlayers) {
        this.players = new ArrayList<>(numPlayers);
        this.numHouses = numHouses;
        this.numInitialSeeds = numInitialSeeds;
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player(i + 1, numHouses, numInitialSeeds));
        }
        currentTurnsPlayer = getPlayer(1);
    }

    public Move Move(int houseNumber) {
        Move move = validateMove(houseNumber);
        if (!move.equals(VALID)){
            return move;
        }
        Player player = currentTurnsPlayer;
        Plantable plantable = player.getHouse(houseNumber);
        int numSeeds = plantable.seedCount();
        plantable.removeSeeds();

        Player nextTurnsPlayer = nextPlayer(player);
        while (numSeeds-- > 0) {
            plantable = player.getNext(plantable);
            if (plantable instanceof Store){
                if (!player.equals(currentTurnsPlayer)){
                    player = nextPlayer(player);
                    plantable = player.getNext(plantable);
                } else {
                    player = nextPlayer(player);
                }
            }

            plantable.addSeed();

            if (numSeeds == 0){
                if (plantable instanceof Store){
                    nextTurnsPlayer = currentTurnsPlayer;
                }
                if (plantable instanceof House){
                    House house = (House)plantable;
                    House oppositeHouse = nextPlayer(player).getOppositeHouse(house);
                    if (player.equals(currentTurnsPlayer) && isCapturable(house, oppositeHouse)){
                        player.capture(house, oppositeHouse);
                    }
                }
            }
        }
        currentTurnsPlayer = nextTurnsPlayer;
        return validateGameBoard();
    }

    private Move validateMove(int houseNumber){
        if (currentTurnsPlayer.getHouse(houseNumber).seedCount() == 0){
            return EMPTY_HOUSE;
        }
        return VALID;
    }

    private Move validateGameBoard(){
        if (currentTurnsPlayer.housesAreEmpty()){
            return GAME_OVER;
        }
        return VALID;
    }

    private boolean isCapturable(House house, House oppositeHouse){
        return house.seedCount() == 1 && oppositeHouse.seedCount() > 0;
    }

    private Player nextPlayer(Player player) {
        return players.get(player.getPlayerNumber() % players.size());
    }

    public Player getPlayer(int playerNumber) {
        for (Player p : players) {
            if (p.getPlayerNumber() == playerNumber) {
                return p;
            }
        }
        throw new RuntimeException("Player not found:  " + playerNumber);
    }

    public int getNumHouses() {
        return numHouses;
    }

    public int getNumInitialSeeds() {
        return numInitialSeeds;
    }

    public int getNumPlayers() {
        return players.size();
    }

    public Player getCurrentTurnsPlayer() {
        return currentTurnsPlayer;
    }

    public List<Score> getScores() {
        List<Score> scores = new ArrayList<>();
        for (Player p : players){
            scores.add(new Score(p.getPlayerNumber(), p.seedCount()));
        }
        return scores;
    }
}
