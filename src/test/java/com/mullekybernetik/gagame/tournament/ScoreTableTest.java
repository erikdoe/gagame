package com.mullekybernetik.gagame.tournament;

import com.mullekybernetik.gagame.match.Strategy;
import com.mullekybernetik.gagame.strategies.Cooperator;
import com.mullekybernetik.gagame.strategies.Defector;
import com.mullekybernetik.gagame.strategies.RandomChoice;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ScoreTableTest {

    private Strategy defector;
    private Strategy cooperator;
    private Strategy random;
    private ScoreTable table;

    @Before
    public void setUp() {
        defector = new Defector();
        cooperator = new Cooperator();
        random = new RandomChoice();
        Score[] scores = new Score[]{new Score(cooperator, 4), new Score(defector, 5), new Score(random, 3)};
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
        assertTrue("A cooperator should be a winner", winners.contains(cooperator) || winners.contains(random));
    }

    private List<Strategy> getPlayers(Collection<Score> scores) {
        ArrayList<Strategy> players = new ArrayList<Strategy>();
        for (Score s : scores)
            players.add(s.getStrategy());
        return players;
    }

}
