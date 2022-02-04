public class Player {
    public boolean hasBall = false;
    private final int playerID;
    public boolean inGame;

    public Player(int playerID, boolean hasBall, boolean inGame) {
        this.hasBall = hasBall;
        this.playerID = playerID;
        this.inGame = inGame;
    }

    public int getPlayerID() {
        return playerID;
    }

    public boolean getHasBall() {
        return hasBall;
    }

    public void setHasBall(boolean hasBall2) {
        hasBall = hasBall2;
    }

    public void setInGame(boolean inGame2) {
        inGame = inGame2;
    }

    public boolean getInGame() {
        return inGame;
    }
}
