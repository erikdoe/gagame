package com.mullekybernetik.gagame.tournament;

import com.mullekybernetik.gagame.strategies.Strategy;

import java.util.List;

public interface PairingFactory {

    List<Pairing> getPairingsForStrategies(List<Strategy> strategies);
}
