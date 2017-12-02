package com.mullekybernetik.ipd.tournament;

import com.mullekybernetik.ipd.match.MatchRunner;
import com.mullekybernetik.ipd.match.Score;
import com.mullekybernetik.ipd.strategies.basic.Cooperator;
import com.mullekybernetik.ipd.strategies.basic.Defector;
import com.mullekybernetik.ipd.strategies.Strategy;
import com.mullekybernetik.ipd.strategies.basic.TitForTat;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.mullekybernetik.ipd.match.Match.POINTS_FOR_SUCCESSFUL_BETRAYAL;
import static com.mullekybernetik.ipd.match.Match.POINTS_FOR_UNSUCCESSFUL_COOPERATION;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

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
        assertEquals("Defector should have points for successful betrayal", POINTS_FOR_SUCCESSFUL_BETRAYAL, result0.getPoints());

        TableEntry result1 = resultIterator.next();
        assertEquals("Cooperator should be loser", new Cooperator(), result1.getStrategy());
        assertEquals("Cooperator should have points for unsuccessful cooperation", POINTS_FOR_UNSUCCESSFUL_COOPERATION, result1.getPoints());

        assertFalse("There should only be two results", resultIterator.hasNext());
    }

}