package com.mullekybernetik.gagame.tournament;

import com.mullekybernetik.gagame.match.Referee;
import com.mullekybernetik.gagame.match.Strategy;
import com.mullekybernetik.gagame.strategies.Cooperator;
import com.mullekybernetik.gagame.strategies.Defector;
import com.mullekybernetik.gagame.strategies.RandomChoice;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ExhaustiveMatchesTest {

    @Test
    public void shouldRunMatchesBetweenAllParticipantsWithRightNumberOfRounds() {

        Referee referee = mock(Referee.class);
        ExhaustiveTournament tournament = new ExhaustiveTournament(referee, 5);
        Strategy[] strategies = new Strategy[] { new Cooperator(), new Defector(), new RandomChoice() };

        when(referee.runMatch(any(Strategy.class), any(Strategy.class), anyInt())).thenReturn(new int[2]);

        tournament.runTournamentInternal(strategies);

        verify(referee).runMatch(eq(strategies[0]), eq(strategies[1]), eq(5));
        verify(referee).runMatch(eq(strategies[0]), eq(strategies[2]), eq(5));
        verify(referee).runMatch(eq(strategies[1]), eq(strategies[2]), eq(5));
    }

    @Test
    public void shouldAddPointsForMatchesAndReturnsInRightOrder() { // the "in order" bit is not so important but it makes our test easier...

        Strategy[] strategies = new Strategy[] { new Cooperator(), new Cooperator(), new Defector() };
        ExhaustiveTournament tournament = new ExhaustiveTournament(new Referee(), 1);

        Score[] scores = tournament.runTournamentInternal(strategies);

        assertEquals("Scores should be in same order as array passed in.", strategies[0], scores[0].getStrategy());
        assertEquals("Cooperator should have 3 points.", 3, scores[0].getPoints());
        assertEquals("Scores should be in same order as array passed in.", strategies[2], scores[2].getStrategy());
        assertEquals("Defector should have 10 points.", 10, scores[2].getPoints());
    }

}
