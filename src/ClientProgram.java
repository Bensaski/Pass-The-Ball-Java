import java.io.PrintWriter;
import java.util.*;


public class ClientProgram {


    public static boolean started;
    static PrintWriter writer = new PrintWriter(System.out, true);
    static int playerID;
    static Client client;
    static boolean joining = false;
    //static int noOfPlayers;
    static Thread myThread = new myThread();
    static int previousBall;
    static int previousPlayers;

    public static void main(String[] args) {


        String line;
        // myThread updateThread = new myThread();


        try {

            Scanner scanner = new Scanner(System.in);


            {
                try {
                    client = new Client(); //When the program is started by the user, a new client class is created for this class to use its functions
                    playerID = client.newPlayer(); // a new player is created and then given a new player ID
                    writer.println("\nWelcome to the game! Your are Player " + playerID + "\n");

                    while (true) { //this while loop is running throughout the lifetime of the program for the user to input commands, the user inputs the commands and then the relevant commands run the function in the Client class
                        started = true;
                        previousBall = client.getPlayerWithBall();
                        previousPlayers = client.getListOfCurrentPlayers().length;

                        if (!joining) {
                            writer.println("The current players in the game - " + Arrays.toString(client.getListOfCurrentPlayers()) + "\n");
                            writer.println("Player " + client.getPlayerWithBall() + " currently has the ball!");
                            if(!myThread.isAlive()) {
                                myThread.start();
                            }
                            joining = true;
                        }
                        if (playerID == client.getPlayerWithBall()) {
                            System.out.println("-    Type PASS to pass the ball to another player\n-    Type PLAYERS to see current list of players\n-    Type BALL to see who has the ball\n-    Type DISCONNECT to disconnect from the game\n");
                        } else {
                            System.out.println("-    Type PLAYERS to see current list of players\n-    Type BALL to see who has the ball\n-    Type DISCONNECT to disconnect from the game\n");

                        }



                        line = scanner.nextLine(); // gets the input from the user
                        line = line.toLowerCase(Locale.ROOT); //sets the input to lowercase

                        if (line.equals("players")) {

                            writer.println("The players in game are : " + Arrays.toString(client.getListOfCurrentPlayers()));
                        }
                        if (line.equals("pass")) {
                            if (playerID == client.getPlayerWithBall()) {

                                writer.println("Who would you like to pass the ball to? Players - " + Arrays.toString(client.getListOfCurrentPlayers()) + " \n");
                                String input = scanner.nextLine();
                                int targetPlayerID = Integer.parseInt(input);

                                writer.println(client.passBall(playerID, targetPlayerID));
                            } else {
                                writer.println("You do not currently have the ball\n");
                            }

                        }

                        if (line.equals("ball")) {
                            if (!(playerID == client.getPlayerWithBall())) {


                                writer.println("Player " + client.getPlayerWithBall() + " currently has the ball!\n");

                            }else{
                                writer.println("You currently have the ball!\n");
                            }
                        }

                        if (line.equals("disconnect")) {
                            writer.println("Goodbye!");
                            //d writer.println(client.Disconnect(playerID));
                            System.exit(0);

                        }


                    }
                } catch (Exception e) {
                    writer.println(("ERROR" + e.getMessage()));

                }
            }


        } finally {
            writer.println(client.Disconnect(playerID));

        }

    }

    public PrintWriter getOut() {
        return writer;
    }
//This thread runs throughout the program. It checks every second to see if any changes have happened to  the game. If it has, then write a message with the update
    public static class myThread extends Thread {
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (previousBall != client.getPlayerWithBall()) {
                    writer.println("The ball was passed to Player " + client.getPlayerWithBall());
                    previousBall = client.getPlayerWithBall();

                }

                if(previousPlayers != client.getListOfCurrentPlayers().length){
                    if(previousPlayers < client.getListOfCurrentPlayers().length){
                        writer.println("A player joined the game! The current players in the game are - "+ Arrays.toString(client.getListOfCurrentPlayers()));
                        previousPlayers = client.getListOfCurrentPlayers().length;
                    }else if(previousPlayers > client.getListOfCurrentPlayers().length){
                        writer.println("A player left the game! The current players in the game are - " + Arrays.toString(client.getListOfCurrentPlayers()));
                        previousPlayers = client.getListOfCurrentPlayers().length;
                    }
                }
            }
        }
    }



}













