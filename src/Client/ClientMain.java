package Client;

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
        System.out.println(ClientMain.welcome); // welcome message

        BufferedReader FromUser = new BufferedReader(new InputStreamReader( //reads from the user
                System.in));

        Socket clientSocket = new Socket(ClientMain.host, ClientMain.port); //unique client socket

        DataOutputStream ToServer = new DataOutputStream( //sends to the server
                clientSocket.getOutputStream());

        BufferedReader FromServer = new BufferedReader(new InputStreamReader( // reads from the server
                clientSocket.getInputStream()));

        while (true) {
            System.out.println("Start the game by selecting Rock, Paper or Scissors");
            System.out.println("or type \"-rules\" in order to see the rules: ");

            String input = "";
            while (!input.equals("Rock") && !input.equals("Paper") && !input.equals("Scissors")) { //if the input is not a game choice
                input = FromUser.readLine(); // from the user
                if (input.equals("-rules")) { // if the user wants to the rules
                    System.out.println(ClientMain.rules); //show the rules
                }
            }

            ToServer.writeBytes(input + "\n");// input to the server
            ToServer.flush();
            System.out.println("\nYour input (" + input + ") was successfully transmitted to the server. Please wait for the result...");

            String[] response = FromServer.readLine().split(","); // response from the server
            System.out.println("Total Score: " + response[0] + ", Round Score: " + response[1]); //vi delade upp response i två delar,
            // där , splittar
        }

        //clientSocket.close();
    }
}