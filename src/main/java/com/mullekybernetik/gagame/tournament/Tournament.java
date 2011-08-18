package com.mullekybernetik.gagame.tournament;

import com.mullekybernetik.gagame.match.Strategy;

public interface Tournament {

    public ScoreTable runTournament(Strategy[] strategies);

}
