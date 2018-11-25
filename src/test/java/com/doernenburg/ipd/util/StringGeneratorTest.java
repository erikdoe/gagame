package com.doernenburg.ipd.util;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class StringGeneratorTest {

    @Test
    public void shouldCreateStringsWithCorrectNumberOfSoleAllowedChar() {

        StringGenerator generator = new StringGenerator("-");

        String s0 = generator.randomString(1);
        assertEquals("String should contain correct number of allowed char", "-", s0);

        String s1 = generator.randomString(3);
        assertEquals("String should contain correct number of allowed char", "---", s1);
    }

    @Test
    public void shouldCreateStringWithRandomCharactersFromSet() {

        Random random = mock(Random.class);
        when(random.nextInt(3)).thenReturn(1);
        StringGenerator generator = new StringGenerator("XYZ", random);

        String s0 = generator.randomString(2);
        assertEquals("String should have specified length and 'random' char", "YY", s0);
    }

}