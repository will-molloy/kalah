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
        currentTurnsPlayer = players.get(0);
    }

    public Move Move(int houseNumber) {
        Move move = validateMove(houseNumber);
        if (!move.equals(VALID)) {
            return move;
        }
        Player currentPlayersHouses = currentTurnsPlayer;
        Plantable plantable = currentPlayersHouses.getHouse(houseNumber);
        int numSeeds = plantable.seedCount();
        plantable.removeSeeds();

        Player nextTurnsPlayer = nextPlayer(currentPlayersHouses);
        while (numSeeds-- > 0) {
            plantable = currentPlayersHouses.getNext(plantable);
            if (plantable instanceof Store) {
                if (currentPlayersHouses.equals(currentTurnsPlayer)) {
                    currentPlayersHouses = nextPlayer(currentPlayersHouses);
                } else {
                    currentPlayersHouses = nextPlayer(currentPlayersHouses);
                    plantable = currentPlayersHouses.getNext(plantable);
                }
            }

            plantable.addSeed();

            if (numSeeds == 0) {
                // Visitor pattern on plantable. visitLastSeed()
                // Return next player (if store -> this else next?)
                // if house -> check if capturable -> capture
                if (plantable instanceof Store) {
                    nextTurnsPlayer = currentTurnsPlayer;
                }
                if (plantable instanceof House) {
                    House house = (House) plantable;
                    House oppositeHouse = nextPlayer(currentPlayersHouses).getOppositeHouse(house);
                    if (currentPlayersHouses.equals(currentTurnsPlayer) && house.canCapture(oppositeHouse)) {
                        currentPlayersHouses.capture(house, oppositeHouse);
                    }
                }
            }
        }
        currentTurnsPlayer = nextTurnsPlayer;
        return validateGameBoard();
    }

    private Move validateMove(int houseNumber) {
        if (currentTurnsPlayer.getHouse(houseNumber).isEmpty()) {
            return EMPTY_HOUSE;
        }
        return VALID;
    }

    private Move validateGameBoard() {
        if (currentTurnsPlayer.housesAreEmpty()) {
            return GAME_OVER;
        }
        return VALID;
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
        for (Player p : players) {
            scores.add(p.getScore());
        }
        return scores;
    }
}
