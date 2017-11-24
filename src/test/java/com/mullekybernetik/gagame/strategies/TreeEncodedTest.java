package com.mullekybernetik.gagame.strategies;

import com.mullekybernetik.gagame.match.Move;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TreeEncodedTest {

    @Test
    public void shouldCreateStrategyFromEightCharString() {
        TreeEncoded strategy = new TreeEncoded("+-+-+-+-");
        Move[] encodedStrategy = strategy.getEncodedStrategy();
        assertEquals(8, encodedStrategy.length);
        for (int i = 0; i < 8; i += 2) {
            assertEquals("Should have returned cooperate for " + i, Move.COOPERATE, encodedStrategy[i]);
            assertEquals("Should have returned defect for " + i + 1, Move.DEFECT, encodedStrategy[i + 1]);
        }
    }

    @Test
    public void shouldCreateStrategyFromSixteenCharString() {
        TreeEncoded strategy = new TreeEncoded("++++------------");
        Move[] encodedStrategy = strategy.getEncodedStrategy();
        assertEquals(16, encodedStrategy.length);
        for (int i = 0; i < 4; i++)
            assertEquals("Should have returned cooperate for " + i, Move.COOPERATE, encodedStrategy[i]);
        for (int i = 4; i < 16; i++)
            assertEquals("Should have returned defect for " + i, Move.DEFECT, encodedStrategy[i]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowOnStringWithInvalidSize() {
        new TreeEncoded("+++++");
    }

    @Test
    public void shouldCreateStrategyForNumber() {
        TreeEncoded strategy = TreeEncoded.fromNumber(12, 4);  // 1100 = ++--
        Move[] encodedStrategy = strategy.getEncodedStrategy();
        assertEquals("Should have returned defect for 0", Move.DEFECT, encodedStrategy[0]);
        assertEquals("Should have returned defect for 1", Move.DEFECT, encodedStrategy[1]);
        assertEquals("Should have returned cooperate for 2", Move.COOPERATE, encodedStrategy[2]);
        assertEquals("Should have returned cooperate for 3", Move.COOPERATE, encodedStrategy[3]);
    }


    @Test
    public void shouldDetermineRightAnswerForCCCC() {
        TreeEncoded strategy = new TreeEncoded("---------------+");
        strategy.newMatch();
        strategy.setOpponentsMove(Move.COOPERATE);
        strategy.setOpponentsMove(Move.COOPERATE);
        strategy.setOpponentsMove(Move.COOPERATE);
        strategy.setOpponentsMove(Move.COOPERATE);

        assertEquals("Should have returned rightmost value", Move.COOPERATE, strategy.getMove());
    }

    @Test
    public void shouldDetermineRightAnswerForDCCC() {
        TreeEncoded strategy = new TreeEncoded("--------------+-");
        strategy.newMatch();
        strategy.setOpponentsMove(Move.DEFECT);
        strategy.setOpponentsMove(Move.COOPERATE);
        strategy.setOpponentsMove(Move.COOPERATE);
        strategy.setOpponentsMove(Move.COOPERATE);

        assertEquals("Should have returned second rightmost value", Move.COOPERATE, strategy.getMove());
    }

    @Test
    public void shouldDetermineRightAnswerForCCCD() {
        TreeEncoded strategy = new TreeEncoded("-------+--------");
        strategy.newMatch();
        strategy.setOpponentsMove(Move.COOPERATE);
        strategy.setOpponentsMove(Move.COOPERATE);
        strategy.setOpponentsMove(Move.COOPERATE);
        strategy.setOpponentsMove(Move.DEFECT);

        assertEquals("Should have returned rightmost value in left half", Move.COOPERATE, strategy.getMove());
    }

    @Test
    public void shouldDetermineRightAnswerForCCDD() {
        TreeEncoded strategy = new TreeEncoded("---+------------");
        strategy.newMatch();
        strategy.setOpponentsMove(Move.COOPERATE);
        strategy.setOpponentsMove(Move.COOPERATE);
        strategy.setOpponentsMove(Move.DEFECT);
        strategy.setOpponentsMove(Move.DEFECT);

        assertEquals("Should have returned rightmost value in leftmost quarter", Move.COOPERATE, strategy.getMove());
    }

    @Test
    public void shouldDetermineRightAnswerForCDCD() {
        TreeEncoded strategy = new TreeEncoded("-----+----------");
        strategy.newMatch();
        strategy.setOpponentsMove(Move.COOPERATE);
        strategy.setOpponentsMove(Move.DEFECT);
        strategy.setOpponentsMove(Move.COOPERATE);
        strategy.setOpponentsMove(Move.DEFECT);

        assertEquals("Should have returned sixth value", Move.COOPERATE, strategy.getMove());
    }


}
