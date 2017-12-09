package com.mullekybernetik.ipd.strategies.encoded;

import com.mullekybernetik.ipd.strategies.StrategyFactory;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DecisionTreeStrategyFactoryTest {

    @Test
    public void shouldCrossOverAtGivenPoint() {
        Random random = mock(Random.class);
        when(random.nextInt(8)).thenReturn(3);
        when(random.nextBoolean()).thenReturn(false);
        StrategyFactory factory = new DecisionTreeStrategyFactory(random);

        DecisionTreeStrategy c = factory.recombine(new DecisionTreeStrategy("+++++++"), new DecisionTreeStrategy("-------"));

        assertEquals("+++----", c.getStringRepresentation());
    }

    @Test
    public void shouldKeepFirstWhenCrossOverPointIsLength() {
        Random random = mock(Random.class);
        when(random.nextInt(8)).thenReturn(7);
        when(random.nextBoolean()).thenReturn(false);
        StrategyFactory factory = new DecisionTreeStrategyFactory(random);

        DecisionTreeStrategy c = factory.recombine(new DecisionTreeStrategy("+++++++"), new DecisionTreeStrategy("-------"));

        assertEquals("+++++++", c.getStringRepresentation());
    }

    @Test
    public void shouldKeepSecondWhenCrossOverPointIs0() {
        Random random = mock(Random.class);
        when(random.nextInt(8)).thenReturn(0);
        when(random.nextBoolean()).thenReturn(false);
        StrategyFactory factory = new DecisionTreeStrategyFactory(random);

        DecisionTreeStrategy c = factory.recombine(new DecisionTreeStrategy("+++++++"), new DecisionTreeStrategy("-------"));

        assertEquals("-------", c.getStringRepresentation());
    }

    @Test
    public void shouldStartWithSecondWhenFlipRandomIsTrue() {
        Random random = mock(Random.class);
        when(random.nextInt(8)).thenReturn(3);
        when(random.nextBoolean()).thenReturn(true);
        StrategyFactory factory = new DecisionTreeStrategyFactory(random);

        DecisionTreeStrategy c = factory.recombine(new DecisionTreeStrategy("+++++++"), new DecisionTreeStrategy("-------"));

        assertEquals("---++++", c.getStringRepresentation());
    }


}