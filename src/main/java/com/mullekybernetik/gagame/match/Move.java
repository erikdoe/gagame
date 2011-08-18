package com.mullekybernetik.gagame.match;

public final class Move {

    public static final Move DEFECT = new Move(0);
    public static final Move COOPERATE = new Move(1);

    private int value;

    private Move(int value) {
        this.value = value;
    }

    private int getValue() {
        return value;
    }

    public boolean equals(Object other) {
        if (other instanceof Move == false)
            return false;
        return ((Move) other).getValue() == value;
    }

    public int hashCode() {
        return value;
    }

    public String toString() {
        return (value == 0) ? "D" : "C";
    }

}
