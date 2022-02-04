import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Game game;
    public int playerID;
    public int passToPlayerID;
    PrintWriter writer;


    public ClientHandler(Socket socket, Game game) throws IOException {
        this.socket = socket;
        this.game = game;

    }

    @Override
    public void run() {


        try (
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                Scanner scanner = new Scanner(socket.getInputStream())) {
            try {


                while (true) { // This section of code runs throughout the clients lifetime. The players choices in the client program class pass the commands to the client class,
                    // which then writes to the output stream socket, which is being listened to here.
                    String line = scanner.nextLine();
                    String[] substrings = line.split(" ");

                    switch (substrings[0].toLowerCase()) { // The inputs are set to lowercase so that whatever case the user inputs, it will be the same when running through the program
                        case "new":
                            if (game.getListOfCurrentPlayers().isEmpty()) { //checks whether the game has no current players in it, if there are no players, then the player created will be given the ball
                                playerID = game.getListOfPlayers().size() + 1;
                                game.createPlayer(playerID, true, true);
                            } else {
                                playerID = game.getListOfPlayers().size() + 1;
                                game.createPlayer(playerID, false, true);
                            }
                            writer.println(playerID);
                            System.out.println("A new player joined the Game! Player ID is -  " + playerID);


                            break;

                        case "players":
                            List<Integer> listOfPlayers = game.getListOfCurrentPlayers();
                            writer.println(listOfPlayers.size());
                            for (Integer players : listOfPlayers) {
                                writer.println(players);
                            }
                            break;
                        case "ball":
                            writer.println(game.getPlayerWithBall());
                            break;
                        case "pass":
                            playerID = Integer.parseInt(substrings[1]); //the substrings 1 and 2 are used to determine the playerId and who to pass the ball to. These substrings are split by a space in the string
                            passToPlayerID = Integer.parseInt(substrings[2]);
                            if (game.getPlayerWithBall() == playerID) { //checks whether the player trying to pass the ball actually has the ball
                                if (game.getInGame(passToPlayerID)) { //checks whether the player the ball is being passed to is in game or not
                                    writer.println("Passed ball to " + passToPlayerID);
                                    System.out.println("Player " + playerID + " Passed ball to " + passToPlayerID);
                                    game.passBall(playerID, passToPlayerID);
                                } else {
                                    writer.println("This player is not in the game currently");
                                }

                            } else {
                                writer.println("You do not currently have the ball");
                            }

                            break;

                        case "disconnect":
                            playerID = Integer.parseInt(substrings[1]);
                            System.out.println("Player " + playerID + " disconnected");
                            game.setInGame(playerID, false);
                            boolean passed = false;
                            if (playerID == game.getPlayerWithBall()) {
                                try {
                                    while (!passed) {
                                        for (int i = 0; i <= game.getListOfPlayers().size(); i++) {
                                            if (game.getInGame(i)) {
                                                game.passBall(playerID, i);
                                                passed = true;

                                                writer.println("Passed the ball to player " + i);

                                            }
                                        }

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        default:
                            throw new Exception("Unknown command : " + substrings[0]);
                    }
                }
            } catch (Exception e) {
                writer.println(("ERROR" + e.getMessage()));
                socket.close();
            }


        } catch (Exception e) {

        } finally { //this finally sections is run when the player disconnects by closing the program rather than inputting the disconnect commands. The code itself is the same.
            System.out.println("Player " + playerID + " disconnected");
            game.setInGame(playerID, false);
            boolean passed = false;
            if (playerID == game.getPlayerWithBall()) {
                try {
                    while (!passed) {
                        for (int i = 0; i <= game.getListOfPlayers().size(); i++) {
                            if (game.getInGame(i)) {
                                game.passBall(playerID, i);
                                System.out.println("Player " + playerID + " passed the ball to Player " + i);
                                passed = true;
                            }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public PrintWriter getOut() {
        return writer;
    }


}









