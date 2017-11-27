package com.mullekybernetik.gagame.match;

import com.mullekybernetik.gagame.strategies.Cooperator;
import com.mullekybernetik.gagame.strategies.Defector;
import org.junit.Assert;
import org.junit.Test;

import static com.mullekybernetik.gagame.match.Match.POINTS_FOR_SUCCESSFUL_BETRAYAL;

public class MatchRunnerTest {

    @Test
    public void shouldRunMultipleRoundsAndReturnScore() {
        MatchRunner matchRunner = new MatchRunner();
        Score score = matchRunner.runMatch(new Cooperator(), new Defector(), 2);

        Assert.assertEquals("Should have right number of points for cooperator", 0, score.a);
        Assert.assertEquals("Should have right number of points for defector", 2 * POINTS_FOR_SUCCESSFUL_BETRAYAL, score.b);
    }

}
