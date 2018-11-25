package com.doernenburg.ipd.match;

public enum Move {

    DEFECT(0),
    COOPERATE(1),
    NA(2);

    private static final String[] STRINGS = new String[] { "D", "C", "#" };

    private final int value;

    Move(int value) {
        this.value = value;
    }

    public String toString() {
        return STRINGS[value];
    }

}
