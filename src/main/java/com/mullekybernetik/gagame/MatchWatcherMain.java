package com.mullekybernetik.gagame;

import com.mullekybernetik.gagame.match.Move;
import com.mullekybernetik.gagame.match.Referee;
import com.mullekybernetik.gagame.match.Strategy;
import com.mullekybernetik.gagame.strategies.TitForTat;
import com.mullekybernetik.gagame.strategies.TreeEncoded;

public class MatchWatcherMain {

    public static void main(String[] args) {
        Strategy tft = new TitForTat();
        Strategy enc = new TreeEncoded("CDDDDDCC");
        Referee ref = new LoggingReferee();
        ref.runMatch(tft, enc, 20);
    }

    private static class LoggingReferee extends Referee {
        protected void doRound() {
            Move moveA = a.getMove();
            Move moveB = b.getMove();
            super.doRound();
            System.out.printf("%s %s %3d %3d\n", moveA.toString(), moveB.toString(), points[0], points[1]);
        }

    }

}
