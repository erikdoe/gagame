package com.mullekybernetik.gagame;

import com.mullekybernetik.gagame.match.Referee;
import com.mullekybernetik.gagame.match.Strategy;
import com.mullekybernetik.gagame.strategies.Defector;
import com.mullekybernetik.gagame.strategies.TitForTat;
import com.mullekybernetik.gagame.strategies.TreeEncoded;
import com.mullekybernetik.gagame.tournament.ExhaustiveTournament;
import com.mullekybernetik.gagame.tournament.ScoreTable;
import com.mullekybernetik.gagame.tournament.Tournament;

import java.util.Random;


public class RandomCodedStrategyRunner {

    static private Random generator = new Random();

    protected Strategy randomStrategy;
    protected Strategy[] players;

    public RandomCodedStrategyRunner(int playerCount) {
        randomStrategy = getRandomTreeEncoded(4);
        players = new Strategy[playerCount];
        for (int j = 0; j < playerCount; j++)
            players[j] = getRandomPlayer();
    }

    private Strategy getRandomPlayer() {
        switch (generator.nextInt(2)) {
            case 0:
                return randomStrategy;
            case 1:
                return new TitForTat();
            case 2:
                return new Defector();
            default:
                throw new IllegalStateException("Should never get here.");
        }
    }

    private Strategy getRandomTreeEncoded(int depth) {
        char[] encoding = new char[1 << depth];
        for (int i = 0; i < encoding.length; i++)
            encoding[i] = generator.nextBoolean() ? 'C' : 'D';
        return new TreeEncoded(new String(encoding));
    }

    public ScoreTable runTournaments(int tournaments, int roundsPerMatch) {
        Tournament tournament = new ExhaustiveTournament(new Referee(), roundsPerMatch);
        return tournament.runTournament(players, tournaments);
    }

}