package com.mullekybernetik.gagame.match;

import com.mullekybernetik.gagame.strategies.Cooperator;
import com.mullekybernetik.gagame.strategies.Defector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RefereeTest {

    private Referee ref;

    @Before
    public void setUp() {
        ref = new Referee();
    }

    @Test
    public void shouldAssignCorrectPointsForCC() {
        int[] points = ref.runMatch(new Cooperator(), new Cooperator(), 1);

        assertEquals(3, points[0]);
        assertEquals(3, points[1]);
    }

    @Test
    public void shouldAssignCorrectPointsForCD() {
        int[] points = ref.runMatch(new Cooperator(), new Defector(), 1);

        Assert.assertEquals(0, points[0]);
        Assert.assertEquals(5, points[1]);
    }

    @Test
    public void shouldAssignCorrectPointsForDC() {
        int[] points = ref.runMatch(new Defector(), new Cooperator(), 1);

        Assert.assertEquals(5, points[0]);
        Assert.assertEquals(0, points[1]);
    }

    @Test
    public void shouldAssignCorrectPointsForDD() {
        int[] points = ref.runMatch(new Defector(), new Defector(), 1);

        Assert.assertEquals(1, points[0]);
        Assert.assertEquals(1, points[1]);
    }

    @Test
    public void shouldProvideStrategiesWithResultsForSingleRound() {
        Strategy alice = mock(Strategy.class);
        when(alice.getMove()).thenReturn(Move.DEFECT);
        Strategy bob = mock(Strategy.class);
        when(bob.getMove()).thenReturn(Move.COOPERATE);
        Referee ref = new Referee();

        ref.runMatch(alice, bob, 1);

        verify(alice).newMatch();
        verify(alice).setOpponentsMove(Move.COOPERATE);
        verify(bob).newMatch();
        verify(bob).setOpponentsMove(Move.DEFECT);
    }

    @Test
    public void shouldRunMultipleRoundsAndCalculatePoints() {
        int points[] = ref.runMatch(new Cooperator(), new Defector(), 2);

        Assert.assertEquals("Should have right number of points for cooperator", 0, points[0]);
        Assert.assertEquals("Should have right number of points for defector", 10, points[1]);
    }

}
