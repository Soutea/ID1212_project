package client;

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

        /* Unique client socket*/
        Socket clientSocket = new Socket(ClientMain.host, ClientMain.port);
        /* Stream to the server*/
        DataOutputStream ToServer = new DataOutputStream(
                clientSocket.getOutputStream());
        /* Reads from the server */
        BufferedReader FromServer = new BufferedReader(new InputStreamReader(
                clientSocket.getInputStream()));

        /* Run this always */
        while (true) {
            System.out.println("Start the game by selecting Rock, Paper or Scissors");
            System.out.println("or type \"-rules\" in order to see the rules: ");

            String input = "";
            /* if the user prints in something else than the hands */
            while (!input.equals("Rock") && !input.equals("Paper") && !input.equals("Scissors")) {
                /* from the user */
                input = FromUser.readLine();
                /*If the user wants to see the rules, show the rules */
                if (input.equals("-rules")) {
                    System.out.println(ClientMain.rules);
                }
            }
            /* input to the server with a flush to clear*/
            ToServer.writeBytes(input + "\n");
            ToServer.flush();
            System.out.println("\nYour input (" + input + ") was successfully transmitted to the server. Please wait for the result...");

            /* response from the server divided into two sections, dividing at "," */
            String[] response = FromServer.readLine().split(",");
            System.out.println("Total Score: " + response[0] + ", Round Score: " + response[1]);

        }

        //clientSocket.close(); //cannot have this if we want to have more game instances
    }
}