package com.mullekybernetik.gagame.tournament;

import com.mullekybernetik.gagame.match.Strategy;
import com.mullekybernetik.gagame.strategies.Cooperator;
import com.mullekybernetik.gagame.strategies.Defector;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ScoreTableTest {

    private Strategy defector;
    private Strategy cooperator1;
    private Strategy cooperator2;
    private ScoreTable table;

    @Before
    public void setUp() {
        defector = new Defector();
        cooperator1 = new Cooperator();
        cooperator2 = new Cooperator();
        Score[] scores = new Score[]{new Score(cooperator1), new Score(defector), new Score(cooperator2)};
        scores[1].addToPoints(5);
        table = new ScoreTable(scores);
    }

    @Test
    public void shouldReturnTopScores() {
        Collection<Score> result = table.getTopScores(1);

        assertEquals("Should return only the winner", 1, result.size());
        List<Strategy> winners = getPlayers(result);
        assertTrue("Defector should be the winner", winners.contains(defector));
    }

    @Test
    public void shouldSelectBestTwoFromGroupWithAmbiguity() {
        Collection<Score> result = table.getTopScores(2);

        assertEquals("Should return two winners", 2, result.size());
        List<Strategy> winners = getPlayers(result);
        assertTrue("Defector should be a winner", winners.contains(defector));
        assertTrue("A cooperator should be a winner", winners.contains(cooperator1) || winners.contains(cooperator2));
    }

    private List<Strategy> getPlayers(Collection<Score> scores) {
        ArrayList<Strategy> players = new ArrayList<Strategy>();
        for (Score s : scores)
            players.add(s.getStrategy());
        return players;
    }

}
