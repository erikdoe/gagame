package com.mullekybernetik.ipd.match;

import com.mullekybernetik.ipd.strategies.Player;
import com.mullekybernetik.ipd.strategies.basic.Cooperator;
import com.mullekybernetik.ipd.strategies.basic.Defector;
import org.junit.Assert;
import org.junit.Test;

import static com.mullekybernetik.ipd.match.Match.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MatchTest {

    @Test
    public void shouldProvidePlayersWithResultsForSingleRound() {
        Player alice = mock(Player.class);
        when(alice.getMove()).thenReturn(Move.COOPERATE);

        Player bob = mock(Player.class);
        when(bob.getMove()).thenReturn(Move.DEFECT);

        Match match = new Match(alice, bob);
        match.playRound();

        verify(alice).setOpponentsMove(Move.DEFECT);
        verify(bob).setOpponentsMove(Move.COOPERATE);
    }

    @Test
    public void shouldAssignCorrectPointsForSuccessfulCooperation() {
        Match match = new Match(new Cooperator(), new Cooperator());
        match.playRound();
        Score score = match.getScore();

        assertEquals(POINTS_FOR_SUCCESSFUL_COOPERATION, score.a);
        assertEquals(POINTS_FOR_SUCCESSFUL_COOPERATION, score.b);
    }

    @Test
    public void shouldAssignCorrectPointsForSuccessfulBetrayalFirstPlayer() {
        Match match = new Match(new Cooperator(), new Defector());
        match.playRound();
        Score score = match.getScore();

        Assert.assertEquals(POINTS_FOR_UNSUCCESSFUL_COOPERATION, score.a);
        Assert.assertEquals(POINTS_FOR_SUCCESSFUL_BETRAYAL, score.b);
    }

    @Test
    public void shouldAssignCorrectPointsForSuccessfulBetrayalSecondPlayer() {
        Match match = new Match(new Defector(), new Cooperator());
        match.playRound();
        Score score = match.getScore();

        Assert.assertEquals(POINTS_FOR_SUCCESSFUL_BETRAYAL, score.a);
        Assert.assertEquals(POINTS_FOR_UNSUCCESSFUL_COOPERATION, score.b);
    }

    @Test
    public void shouldAssignCorrectPointsForMutualBetrayal() {
        Match match = new Match(new Defector(), new Defector());
        match.playRound();
        Score score = match.getScore();

        Assert.assertEquals(POINTS_FOR_MUTUAL_BETRAYAL, score.a);
        Assert.assertEquals(POINTS_FOR_MUTUAL_BETRAYAL, score.b);
    }

    @Test
    public void shouldRunMultipleRoundsAndReturnScore() {
        Match match = new Match(new Cooperator(), new Defector());
        Score score = match.playGame(2);

        Assert.assertEquals("Should have right number of points for cooperator", 0, score.a);
        Assert.assertEquals("Should have right number of points for defector", 2 * POINTS_FOR_SUCCESSFUL_BETRAYAL, score.b);
    }

}
