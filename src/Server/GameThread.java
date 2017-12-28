package Server;

import GameLogic.Hand;
import GameLogic.Result;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class GameThread extends Thread {

    private final Socket[] clients;


    GameThread(Socket[] clients) {
        this.clients = clients;
        //this.client1 = client1;
        //this.client2 = client2;
        setDaemon(true); // förhindrar att spelet är igång när gamla spelare håller spelet igång
    }


    public void run() {
        try {
            DataOutputStream[] outputs = new DataOutputStream[clients.length];
            BufferedReader[] inputs = new BufferedReader[clients.length];
            for (int i = 0; i < clients.length; i++) {
                outputs[i] = new DataOutputStream(clients[i].getOutputStream());
                inputs[i] = new BufferedReader(new InputStreamReader(clients[i].getInputStream()));
            }

            int[] totalPoints = new int[clients.length];
            while (true) {
                Hand[] currentHands = new Hand[clients.length];
                for (int i = 0; i < clients.length; i++) {
                    // Get client inputs
                    currentHands[i] = Hand.valueOf(inputs[i].readLine().toUpperCase()); //få händerna
                }

                int[] currentPoints = new int[clients.length];
                for (int i = 0; i < currentHands.length; i++) { //räkna poängen
                    for (int j = 0; j < currentHands.length; j++) {
                        if (currentHands[i].beats(currentHands[j]) == Result.WIN) { //bryr oss om detta fall
                            currentPoints[i]++;
                            totalPoints[i]++;
                        }
                    }
                }


                // Send responses in uppercase and close sockets
                for (int i = 0; i < clients.length; i++) {
                    outputs[i].writeBytes(totalPoints[i] + "," + currentPoints[i] + "\r\n");
                    outputs[i].flush();
                    // clients[i].close();
                }
            }
        } catch (
                Exception error)

        {
            throw new RuntimeException(error);
        }
    }
}






