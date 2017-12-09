package com.mullekybernetik.ipd.tournament;

import com.mullekybernetik.ipd.strategies.*;
import com.mullekybernetik.ipd.strategies.basic.Cooperator;
import com.mullekybernetik.ipd.strategies.basic.Defector;
import com.mullekybernetik.ipd.strategies.basic.TitForTat;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TableTest {

    private Table table;
    private Strategy defector;
    private Strategy titForTat0;

    @Before
    public void setUp() {
        Strategy[] strategies = {new Cooperator(), new TitForTat(), new Defector(), new TitForTat()};
        Integer[] points = {0, 4, 6, 4};

        table = new Table(strategies, Arrays.stream(points).map(AtomicInteger::new).toArray(AtomicInteger[]::new));
        titForTat0 = strategies[1];
        defector = strategies[2];
    }

    @Test
    public void shouldReturnTopStrategyWithCorrectPoints() {
        List<TableEntry> result = table.getTopEntries(1);

        assertEquals("Should return only the winner", 1, result.size());
        assertEquals("Defector should be the winner", defector, result.get(0).getStrategy());
        assertEquals("Entry for winner should have correct points", 6, result.get(0).getPoints());
    }

    @Test
    public void shouldReturnTopTwoStrategies() {
        Collection<TableEntry> result = table.getTopEntries(2);

        assertEquals("Should return the top 2", 2, result.size());
        List<Strategy> winners = result.stream().map(TableEntry::getStrategy).collect(toList());
        assertEquals("Defector should be the winner", defector, winners.get(0));
        assertEquals("Tit-for-tat should be runner-up", titForTat0.toString(), winners.get(1).toString());
    }

}
