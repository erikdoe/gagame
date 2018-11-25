package com.doernenburg.ipd.tournament;

import com.doernenburg.ipd.strategies.Strategy;

import java.util.List;

public interface PairingFactory {

    List<Pairing> getPairingsForStrategies(List<Strategy> strategies);
}
