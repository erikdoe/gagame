package com.mullekybernetik.ipd.strategies.encoded;

import com.mullekybernetik.ipd.match.Move;
import com.mullekybernetik.ipd.strategies.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConditionalDefectorTest {

    @Test
    public void shouldCooperateWhenNoConditionsAreGiven() {

        Player player = new ConditionalDefector("").instantiate();

        Move move = player.getMove();

        assertEquals("Should have returned expected move", Move.COOPERATE, move);
    }

    @Test
    public void shouldCooperateWhenConditionIsFalse() {

        Player player = new ConditionalDefector("D").instantiate();

        Move move = player.getMove();

        assertEquals("Should have returned expected move", Move.COOPERATE, move);
    }

    @Test
    public void shouldDefectWhenConditionsCheckForNoMoveAndNoMoveGiven() {

        Player player = new ConditionalDefector("U").instantiate();

        Move move = player.getMove();

        assertEquals("Should have returned expected move", Move.DEFECT, move);
    }

    @Test
    public void shouldDefectWhenConditionChecksForDefectAndDefectMoveGiven() {

        Player player = new ConditionalDefector("D").instantiate();

        player.getMove();
        player.setOpponentsMove(Move.DEFECT);
        Move move = player.getMove();

        assertEquals("Should have returned expected move", Move.DEFECT, move);
    }


    @Test
    public void shouldCooperateWhenConditionChecksForCooperateAndDefectMoveGiven() {

        Player player = new ConditionalDefector("C").instantiate();

        player.getMove();
        player.setOpponentsMove(Move.DEFECT);
        Move move = player.getMove();

        assertEquals("Should have returned expected move", Move.COOPERATE, move);
    }

    @Test
    public void shouldCooperateWhenConditionHasQuestionMarkAndNoMovesKnown() {

        Player player = new ConditionalDefector("?").instantiate();

        Move move = player.getMove();

        assertEquals("Should have returned expected move", Move.COOPERATE, move);
    }

    @Test
    public void shouldDefectWhenConditionChecksForTwoDefectCooperateAndThatIsGiven() {

        Player player = new ConditionalDefector("DC").instantiate();

        player.getMove();
        player.setOpponentsMove(Move.DEFECT);
        player.getMove();
        player.setOpponentsMove(Move.COOPERATE);
        Move move = player.getMove();

        assertEquals("Should have returned expected move", Move.DEFECT, move);
    }

    @Test
    public void shouldCooperateWhenConditionChecksForTwoConsecutiveDefectsAndCooperateDefectIsGiven() {

        Player player = new ConditionalDefector("DC").instantiate();

        player.getMove();
        player.setOpponentsMove(Move.DEFECT);
        player.getMove();
        player.setOpponentsMove(Move.DEFECT);
        Move move = player.getMove();

        assertEquals("Should have returned expected move", Move.COOPERATE, move);
    }

    @Test
    public void shouldDefectWhenConditionHasQuestionMarksMatchingDefectAndCooperate() {

        Player player = new ConditionalDefector("C??").instantiate();

        player.getMove();
        player.setOpponentsMove(Move.COOPERATE);
        player.getMove();
        player.setOpponentsMove(Move.DEFECT);
        player.getMove();
        player.setOpponentsMove(Move.COOPERATE);
        Move move = player.getMove();

        assertEquals("Should have returned expected move", Move.DEFECT, move);
    }

    @Test
    public void shouldAlsoEvaluateSecondBlock() {

        Player player = new ConditionalDefector("DC-UUC").instantiate();

        player.getMove();
        player.setOpponentsMove(Move.COOPERATE);
        Move move = player.getMove();

        assertEquals("Should have returned expected move", Move.DEFECT, move);
    }
}