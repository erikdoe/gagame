package com.mullekybernetik.gagame.strategies;

import com.mullekybernetik.gagame.match.Move;
import junit.framework.Assert;
import org.junit.Test;

public class TitForTatTest {

    @Test
    public void shouldFirstCooperate() {
        Player tft = new TitForTat().instantiate();
        Assert.assertEquals(Move.COOPERATE, tft.getMove());
    }

    @Test
    public void shouldCooperateWhenOpponentCooperated() {
        Player tft = new TitForTat().instantiate();
        tft.setOpponentsMove(Move.COOPERATE);
        Assert.assertEquals(Move.COOPERATE, tft.getMove());
    }

    @Test
    public void shouldDefectWhenOpponentDefected() {
        Player tft = new TitForTat().instantiate();
        tft.setOpponentsMove(Move.DEFECT);
        Assert.assertEquals(Move.DEFECT, tft.getMove());
    }

}
