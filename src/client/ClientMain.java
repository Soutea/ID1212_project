package client;

import server.gameLogic.Hand;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

class ClientMain {

    /* Class variables */
    private static String host = "localhost";
    private static Integer port = 5555;
    private static String welcome = "Welcome to this Rock Paper Scissors game!";
    private static String rules = "\nRules:\n - Rock beats Scissors\n - Scissors beats Paper\n - Paper beats Rock\n";

    public static void main(String args[]) throws Exception {
        /* Welcome message */
        System.out.println(ClientMain.welcome); // welcome message

        /* reads from the user */
        BufferedReader FromUser = new BufferedReader(new InputStreamReader(
                System.in));

        Connection connection = new Connection();
        connection.connectToServer(host, port);

        /* Run this always */
        while (true) {
            System.out.println("Start the game by selecting Rock, Paper or Scissors");
            System.out.println("or type \"-rules\" in order to see the rules: ");

            Hand input = null;
            /* if the user prints in something else than the hands */
            while (input == null) {
                /* from the user */
                String line = FromUser.readLine();
                try {
                    input = Hand.valueOf(line.toUpperCase());
                } catch (IllegalArgumentException ex) {
                    input = null;
                }
                /*If the user wants to see the rules, show the rules */
                if (line.equals("-rules")) {
                    System.out.println(ClientMain.rules);
                }
            }

            /* input to the server with a flush to clear*/
            System.out.println("\nYour input (" + input + ") was successfully transmitted to the server. Please wait for the result...");
            Points response = connection.playRound(input);
            System.out.println("Total Score: " + response.getTotal() + ", Round Score: " + response.getRound());

        }

        //clientSocket.close(); //cannot have this if we want to have more game instances
    }
}