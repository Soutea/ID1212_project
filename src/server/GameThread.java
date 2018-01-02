package server;

import server.gameLogic.Game;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class GameThread extends Thread {

    /* Array of sockets*/
    private final Socket[] clients;

    /* Constructor */
    GameThread(Socket[] clients) {
        this.clients = clients;
        setDaemon(true);
    }

    /* */
    public void run() {
        try {
            Game game = new Game();

            DataOutputStream[] outputs = new DataOutputStream[clients.length];
            BufferedReader[] inputs = new BufferedReader[clients.length];
            for (int i = 0; i < clients.length; i++) {
                outputs[i] = new DataOutputStream(clients[i].getOutputStream());
                inputs[i] = new BufferedReader(new InputStreamReader(clients[i].getInputStream()));
            }

            while (true) {
                game.beginRound();
                for (int i = 0; i < clients.length; i++) {
                    // Get client inputs
                    game.setPlayerHand(i, inputs[i].readLine());
                }
                game.finishRound();

                // Send responses in uppercase and close sockets
                for (int i = 0; i < clients.length; i++) {
                    outputs[i].writeBytes(game.getTotalPoints(i) + "," + game.getCurrentPoints(i) + "\r\n");
                    outputs[i].flush();
                    // clients[i].close();
                }
            }
        } catch (
                Exception error) {
            throw new RuntimeException(error);
        }
    }
}






