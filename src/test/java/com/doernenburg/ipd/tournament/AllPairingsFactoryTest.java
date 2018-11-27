package com.doernenburg.ipd.tournament;

import com.doernenburg.ipd.strategies.Strategy;
import com.doernenburg.ipd.strategies.basic.Cooperator;
import com.doernenburg.ipd.strategies.basic.Copycat;
import com.doernenburg.ipd.strategies.basic.Defector;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AllPairingsFactoryTest {

    @Test
    public void shouldAddPairingsWithOpponentAndSelf() {

        AllPairingsFactory factory = new AllPairingsFactory();
        List<Strategy> strategies = Arrays.asList(new Cooperator(), new Defector());

        List<Pairing> pairings = factory.getPairingsForStrategies(strategies);

        Pairing p0 = pairings.get(0);
        assertEquals("Should pair first strategy with itself", 0, p0.aIdx);
        assertEquals("Should pair first strategy with itself", 0, p0.bIdx);
        Pairing p1 = pairings.get(1);
        assertEquals("Should pair second strategy with first", 0, p1.aIdx);
        assertEquals("Should pair second strategy with first", 1, p1.bIdx);
        Pairing p2 = pairings.get(2);
        assertEquals("Should pair second strategy with itself", 1, p2.aIdx);
        assertEquals("Should pair second strategy with itself", 1, p2.bIdx);
    }

    @Test
    public void shouldAddParingsWithAllOthersAndSelf() {

        AllPairingsFactory factory = new AllPairingsFactory();
        List<Strategy> strategies = Arrays.asList(new Cooperator(), new Defector(), new Copycat());

        List<Pairing> pairings = factory.getPairingsForStrategies(strategies);

        assertEquals("Should pair each against all", 6, pairings.size());
    }

}