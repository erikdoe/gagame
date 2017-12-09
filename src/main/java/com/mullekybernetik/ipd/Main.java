package com.mullekybernetik.ipd;

import com.mullekybernetik.ipd.searcher.GeneticSearcher;
import com.mullekybernetik.ipd.strategies.Strategy;
import com.mullekybernetik.ipd.tournament.AllPairingsFactory;
import com.mullekybernetik.ipd.tournament.Table;
import com.mullekybernetik.ipd.tournament.TableEntry;
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
                    if (logProgress(i, result)) {
                        earlyStopCounter += 1;
                        if (earlyStopCounter == 5) {
                            break;
                        }
                    } else {
                        earlyStopCounter = 0;
                    }
                }

                population = searcher.createNextPopulation(POPULATION_SIZE, result);
            }

            writer.write("\n**** NEW TOURNAMENT ****\n\n");
        }
    }


    private static boolean logProgress(int i, Table result) throws IOException {
        List<TableEntry> allEntries = result.getAllEntries();

        writer.write(String.format("%6d; ", i));
        TableEntry current = null;
        int count = 0;
        boolean isWinner = true;
        int winnerCount = 0;
        for (TableEntry e : allEntries) {
            if (current == null) {
                current = e;
                count = 1;
            } else if (!current.getStrategy().equals(e.getStrategy())) {
                writer.write(String.format("(%3d, %-35s, %5d); ", count, current.getStrategy(), current.getPoints()));
                current = e;
                if (isWinner) {
                    winnerCount += count;
                }
                isWinner = false;
                count = 1;
            } else {
                count += 1;
            }
        }
        writer.write(String.format("(%3d, %-35s, %5d); ", count, current.getStrategy(), current.getPoints()));
        writer.write("\n");
        writer.flush();

        return (double)winnerCount / allEntries.size()  > 0.95;
    }

}
