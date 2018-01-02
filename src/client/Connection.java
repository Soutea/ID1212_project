package client;

import server.gameLogic.Hand;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Connection {
    private DataOutputStream toServer;
    private BufferedReader fromServer;

    public void connectToServer(String host, int port) throws IOException {
        Socket clientSocket = new Socket(host, port);
        /* Stream to the server*/
        toServer = new DataOutputStream(
                clientSocket.getOutputStream());
        /* Reads from the server */

        fromServer = new BufferedReader(new InputStreamReader(
                clientSocket.getInputStream()));
    }

    public Points playRound(Hand hand) throws IOException {
        toServer.writeBytes(hand + "\n");
        toServer.flush();

        /* response from the server divided into two sections, dividing at "," */
        String[] response = fromServer.readLine().split(",");
        return new Points(Integer.parseInt(response[0]), Integer.parseInt(response[1]));
    }
}



