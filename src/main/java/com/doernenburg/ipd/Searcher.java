package com.doernenburg.ipd;

import com.doernenburg.ipd.tournament.Table;
import com.doernenburg.ipd.strategies.Strategy;

import java.util.List;

public interface Searcher {

    List<Strategy> createInitialPopulation(int populationSize);
    List<Strategy> createNextPopulation(int populationSize, Table result);

}
