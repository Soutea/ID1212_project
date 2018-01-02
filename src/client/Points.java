package client;

import java.util.Objects;

public class Points {
    private final int total;
    private final int round;

    public Points(int total, int round) {
        this.total = total;
        this.round = round;
    }

    public int getTotal() {
        return total;
    }

    public int getRound() {
        return round;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Points points = (Points) o;
        return total == points.total &&
                round == points.round;
    }

    @Override
    public int hashCode() {

        return Objects.hash(total, round);
    }
}

