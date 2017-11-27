package com.mullekybernetik.gagame.tournament;

import com.mullekybernetik.gagame.match.MatchRunner;
import com.mullekybernetik.gagame.match.Score;
import com.mullekybernetik.gagame.strategies.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TournamentTest {

    @Test
    public void shouldRunMatchesForAllPairingsWithRightNumberOfRounds() {

        MatchRunner runner = mock(MatchRunner.class);
        Tournament tournament = new Tournament(runner, new AllPairingsFactory());
        List<Strategy> strategies = Arrays.asList(new Cooperator(), new Defector(), new TitForTat());
        when(runner.runMatch(any(Strategy.class), any(Strategy.class), anyInt())).thenReturn(new Score());

        tournament.runTournament(strategies, 5);

        verify(runner).runMatch(eq(strategies.get(0)), eq(strategies.get(1)), eq(5));
        verify(runner).runMatch(eq(strategies.get(0)), eq(strategies.get(2)), eq(5));
        verify(runner).runMatch(eq(strategies.get(1)), eq(strategies.get(2)), eq(5));
    }

    @Test
    public void shouldAddPointsForMatches() {

        Tournament tournament = new Tournament(new MatchRunner(), new AllPairingsFactory());
        List<Strategy> strategies = Arrays.asList(new Cooperator(), new Defector());

        Table table = tournament.runTournament(strategies, 1);
        Iterator<TableEntry> resultIterator = table.getAllEntries().iterator();

        TableEntry result0 = resultIterator.next();
        assertEquals("Defector should be winner", new Defector(), result0.getStrategy());
        assertEquals("Defector should have 6 points", 3, result0.getPoints());

        TableEntry result1 = resultIterator.next();
        assertEquals("Cooperator should be loser", new Cooperator(), result1.getStrategy());
        assertEquals("Cooperator should have 2 points", 0, result1.getPoints());

        assertFalse("There should only be two results", resultIterator.hasNext());
    }

}