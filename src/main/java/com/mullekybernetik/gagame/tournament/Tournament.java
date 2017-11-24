package com.mullekybernetik.gagame.tournament;

import com.mullekybernetik.gagame.strategies.Strategy;

import java.util.List;

public interface Tournament {

    public Table runTournament(List<Strategy> strategies, int roundsPerMatch);

}
