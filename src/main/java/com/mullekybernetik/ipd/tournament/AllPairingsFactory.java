package com.mullekybernetik.ipd.tournament;

import com.mullekybernetik.ipd.strategies.Strategy;

import java.util.ArrayList;
import java.util.List;

public class AllPairingsFactory implements PairingFactory {

    List<Pairing> pairings;

    @Override
    public List<Pairing> getPairingsForStrategies(List<Strategy> strategies) {

        int s = strategies.size();
        if ((pairings != null) && (pairings.size() == ((s*s)-s)/2)) {
            return pairings;
        }

        pairings = new ArrayList<>();
        for (int i = 0; i < strategies.size(); i++) {
            for (int j = i + 1; j < strategies.size(); j++) {
                pairings.add(new Pairing(i, j));
            }
        }
        return pairings;
    }

}
