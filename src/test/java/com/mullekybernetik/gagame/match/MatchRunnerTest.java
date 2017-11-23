package com.mullekybernetik.gagame.match;

import com.mullekybernetik.gagame.strategies.Cooperator;
import com.mullekybernetik.gagame.strategies.Defector;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MatchRunnerTest {

    @Test
    public void shouldRunMultipleRoundsAndReturnScore() {
        MatchRunner matchRunner = new MatchRunner();
        Score score = matchRunner.runMatch(new Cooperator(), new Defector(), 2);

        Assert.assertEquals("Should have right number of points for cooperator", 0, score.a);
        Assert.assertEquals("Should have right number of points for defector", 6, score.b);
    }

}
