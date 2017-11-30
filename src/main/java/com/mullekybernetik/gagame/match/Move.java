package com.mullekybernetik.gagame.match;

public final class Move {

    public static final Move DEFECT = new Move(0);
    public static final Move COOPERATE = new Move(1);
    public static final Move UNKNOWN = new Move(2);

    private static final String[] STRINGS = new String[] { "D", "C", "U" };

    private int value;


    public static Move fromChar(char s) {
        if (s == 'D') {
            return Move.DEFECT;
        } else if (s == 'C') {
            return Move.COOPERATE;
        } else {
            return Move.UNKNOWN;
        }
    }


    private Move(int value) {
        this.value = value;
    }

    public int getValue() {
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
        return STRINGS[value];
    }

}
