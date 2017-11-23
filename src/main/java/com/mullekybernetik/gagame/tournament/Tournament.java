package com.mullekybernetik.gagame.tournament;

import com.mullekybernetik.gagame.match.Strategy;

public interface Tournament {

    public Table runTournament(Strategy[] strategies, int roundsPerMatch);

}
