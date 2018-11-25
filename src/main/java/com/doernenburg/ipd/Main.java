package com.doernenburg.ipd;

import com.doernenburg.ipd.searcher.GeneticSearcher;
import com.doernenburg.ipd.strategies.Strategy;
import com.doernenburg.ipd.strategies.encoded.ConditionalCooperatorFactory;
import com.doernenburg.ipd.tournament.AllPairingsFactory;
import com.doernenburg.ipd.tournament.PairingFactory;
import com.doernenburg.ipd.tournament.Table;
import com.doernenburg.ipd.tournament.Tournament;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {

    private static final int POPULATION_SIZE = 1000;
    private static final int TOURNAMENTS_PER_SEARCH = 5000;
    private static final int ROUNDS_PER_MATCH = 20;

    private final Searcher searcher;
    private final Writer writer;
    private final PairingFactory pairingFactory;
    private int stopConditionCounter;

    public static void main(String[] args) throws IOException {

        ConditionalCooperatorFactory strategyFactory = new ConditionalCooperatorFactory();
        strategyFactory.setDepth(3);
        strategyFactory.setBlockCount(3);

        Main main = new Main(
                new GeneticSearcher(strategyFactory),
                new AllPairingsFactory(),
                new BufferedWriter(new OutputStreamWriter(new FileOutputStream("ipd-out.txt"), StandardCharsets.UTF_8)));

        while (true) {
            main.runSearch();
        }
    }

    private Main(Searcher searcher, PairingFactory pairingFactory, Writer writer) {
        this.searcher = searcher;
        this.pairingFactory = pairingFactory;
        this.writer = writer;
    }

    private void runSearch() throws IOException {

        writer.write("\n*** NEW SEARCH ***\n\n");
        List<Strategy> population = searcher.createInitialPopulation(POPULATION_SIZE);
        stopConditionCounter = 0;

        for(int i = TOURNAMENTS_PER_SEARCH; i > 0; i--) {

            Tournament tournament = new Tournament(pairingFactory);
            Table result = tournament.runTournament(population, ROUNDS_PER_MATCH);
            population = searcher.createNextPopulation(POPULATION_SIZE, result);

            if ((i % 50 == 0) || (i == 1)) {
                boolean stopCondition = updateStopConditionCounter(result);
                writeResult(i, result, stopCondition);
                if (stopConditionCounter == 5) {
                    break;
                }
            }
        }
    }

    private boolean updateStopConditionCounter(Table result) {
        if ((double) result.getWinningStrategyCount() / POPULATION_SIZE > 0.95) {
            stopConditionCounter += 1;
            return true;
        }
        return false;
    }

    private void writeResult(int i, Table result, boolean stopCondition) throws IOException {
        String prefix = " ";
        if (stopCondition) {
            prefix = ((stopConditionCounter == 5) || (i == 1)) ? "@" : String.format("%d", 5 - stopConditionCounter);
        }
        writer.write(prefix);
        writeResult(i, result);
    }

    private void writeResult(int i, Table results) throws IOException {
        List<Table.EntryBucket> buckets = results.getEntryBuckets();
        writer.write(String.format("%6d; ", i));
        for (Table.EntryBucket b : buckets) {
            writer.write(String.format("(%3d, %-35s, %5d); ",
                    b.getCount(), b.getEntry().getStrategy(), b.getEntry().getPoints()));
        }
        writer.write("\n");
        writer.flush();
    }

}
