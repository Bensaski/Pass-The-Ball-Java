import java.io.PrintWriter;
import java.net.Socket;

import java.util.Scanner;

public  class Client implements AutoCloseable {
    final int port = 1234;
    private final Scanner reader;
    private static PrintWriter writer;

    public Client() throws Exception {
        Socket socket = new Socket("localhost", port);
        reader = new Scanner(socket.getInputStream());

        writer = new PrintWriter(socket.getOutputStream(), true); //this printwriter writes to the socket ouput stream, which the reader in the clienthandler class is listening to
        //writer2 = Server.getWriter();


    }
//All of these functions below write to the PrintWriter a line that is being listened to in the ClientHandler. The Clienthandler then runs the switch case based on the input from here,
// and then writes out to its PrintWriter , which the reader here is listening to. That is then assigned to the String line, which gets returned to the ClientProgram class to be used
// for variables or outputs






    public String Disconnect(int playerID) {
        writer.println("disconnect " + playerID);
        return reader.nextLine();
    }

    public int[] getListOfPlayers() {
        writer.println("players");

        String line = reader.nextLine();
        int listofplayers = Integer.parseInt(line);
        int[] players1 = new int[listofplayers];
        for (int i = 0; i < listofplayers; i++) {
            line = reader.nextLine();
            players1[i] = Integer.parseInt(line);
        }
        return players1;
    }


    public int getPlayerWithBall() {

        writer.println("ball");
        String line = reader.nextLine();
        return Integer.parseInt(line);
    }


    public int getListOfCurrentPlayers2() {
        writer.println("players2");
        String line = reader.nextLine();



        return Integer.parseInt(line);
    }

    public int[] getListOfCurrentPlayers() {
        writer.println("players");

        String line = reader.nextLine();
        int listofplayers = Integer.parseInt(line);
        int[] players1 = new int[listofplayers];
        for (int i = 0; i < listofplayers; i++) {
            line = reader.nextLine();
            players1[i] = Integer.parseInt(line);
        }
        return players1;
    }

    public String passBall(int PlayerID, int targetPlayerID) {
        writer.println("pass " + PlayerID + " " + targetPlayerID);

        return reader.nextLine();
    }

    public int newPlayer() {
        writer.println("new");
        String line = reader.nextLine();
        return Integer.parseInt(line);
    }






    @Override
    public void close() throws Exception {
        writer.close();
        reader.close();
    }
}
