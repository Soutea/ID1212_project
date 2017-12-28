package GameLogic;

public enum Hand {
    ROCK(null),
    PAPER(ROCK),
    SCISSORS(PAPER);

    private final Hand beats;

    Hand(Hand beats) {
        this.beats = beats;
    }

    public Result beats(Hand other) {
        Hand thisBeats = beats != null ? beats : SCISSORS;
        if (this == other) {
            return Result.DRAW;
        } else if (thisBeats == other) {
            return Result.WIN;
        } else {
            return Result.LOSS;
        }
    }
}
