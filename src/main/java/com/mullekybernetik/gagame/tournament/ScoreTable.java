package com.mullekybernetik.gagame.tournament;

import java.util.*;

public class ScoreTable {

    List<Score> scores;

    public ScoreTable(Score[] scores) {
        this.scores = Arrays.asList(scores);
    }

    public Collection<Score> getAllScores() {
        List<Score> result = new ArrayList<Score>(scores);
        Collections.sort(result);
        Collections.reverse(result);
        return result;
    }

    public Collection<Score> getTopScores(int count) {
        SortedSet<Score> result = new TreeSet<Score>();
        Score minScore = null;

        for (Score score : scores) {
            int comp = (minScore != null) ? score.compareTo(minScore) : -1;
            if (comp < 0) {
                if (result.size() < count) {
                    result.add(score);
                    minScore = score;
                }
            } else if (comp == 0) {
                if (result.size() < count)
                    result.add(score);
            } else if (comp > 0) {
                if (result.size() == count)
                    result.remove(minScore);
                result.add(score);
                minScore = result.first();
            }
        }

        return result;
    }

}
