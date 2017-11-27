package com.mullekybernetik.gagame.tournament;

import com.mullekybernetik.gagame.strategies.Strategy;

import java.util.ArrayList;
import java.util.List;

public class AllPairingsFactory implements PairingFactory {

    @Override
    public List<Pairing> getPairingsForStrategies(List<Strategy> strategies) {
        List<Pairing> pairings = new ArrayList<>();
        for (int i = 0; i < strategies.size(); i++) {
            for (int j = i + 1; j < strategies.size(); j++) {
                pairings.add(new Pairing(i, j));
            }
        }
        return pairings;
    }

}
