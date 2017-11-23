package com.mullekybernetik.gagame.match;

import com.mullekybernetik.gagame.strategies.Cooperator;
import com.mullekybernetik.gagame.strategies.Defector;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MatchTest {

    @Test
    public void shouldProvideStrategiesWithResultsForSingleRound() {
        Strategy alice = mock(Strategy.class);
        when(alice.getMove()).thenReturn(Move.DEFECT);
        Strategy bob = mock(Strategy.class);
        when(bob.getMove()).thenReturn(Move.COOPERATE);

        Match match = new Match(alice, bob);
        match.playRound();

        verify(alice).newMatch();
        verify(alice).setOpponentsMove(Move.COOPERATE);
        verify(bob).newMatch();
        verify(bob).setOpponentsMove(Move.DEFECT);
    }

    @Test
    public void shouldAssignCorrectPointsForSuccessfulCooperation() {
        Match match = new Match(new Cooperator(), new Cooperator());
        match.playRound();
        Score score = match.getScore();

        assertEquals(2, score.a);
        assertEquals(2, score.b);
    }

    @Test
    public void shouldAssignCorrectPointsForSuccessfulBetrayalFirstPlayer() {
        Match match = new Match(new Cooperator(), new Defector());
        match.playRound();
        Score score = match.getScore();

        Assert.assertEquals(0, score.a);
        Assert.assertEquals(3, score.b);
    }

    @Test
    public void shouldAssignCorrectPointsForSuccessfulBetrayalSecondPlayer() {
        Match match = new Match(new Defector(), new Cooperator());
        match.playRound();
        Score score = match.getScore();

        Assert.assertEquals(3, score.a);
        Assert.assertEquals(0, score.b);
    }

    @Test
    public void shouldAssignCorrectPointsForMutualBetrayal() {
        Match match = new Match(new Defector(), new Defector());
        match.playRound();
        Score score = match.getScore();

        Assert.assertEquals(1, score.a);
        Assert.assertEquals(1, score.b);
    }

}
