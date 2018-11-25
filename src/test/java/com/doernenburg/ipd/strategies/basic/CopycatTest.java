package com.doernenburg.ipd.strategies.basic;

import com.doernenburg.ipd.match.Move;
import com.doernenburg.ipd.strategies.Player;
import junit.framework.Assert;
import org.junit.Test;

public class CopycatTest {

    @Test
    public void shouldFirstCooperate() {
        Player tft = new Copycat().instantiate();
        Assert.assertEquals(Move.COOPERATE, tft.getMove());
    }

    @Test
    public void shouldCooperateWhenOpponentCooperated() {
        Player tft = new Copycat().instantiate();
        tft.setOpponentsMove(Move.COOPERATE);
        Assert.assertEquals(Move.COOPERATE, tft.getMove());
    }

    @Test
    public void shouldDefectWhenOpponentDefected() {
        Player tft = new Copycat().instantiate();
        tft.setOpponentsMove(Move.DEFECT);
        Assert.assertEquals(Move.DEFECT, tft.getMove());
    }

}
