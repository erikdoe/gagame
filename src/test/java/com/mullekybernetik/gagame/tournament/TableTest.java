package com.mullekybernetik.gagame.tournament;

import com.mullekybernetik.gagame.match.Strategy;
import com.mullekybernetik.gagame.strategies.Cooperator;
import com.mullekybernetik.gagame.strategies.Defector;
import com.mullekybernetik.gagame.strategies.RandomChoice;
import com.mullekybernetik.gagame.strategies.TitForTat;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TableTest {

    private Strategy defector;
    private Strategy cooperator;
    private Strategy random;
    private Strategy titForTat;
    private Table table;

    @Before
    public void setUp() {
        defector = new Defector();
        cooperator = new Cooperator();
        random = new RandomChoice();
        titForTat = new TitForTat();
        table = new Table(new Strategy[]{cooperator, defector, random, titForTat}, new int[]{2, 5, 4, 4});
    }

    @Test
    public void shouldReturnTopStrategies() {
        Collection<TableEntry> result = table.getTopEntries(1);

        assertEquals("Should return only the winner", 1, result.size());
        List<Strategy> winners = getStrategies(result);
        assertTrue("Defector should be the winner", winners.contains(defector));
    }

    @Test
    public void shouldSelectBestTwoFromGroupWithAmbiguity() {
        Collection<TableEntry> result = table.getTopEntries(2);

        assertEquals("Should return two winners", 2, result.size());
        List<Strategy> winners = getStrategies(result);
        assertTrue("Defector should be a winner", winners.contains(defector));
        assertTrue("A non-defector should be a winner, too", winners.contains(titForTat) || winners.contains(random));
    }

    private List<Strategy> getStrategies(Collection<TableEntry> entries) {
        ArrayList<Strategy> players = new ArrayList<Strategy>();
        for (TableEntry e : entries)
            players.add(e.getStrategy());
        return players;
    }

}
