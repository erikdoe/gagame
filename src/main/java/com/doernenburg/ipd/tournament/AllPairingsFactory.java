package com.doernenburg.ipd.tournament;

import com.doernenburg.ipd.strategies.Strategy;

import java.util.ArrayList;
import java.util.List;

public class AllPairingsFactory implements PairingFactory {

    private List<Pairing> pairings;

    @Override
    public List<Pairing> getPairingsForStrategies(List<Strategy> strategies) {

        int n = strategies.size();
        if ((pairings != null) && (pairings.size() == ((n*n)+n)/2)) {
            return pairings;
        }

        pairings = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                pairings.add(new Pairing(i, j));
            }
        }
        return pairings;
    }

}
