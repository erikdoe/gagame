package com.mullekybernetik.ipd.tournament;

import com.mullekybernetik.ipd.strategies.Strategy;

import java.util.List;

public interface PairingFactory {

    List<Pairing> getPairingsForStrategies(List<Strategy> strategies);
}
