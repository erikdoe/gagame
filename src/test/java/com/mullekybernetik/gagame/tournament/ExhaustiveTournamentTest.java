package com.mullekybernetik.gagame.tournament;

import com.mullekybernetik.gagame.match.MatchRunner;
import com.mullekybernetik.gagame.match.Score;
import com.mullekybernetik.gagame.match.Strategy;
import com.mullekybernetik.gagame.strategies.Cooperator;
import com.mullekybernetik.gagame.strategies.Defector;
import com.mullekybernetik.gagame.strategies.RandomChoice;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ExhaustiveTournamentTest {

    @Test
    public void shouldRunMatchesBetweenAllParticipantsWithRightNumberOfRounds() {

        MatchRunner runner = mock(MatchRunner.class);
        ExhaustiveTournament tournament = new ExhaustiveTournament(runner);
        Strategy[] strategies = new Strategy[] { new Cooperator(), new Defector(), new RandomChoice() };
        when(runner.runMatch(any(Strategy.class), any(Strategy.class), anyInt())).thenReturn(new Score());

        tournament.runTournamentInternal(strategies, 5);

        verify(runner).runMatch(eq(strategies[0]), eq(strategies[1]), eq(5));
        verify(runner).runMatch(eq(strategies[0]), eq(strategies[2]), eq(5));
        verify(runner).runMatch(eq(strategies[1]), eq(strategies[2]), eq(5));
    }

    @Test
    public void shouldAddPointsForMatchesAndReturnInOrderOfStrategiesPassedIn() {

        ExhaustiveTournament tournament = new ExhaustiveTournament(new MatchRunner());
        Strategy[] strategies = new Strategy[] { new Cooperator(), new Cooperator(), new Defector() };

        int[] points = tournament.runTournamentInternal(strategies, 1);

        assertEquals("Cooperator should have 2 points.", 2, points[0]);
        assertEquals("Cooperator should have 2 points.", 2, points[1]);
        assertEquals("Defector should have 6 points.", 6, points[2]);
    }

}
