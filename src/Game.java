
import java.util.*;

public class Game {

    private final TreeMap<Integer, Player> players = new TreeMap<>();

    //The functions below are what is being called by the ClientHandler class to handle the actions in the game.

    public void createPlayer(int playerID, boolean hasBall, boolean inGame) {
        Player player = new Player(playerID, hasBall, inGame);
        player.setHasBall(hasBall);
        players.put(playerID, player);
    }


    public int getPlayerWithBall() {
        int playerWithBall = 0;

        for (Player player : players.values()) {
            if (player.getHasBall()) {
                playerWithBall = player.getPlayerID();
            }
        }
        return playerWithBall;
    }

    public List<Integer> getListOfPlayers() {
        List<Integer> result = new ArrayList<Integer>();

        for (Player player : players.values()) {
            result.add(player.getPlayerID());
        }

        return result;
    }

    public List<Integer> getListOfCurrentPlayers() {
        List<Integer> result = new ArrayList<Integer>();

        for (Player player : players.values()) {
            if (player.getInGame()) {
                result.add(player.getPlayerID());
            }
        }
        return result;
    }

    public void passBall(int PlayerID, int targetPlayerID) throws Exception {
        synchronized ((players)) {
            if (players.get(PlayerID).getHasBall()) {
                players.get(PlayerID).setHasBall(false);
                players.get(targetPlayerID).setHasBall(true);

            }
        }

    }

    public void setInGame(int playerID, boolean inGame2) {
        players.get(playerID).setInGame(inGame2);
    }

    public boolean getInGame(int playerID) {
        if (getListOfPlayers().contains(playerID)) {
            return players.get(playerID).getInGame();
        } else {
            return false;
        }
    }


}
