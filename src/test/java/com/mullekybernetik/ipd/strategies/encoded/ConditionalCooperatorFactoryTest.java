package com.mullekybernetik.ipd.strategies.encoded;

import com.mullekybernetik.ipd.strategies.StrategyFactory;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConditionalCooperatorFactoryTest {

    @Test
    public void shouldCrossOverAtGivenPoint() {
        Random random = mock(Random.class);
        when(random.nextInt(5)).thenReturn(2);
        when(random.nextBoolean()).thenReturn(false);
        StrategyFactory factory = new ConditionalCooperatorFactory(random);

        ConditionalCooperator s0 = new ConditionalCooperator("CCCC");
        ConditionalCooperator s1 = new ConditionalCooperator("DDDDDD");
        ConditionalCooperator c = factory.recombine(s0, s1);

        assertEquals("CCDDDD", c.getStringRepresentation());
    }

    @Test
    public void shouldStartWithSecondWhenFlipRandomIsTrue() {
        Random random = mock(Random.class);
        when(random.nextInt(5)).thenReturn(1);
        when(random.nextBoolean()).thenReturn(true);
        StrategyFactory factory = new ConditionalCooperatorFactory(random);

        ConditionalCooperator s0 = new ConditionalCooperator("CCCCC");
        ConditionalCooperator s1 = new ConditionalCooperator("DDDD");
        ConditionalCooperator c = factory.recombine(s0, s1);

        assertEquals("DCCCC", c.getStringRepresentation());
    }

}