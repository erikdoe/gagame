package com.doernenburg.ipd.tournament;

import com.doernenburg.ipd.strategies.Strategy;
import com.doernenburg.ipd.strategies.basic.Cooperator;
import com.doernenburg.ipd.strategies.basic.Defector;
import com.doernenburg.ipd.strategies.basic.Copycat;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

public class TableTest {

    private Table table;
    private Strategy defector;
    private Strategy titForTat0;

    @Before
    public void setUp() {
        Strategy[] strategies = {new Cooperator(), new Copycat(), new Defector(), new Copycat()};
        Integer[] points = {0, 4, 6, 4};

        table = new Table(strategies, Arrays.stream(points).map(AtomicInteger::new).toArray(AtomicInteger[]::new));
        titForTat0 = strategies[1];
        defector = strategies[2];
    }

    @Test
    public void shouldReturnTopStrategyWithCorrectPoints() {
        List<Table.Entry> result = table.getTopEntries(1);

        assertEquals("Should return only the winner", 1, result.size());
        assertEquals("Defector should be the winner", defector, result.get(0).getStrategy());
        assertEquals("Entry for winner should have correct points", 6, result.get(0).getPoints());
    }

    @Test
    public void shouldReturnTopTwoStrategies() {
        Collection<Table.Entry> result = table.getTopEntries(2);

        assertEquals("Should return the top 2", 2, result.size());
        List<Strategy> winners = result.stream().map(Table.Entry::getStrategy).collect(toList());
        assertEquals("Defector should be the winner", defector, winners.get(0));
        assertEquals("Tit-for-tat should be runner-up", titForTat0.toString(), winners.get(1).toString());
    }

    @Test
    public void shouldReturnWinnerCount() {
        int count = table.getWinningStrategyCount();

        assertEquals(1, count);
    }

    @Test
    public void shouldReturnResultsBuckets() {
        List<Table.EntryBucket> buckets = table.getEntryBuckets();

        assertEquals("Should have grouped into three buckets.", 3, buckets.size());
        assertEquals("Defector should be in first bucket", defector, buckets.get(0).getEntry().getStrategy());
        assertEquals("There should be one defector", 1, buckets.get(0).getCount());
        assertEquals("Tit-for-tat should be in second bucket", titForTat0.toString(), buckets.get(1).getEntry().getStrategy().toString());
        assertEquals("There should be two entries in tit-for-tat bucket", 2, buckets.get(1).getCount());

    }
}
