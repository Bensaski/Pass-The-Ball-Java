import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final static int port = 1234;
    private static final Game game = new Game();
    //public static final List<Socket> clients = new ArrayList<>();
    //private static final ArrayList<ClientHandler> players = new ArrayList<>();
    //static PrintWriter server = new PrintWriter(System.out, true);

    public static void main(String[] args) {

        RunServer();

    }


    private static void RunServer() {

        ServerSocket serverSocket;

        try {

            serverSocket = new ServerSocket(port);
            System.out.println("Waiting for connections..");
            while (true) {
                Socket socket = serverSocket.accept(); //When the ClientProgram is run, the server accepts the connection request and creates a new ClientHandler as well as a new thread to handle it.
                ClientHandler client = new ClientHandler(socket,game);
                new Thread(client).start();

            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    // The UpdateMessages function below was used to update players automatically when actions were taken in game. These functions were called based on the type inputted.
    // This worked using PUTTY but I did not manage to get it to work with the ClientProgram class. I hae left it here so you can take a look at it if you like
/*
    public static void UpdateMessage(String type, int playerID) throws IOException {

        for (ClientHandler h: players) {
            server = h.getOut();
            if (type.equals("Pass")) {
                if (playerID == game.getPlayerWithBall()) {
                    server.println("You received the ball!");
                    server.println("To pass the ball, type PASS");
                }
            } else if (type.equals("Players")) {
                server.println("The current player with the ball is : " + game.getPlayerWithBall());
                server.println("The current players in game are : " + game.getListOfCurrentPlayers());
            } else if (type.equals("New")) {
                //System.out.println("Player" + playerID + " has joined the game!");
                server.println("Player " + playerID + " has joined the game!");
               //server.println("The current players in game are : " + game.getListOfCurrentPlayers());

            } else if (type.equals("Disconnect")) {
                server.println("Player " + playerID + " has disconnected!");
                server.println("The current players in game are : " + game.getListOfCurrentPlayers());
            }
        }


    }

    public static PrintWriter getWriter() {
        return server;
    }
*/

}



