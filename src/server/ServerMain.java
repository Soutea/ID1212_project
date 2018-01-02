package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerMain {

    /* Class variables */
    private static Integer port = 5555;
    private static String welcome = "Welcome to the Rock Paper Scissors Server!";

    /* Port numbers ranging from 1024 to 65535 ( 1 - 1023 being reserved for well-known services */
    private static boolean validPort(Integer x) {
        return x >= 1024 && x <= 65535;
    }

    /* Allowing the user to decide a port or use the hardcoded/default one */
    private static int getPort() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Select a port by entering a value between 1024 and 65535 or\n");
        System.out.print("insert \"0\" if you want to use the default port (" + ServerMain.port + "): ");

        Integer input = scanner.nextInt();

        if (input != 0 && !ServerMain.validPort(input)) {
            return input;
        } else {
            return ServerMain.port;
        }
    }


    public static void main(String args[]) throws Exception {
        /* Welcome message to the player & giving the server a port */
        System.out.println(ServerMain.welcome);
        ServerMain.port = ServerMain.getPort();

        /* Create new server socket with a status message */
        ServerSocket welcomeSocket = new ServerSocket(ServerMain.port);
        System.out.println("\n We're up and running on port " + welcomeSocket.getLocalPort() + " ...");

        while (!welcomeSocket.isClosed()) {
            /* 3 players */
            Socket[] clients = new Socket[3];

            for (int i = 0; i < clients.length; i++) {
                clients[i] = welcomeSocket.accept();
                if (clients[i].isConnected()) {
                    String nextMsg;
                    if (i == clients.length - 1) {
                        nextMsg = "Starting game";
                    } else {
                        nextMsg = "Waiting for player " + (i + 1);
                    }
                    System.out.println("\nPlayer " + i + " (" + (clients[i].getLocalAddress().toString()).substring(1) + ":"
                            + clients[i].getLocalPort() + ") has joined ... " + nextMsg);
                }


            }
            /* Start a new game */
            new GameThread(clients).start();
        }
    }
}