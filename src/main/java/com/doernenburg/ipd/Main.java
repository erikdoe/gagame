package com.doernenburg.ipd;

import com.doernenburg.ipd.searcher.GeneticSearcher;
import com.doernenburg.ipd.searcher.Searcher;
import com.doernenburg.ipd.strategies.Strategy;
import com.doernenburg.ipd.strategies.encoded.DecisionTreeStrategyFactory;
import com.doernenburg.ipd.tournament.AllPairingsFactory;
import com.doernenburg.ipd.tournament.PairingFactory;
import com.doernenburg.ipd.tournament.Table;
import com.doernenburg.ipd.tournament.Tournament;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {

    private static final int POPULATION_SIZE = 100;
    private static final int NUM_RUNS = 20;
    private static final int NUM_GENERATIONS = 50;
    private static final int GAMES_PER_MATCH = 100;
    private static final int MEMORY_CAPACITY = 3;
    private static final double MISTAKE_PROB = 0.001;

    private static Searcher searcher;
    private static Writer writer;
    private static PairingFactory pairingFactory;

    public static void main(String[] args) throws IOException {
        searcher = new GeneticSearcher(new DecisionTreeStrategyFactory(MEMORY_CAPACITY));
        pairingFactory = new AllPairingsFactory();
        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("ipd-out.txt"), StandardCharsets.UTF_8));

        for (int r = 0; r < NUM_RUNS; r++) {
            doOneRun();
        }
    }

    private static void doOneRun() throws IOException {
        writer.write("\n*** NEW RUN ***\n\n");
        List<Strategy> population = searcher.createInitialPopulation(POPULATION_SIZE);
        for (int i = 1; i <= NUM_GENERATIONS; i++) {
            Tournament tournament = new Tournament(pairingFactory, GAMES_PER_MATCH, MISTAKE_PROB);
            Table result = tournament.runTournament(population);
            writeResult(i, result);
            population = searcher.createNextPopulation(POPULATION_SIZE, result);
        }
    }

    private static void writeResult(int i, Table results) throws IOException {
        writer.write((i == NUM_GENERATIONS) ? "@" : " ");
        writer.write(String.format("%4d; ", i));
        for (Table.EntryBucket b : results.getEntryBuckets()) {
            writer.write(String.format("(%3d, %5d, %-20s); ",
                    b.getCount(), b.getEntry().getPoints(), b.getEntry().getStrategy()));
        }
        writer.write("\n");
        writer.flush();
    }

}
