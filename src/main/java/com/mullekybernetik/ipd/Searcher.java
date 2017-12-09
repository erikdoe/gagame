package com.mullekybernetik.ipd;

import com.mullekybernetik.ipd.strategies.Strategy;
import com.mullekybernetik.ipd.tournament.Table;

import java.util.List;

public interface Searcher {

    List<Strategy> createInitialPopulation(int populationSize);
    List<Strategy> createNextPopulation(int populationSize, Table result);

}
