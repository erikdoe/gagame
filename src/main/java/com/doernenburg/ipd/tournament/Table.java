package com.doernenburg.ipd.tournament;

import com.doernenburg.ipd.strategies.Strategy;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Table {

    private List<Entry> entries;

    public Table(Strategy[] strategies, AtomicInteger[] points) {
        entries = new ArrayList<>(strategies.length);
        for (int i = 0; i < strategies.length; i++) {
            entries.add(new Entry(strategies[i], points[i].get()));
        }
        Collections.sort(entries);
    }

    public List<Entry> getAllEntries() {
        return entries;
    }

    public List<Entry> getTopEntries(int count) {
        return entries.subList(0, count);
    }

    public int getWinningStrategyCount() {
        Strategy winningStrategy = entries.get(0).getStrategy();
        int count = 0;
        for (Table.Entry e : entries) {
            if (!e.getStrategy().equals(winningStrategy)) {
                break;
            }
            count += 1;
        }
        return count;
    }

    public List<EntryBucket> getEntryBuckets() {
        List<EntryBucket> buckets = new ArrayList<>();
        Table.Entry current = null;
        int count = 0;
        for (Table.Entry e : entries) {
            if (current == null) {
                current = e;
                count = 1;
            } else if (!current.getStrategy().equals(e.getStrategy())) {
                buckets.add(new EntryBucket(current, count));
                current = e;
                count = 1;
            } else {
                count += 1;
            }
        }
        buckets.add(new EntryBucket(current, count));
        return buckets;
    }


    public static class Entry implements Comparable<Entry> {

        private final Strategy strategy;
        private final int points;

        public Entry(Strategy strategy, int points) {
            this.strategy = strategy;
            this.points = points;
        }

        public Strategy getStrategy() {
            return strategy;
        }

        public int getPoints() {
            return points;
        }

        @Override
        public int compareTo(Entry other) {
            return (other.points - this.points);  // order descending
        }

    }


    public static class EntryBucket {

        private final Entry entry;
        private final int count;

        public EntryBucket(Entry entry, int count) {
            this.entry = entry;
            this.count = count;
        }

        public Entry getEntry() {
            return entry;
        }

        public int getCount() {
            return count;
        }
    }

}
