package server.gameLogic;

public class Game {
    private int numberOfClients = 3;
    private int[] currentPoints = new int[numberOfClients];
    private int[] totalPoints = new int[numberOfClients];

    private Hand[] currentHands = new Hand[numberOfClients];

    /* resets the amount of points for the round */
    public void beginRound() {
        currentPoints = new int[numberOfClients];
    }

    /* get the game hands */
    public void setPlayerHand(int i, String hand) {
        currentHands[i] = Hand.valueOf(hand.toUpperCase());
    }

    /* if there is a win, we want to increase the point variables*/
    public void finishRound() {
        for (int i = 0; i < currentHands.length; i++) {
            for (int j = 0; j < currentHands.length; j++) {
                if (currentHands[i].beats(currentHands[j]) == Result.WIN) { //bryr oss om detta fall
                    currentPoints[i]++;
                    totalPoints[i]++;
                }
            }
        }
    }

    /* Getters */
    public int getCurrentPoints(int i) {
        return currentPoints[i];
    }

    public int getTotalPoints(int i) {
        return totalPoints[i];
    }
}




