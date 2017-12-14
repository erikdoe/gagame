package com.mullekybernetik.ipd;

import com.mullekybernetik.ipd.searcher.GeneticSearcher;
import com.mullekybernetik.ipd.strategies.Strategy;
import com.mullekybernetik.ipd.tournament.AllPairingsFactory;
import com.mullekybernetik.ipd.tournament.Table;
import com.mullekybernetik.ipd.tournament.Tournament;

import java.io.*;
import java.util.*;

public class Main {

    private static final int POPULATION_SIZE = 500;
    private static final int TOURNAMENTS_PER_SEARCH = 10_000;
    private static final int ROUNDS_PER_MATCH = 20;

    private static Searcher searcher;
    private static Writer writer;

    public static void main(String[] args) throws IOException {

        // TODO: check troughput
        // System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "12");

        searcher = new GeneticSearcher();
        writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("ipd-out.txt"), "utf-8"));

        AllPairingsFactory pairingFactory = new AllPairingsFactory();

        while (true) {

            List<Strategy> population = searcher.createInitialPopulation(POPULATION_SIZE);
            int earlyStopCounter = 0;

            for(int i = TOURNAMENTS_PER_SEARCH; i > 0; i--) {

                Tournament tournament = new Tournament(pairingFactory);
                Table result = tournament.runTournament(population, ROUNDS_PER_MATCH);

                if ((i % 100 == 0) || (i == 1)) {
                    if ((double) result.getWinningStrategyCount() / POPULATION_SIZE > 0.95) {
                        earlyStopCounter += 1;
                        writer.write(((earlyStopCounter == 5) || (i == 1)) ?
                                "@" : String.format("%d", 5 - earlyStopCounter));
                        writeResult(i, result);
                        if (earlyStopCounter == 5) {
                            break;
                        }
                    } else {
                        earlyStopCounter = 0;
                        writer.write(" ");
                        writeResult(i, result);
                    }
                }

                population = searcher.createNextPopulation(POPULATION_SIZE, result);
            }

            writer.write("\n*** NEW TOURNAMENT ***\n\n");
        }
    }

    private static void writeResult(int i, Table results) throws IOException {
        List<Table.EntryBucket> buckets = results.getEntryBuckets();
        writer.write(String.format("%6d; ", i));
        for (Table.EntryBucket b : buckets) {
            writer.write(String.format("(%3d, %-35s, %5d); ", b.getCount(), b.getEntry().getStrategy(), b.getEntry().getPoints()));
        }
        writer.write("\n");
        writer.flush();
    }

}
