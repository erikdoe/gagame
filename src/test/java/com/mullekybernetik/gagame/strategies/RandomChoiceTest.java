package com.mullekybernetik.gagame.strategies;

import com.mullekybernetik.gagame.match.Move;
import junit.framework.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RandomChoiceTest {

    private static final int TESTCOUNT = 1000;

    @Test
    public void shouldReturnEvenlyDistributedMoves() {
        RandomChoice randomStrategy = new RandomChoice();
        int defectCount = 0;
        for (int i = 0; i < TESTCOUNT; i++) {
            if (randomStrategy.getMove() == Move.DEFECT)
                defectCount++;
        }
        assertTrue("Should have fairly even distribution", defectCount > TESTCOUNT * 40 / 100);
    }

}
