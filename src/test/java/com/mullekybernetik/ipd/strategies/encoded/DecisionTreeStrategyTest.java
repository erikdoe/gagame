package com.mullekybernetik.ipd.strategies.encoded;

import com.mullekybernetik.ipd.match.Move;
import org.junit.Test;

import static org.junit.Assert.*;

public class DecisionTreeStrategyTest {

    @Test
    public void shouldCreateNumericalEncodingFromString() {

        DecisionTreeStrategy strategy = new DecisionTreeStrategy("DDCDDDC");
        int numericalRepresentation = strategy.getNumericalRepresentation();
        assertEquals("Number should have used bits in reverse order", 0x44, numericalRepresentation);
    }

    @Test
    public void shouldCalculateStrategyDepth() {

        DecisionTreeStrategy strategy = new DecisionTreeStrategy("CDCDDDCDDDDDDDC");
        int depth = strategy.getDepth();
        assertEquals("Depth should be log(x) of longest block", 3, depth);
    }

    @Test
    public void playerShouldRepresentMovesAsBitsInMemory() {

        DecisionTreeStrategy strategy = new DecisionTreeStrategy("CDCDDDCDDDDDDDC");
        DecisionTreeStrategy.PlayerImpl player = (DecisionTreeStrategy.PlayerImpl) strategy.instantiate();
        player.setOpponentsMove(Move.COOPERATE);
        player.setOpponentsMove(Move.COOPERATE);
        player.setOpponentsMove(Move.DEFECT);

        int memory = player.getMemory();

        assertEquals(0b110, memory);
    }

    @Test
    public void playerShouldCapMemoryAtDepth() {

        DecisionTreeStrategy strategy = new DecisionTreeStrategy("CDCDDDC");
        DecisionTreeStrategy.PlayerImpl player = (DecisionTreeStrategy.PlayerImpl) strategy.instantiate();
        player.setOpponentsMove(Move.COOPERATE);
        player.setOpponentsMove(Move.COOPERATE);
        player.setOpponentsMove(Move.DEFECT);

        int memory = player.getMemory();

        assertEquals(0b10, memory);
    }

    @Test
    public void playerShouldUseLowestBitToDetermineInitialMove() {
        DecisionTreeStrategy strategy = new DecisionTreeStrategy("CDD");
        DecisionTreeStrategy.PlayerImpl player = (DecisionTreeStrategy.PlayerImpl) strategy.instantiate();

        Move move = player.getMove();

        assertEquals(Move.COOPERATE, move);
    }

    @Test
    public void playerShouldUseLowestBitInSecondBlockToDetermineResponseToDefect() {
        DecisionTreeStrategy strategy = new DecisionTreeStrategy("DCD");
        DecisionTreeStrategy.PlayerImpl player = (DecisionTreeStrategy.PlayerImpl) strategy.instantiate();

        player.getMove();
        player.setOpponentsMove(Move.DEFECT);
        Move move = player.getMove();

        assertEquals(Move.COOPERATE, move);
    }

    @Test
    public void playerShouldOnlyConsiderCorrectBitWhenDeterminingResponse() {
        DecisionTreeStrategy strategy = new DecisionTreeStrategy("CDC");
        DecisionTreeStrategy.PlayerImpl player = (DecisionTreeStrategy.PlayerImpl) strategy.instantiate();

        player.getMove();
        player.setOpponentsMove(Move.DEFECT);
        Move move = player.getMove();

        assertEquals(Move.DEFECT, move);
    }

    @Test
    public void playerShouldUseCorrectBitInThirdBlockToDetermineResponse() {
        DecisionTreeStrategy strategy = new DecisionTreeStrategy("DDDDCDD");
        DecisionTreeStrategy.PlayerImpl player = (DecisionTreeStrategy.PlayerImpl) strategy.instantiate();

        player.getMove();
        player.setOpponentsMove(Move.DEFECT);
        player.getMove();
        player.setOpponentsMove(Move.COOPERATE);
        Move move = player.getMove();

        assertEquals(Move.COOPERATE, move);
    }

    @Test
    public void playerShouldIgnoreMovesBeyondDepthOfStrategy() {
        DecisionTreeStrategy strategy = new DecisionTreeStrategy("DDDDCDD");
        DecisionTreeStrategy.PlayerImpl player = (DecisionTreeStrategy.PlayerImpl) strategy.instantiate();

        player.getMove();
        player.setOpponentsMove(Move.COOPERATE);
        player.getMove();
        player.setOpponentsMove(Move.DEFECT);
        player.getMove();
        player.setOpponentsMove(Move.COOPERATE);
        Move move = player.getMove();

        assertEquals(Move.COOPERATE, move);
    }

}
