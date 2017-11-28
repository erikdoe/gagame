package com.mullekybernetik.gagame.strategies;

import com.mullekybernetik.gagame.match.Move;
import org.junit.Test;

import static org.junit.Assert.*;

public class EncodedStrategyTest {

    @Test
    public void shouldCreateNumericalEncodingFromString() {

        EncodedStrategy strategy = new EncodedStrategy("--+---+");
        int numericalRepresentation = strategy.getNumericalRepresentation();
        assertEquals("Number should have used bits in reverse order", 68, numericalRepresentation);
    }

    @Test
    public void shouldCalculateStrategyDepth() {

        EncodedStrategy strategy = new EncodedStrategy("+-+---+-------+");
        int depth = strategy.getDepth();
        assertEquals("Depth should be log(x) of longest block", 3, depth);
    }

    @Test
    public void playerShouldRepresentMovesAsBitsInMemory() {

        EncodedStrategy strategy = new EncodedStrategy("+-+---+-------+");
        EncodedStrategy.PlayerImpl player = (EncodedStrategy.PlayerImpl) strategy.instantiate();
        player.setOpponentsMove(Move.COOPERATE);
        player.setOpponentsMove(Move.COOPERATE);
        player.setOpponentsMove(Move.DEFECT);

        int memory = player.getMemory();

        assertEquals("Correct bits should be set", 6, memory);
    }

    @Test
    public void playerShouldCapMemoryAtDepth() {

        EncodedStrategy strategy = new EncodedStrategy("+-+---+");
        EncodedStrategy.PlayerImpl player = (EncodedStrategy.PlayerImpl) strategy.instantiate();
        player.setOpponentsMove(Move.COOPERATE);
        player.setOpponentsMove(Move.COOPERATE);
        player.setOpponentsMove(Move.DEFECT);

        int memory = player.getMemory();

        assertEquals("Correct bits should be set", 2, memory);
    }

    @Test
    public void playerShouldUseLowestBitToDetermineInitialMove() {
        EncodedStrategy strategy = new EncodedStrategy("+--");
        EncodedStrategy.PlayerImpl player = (EncodedStrategy.PlayerImpl) strategy.instantiate();

        Move move = player.getMove();

        assertEquals("Should have used lowest bit", Move.COOPERATE, move);
    }

    @Test
    public void playerShouldUseLowestBitInSecondBlockToDetermineResponseToDefect() {
        EncodedStrategy strategy = new EncodedStrategy("-+-");
        EncodedStrategy.PlayerImpl player = (EncodedStrategy.PlayerImpl) strategy.instantiate();

        player.getMove();
        player.setOpponentsMove(Move.DEFECT);
        Move move = player.getMove();

        assertEquals("Should have used lowest bit in second block", Move.COOPERATE, move);
    }

    @Test
    public void playerShouldOnlyConsiderCorrectBitWhenDeterminingResponse() {
        EncodedStrategy strategy = new EncodedStrategy("+-+");
        EncodedStrategy.PlayerImpl player = (EncodedStrategy.PlayerImpl) strategy.instantiate();

        player.getMove();
        player.setOpponentsMove(Move.DEFECT);
        Move move = player.getMove();

        assertEquals("Should have used lowest bit in second block", Move.DEFECT, move);
    }

    @Test
    public void playerShouldUseCorrectBitInThirdBlockToDetermineResponse() {
        EncodedStrategy strategy = new EncodedStrategy("----+--");
        EncodedStrategy.PlayerImpl player = (EncodedStrategy.PlayerImpl) strategy.instantiate();

        player.getMove();
        player.setOpponentsMove(Move.DEFECT);
        player.getMove();
        player.setOpponentsMove(Move.COOPERATE);
        Move move = player.getMove();

        assertEquals("Should have used correct bit in third block", Move.COOPERATE, move);
    }

    @Test
    public void playerShouldIgnoreMovesBeyondDepthOfStrategy() {
        EncodedStrategy strategy = new EncodedStrategy("----+--");
        EncodedStrategy.PlayerImpl player = (EncodedStrategy.PlayerImpl) strategy.instantiate();

        player.getMove();
        player.setOpponentsMove(Move.COOPERATE);
        player.getMove();
        player.setOpponentsMove(Move.DEFECT);
        player.getMove();
        player.setOpponentsMove(Move.COOPERATE);
        Move move = player.getMove();

        assertEquals("At depth 2 the result for CDC should be the same as for DC", Move.COOPERATE, move);
    }

}