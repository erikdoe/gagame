package com.mullekybernetik.ipd.tournament;

import com.mullekybernetik.ipd.strategies.*;
import com.mullekybernetik.ipd.strategies.basic.Cooperator;
import com.mullekybernetik.ipd.strategies.basic.Defector;
import com.mullekybernetik.ipd.strategies.basic.TitForTat;
import com.mullekybernetik.ipd.strategies.encoded.ConditionalDefector;
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
    private Strategy conditional;
    private Strategy titForTat;
    private Table table;

    @Before
    public void setUp() {
        defector = new Defector();
        cooperator = new Cooperator();
        conditional = new ConditionalDefector("");
        titForTat = new TitForTat();
        table = new Table(new Strategy[]{cooperator, defector, conditional, titForTat, conditional}, new int[]{2, 6, 4, 5, 4});
    }

    @Test
    public void shouldReturnTopStrategy() {
        Collection<TableEntry> result = table.getTopEntries(1);

        assertEquals("Should return only the winner", 1, result.size());
        List<Strategy> winners = getStrategies(result);
        assertEquals("Defector should be the winner", defector, winners.get(0));
    }

    @Test
    public void shouldReturnTopTwoStrategies() {
        Collection<TableEntry> result = table.getTopEntries(2);

        assertEquals("Should return the top 2", 2, result.size());
        List<Strategy> winners = getStrategies(result);
        assertEquals("Defector should be the winner", defector, winners.get(0));
        assertEquals("Tit-for-tat should be runner-up", titForTat, winners.get(1));
    }

    @Test
    public void shouldSelectWithAmbiguity() {
        Collection<TableEntry> result = table.getTopEntries(3);

        assertEquals("Should return three winners", 3, result.size());
        List<Strategy> winners = getStrategies(result);
        assertEquals("Defector should be the winner", defector, winners.get(0));
        assertEquals("Tit-for-tat should be runner-up", titForTat, winners.get(1));
        assertEquals("Random should be third", conditional, winners.get(2));
    }

    private List<Strategy> getStrategies(Collection<TableEntry> entries) {
        ArrayList<Strategy> players = new ArrayList<Strategy>();
        for (TableEntry e : entries)
            players.add(e.getStrategy());
        return players;
    }

}
