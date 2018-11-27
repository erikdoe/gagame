package com.doernenburg.ipd.tournament;

import com.doernenburg.ipd.strategies.basic.Cooperator;
import com.doernenburg.ipd.strategies.basic.Defector;
import com.doernenburg.ipd.strategies.Strategy;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.doernenburg.ipd.match.Match.POINTS_FOR_SUCCESSFUL_BETRAYAL;
import static com.doernenburg.ipd.match.Match.POINTS_FOR_UNSUCCESSFUL_COOPERATION;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TournamentTest {

    @Test
    public void shouldAddPointsForMatches() {

        Tournament tournament = new Tournament(strategies -> Arrays.asList(new Pairing(0, 1)), 1, 0);
        List<Strategy> strategies = Arrays.asList(new Cooperator(), new Defector());

        Table table = tournament.runTournament(strategies);
        Iterator<Table.Entry> resultIterator = table.getAllEntries().iterator();

        Table.Entry result0 = resultIterator.next();
        assertEquals("Defector should be winner", new Defector(), result0.getStrategy());
        assertEquals("Defector should have points for successful betrayal", POINTS_FOR_SUCCESSFUL_BETRAYAL, result0.getPoints());

        Table.Entry result1 = resultIterator.next();
        assertEquals("Cooperator should be loser", new Cooperator(), result1.getStrategy());
        assertEquals("Cooperator should have points for unsuccessful cooperation", POINTS_FOR_UNSUCCESSFUL_COOPERATION, result1.getPoints());

        assertFalse("There should only be two results", resultIterator.hasNext());
    }

}