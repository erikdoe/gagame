package com.mullekybernetik.ipd.strategies.encoded;

import com.mullekybernetik.ipd.match.Move;
import com.mullekybernetik.ipd.strategies.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConditionalCooperatorTest {

    @Test
    public void shouldCooperateWhenEmptyConditionIsGiven() {

        Player player = new ConditionalCooperator("").instantiate();

        Move move = player.getMove();

        assertEquals(Move.COOPERATE, move);
    }

    @Test
    public void shouldDefectWhenConditionIsFalse() {

        Player player = new ConditionalCooperator("D").instantiate();

        Move move = player.getMove();

        assertEquals(Move.DEFECT, move);
    }

    @Test
    public void shouldCooperateWhenConditionsCheckForNoMoveAndNoMoveGiven() {

        Player player = new ConditionalCooperator("N").instantiate();

        Move move = player.getMove();

        assertEquals(Move.COOPERATE, move);
    }

    @Test
    public void shouldCooperateWhenConditionChecksForDefectAndDefectMoveGiven() {

        Player player = new ConditionalCooperator("D").instantiate();

        player.getMove();
        player.setOpponentsMove(Move.DEFECT);
        Move move = player.getMove();

        assertEquals(Move.COOPERATE, move);
    }


    @Test
    public void shouldNotCooperateWhenConditionChecksForCooperateAndDefectMoveGiven() {

        Player player = new ConditionalCooperator("C").instantiate();

        player.getMove();
        player.setOpponentsMove(Move.DEFECT);
        Move move = player.getMove();

        assertEquals(Move.DEFECT, move);
    }

    @Test
    public void shouldDefectWhenConditionIsAnyMoveButNoMovesAvailable() {

        Player player = new ConditionalCooperator("?").instantiate();

        Move move = player.getMove();

        assertEquals(Move.DEFECT, move);
    }

    @Test
    public void shouldCooperateWhenConditionChecksForGivenMoves() {

        Player player = new ConditionalCooperator("DC").instantiate();

        player.getMove();
        player.setOpponentsMove(Move.DEFECT);
        player.getMove();
        player.setOpponentsMove(Move.COOPERATE);
        Move move = player.getMove();

        assertEquals(Move.COOPERATE, move);
    }

    @Test
    public void shouldCooperateWhenConditionChecksForNoMoveAvailableAndGivenMove() {

        Player player = new ConditionalCooperator("ND").instantiate();

        player.getMove();
        player.setOpponentsMove(Move.DEFECT);
        Move move = player.getMove();

        assertEquals(Move.COOPERATE, move);
    }

    @Test
    public void shouldDefectWhenConditionDoesNotMatchGivenMoves() {

        Player player = new ConditionalCooperator("DC").instantiate();

        player.getMove();
        player.setOpponentsMove(Move.DEFECT);
        player.getMove();
        player.setOpponentsMove(Move.DEFECT);
        Move move = player.getMove();

        assertEquals(Move.DEFECT, move);
    }

    @Test
    public void shouldCooperateWhenConditionHasAnyMovesMatchingDefectAndCooperate() {

        Player player = new ConditionalCooperator("C??").instantiate();

        player.getMove();
        player.setOpponentsMove(Move.COOPERATE);
        player.getMove();
        player.setOpponentsMove(Move.DEFECT);
        player.getMove();
        player.setOpponentsMove(Move.COOPERATE);
        Move move = player.getMove();

        assertEquals(Move.COOPERATE, move);
    }

    @Test
    public void shouldCooperateWhenConditionHasHashMatchingNoMoveAndMove() {

        Player player = new ConditionalCooperator("###C").instantiate();

        player.setOpponentsMove(Move.COOPERATE);
        player.getMove();
        player.setOpponentsMove(Move.DEFECT);
        player.getMove();
        player.setOpponentsMove(Move.COOPERATE);
        Move move = player.getMove();

        assertEquals(Move.COOPERATE, move);
    }

    @Test
    public void shouldAlsoEvaluateSecondBlock() {

        Player player = new ConditionalCooperator("CD-NNC").instantiate();

        player.getMove();
        player.setOpponentsMove(Move.COOPERATE);
        Move move = player.getMove();

        assertEquals(Move.COOPERATE, move);
    }

}