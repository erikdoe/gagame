package com.mullekybernetik.ipd.tournament;

import com.mullekybernetik.ipd.strategies.Strategy;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Table {

    List<TableEntry> entries;

    public Table(List<TableEntry> entries) {
        this.entries = entries;
    }

    public Table(Strategy[] strategies, int[] points) {
        entries = new ArrayList<>(strategies.length);
        for (int i = 0; i < strategies.length; i++) {
            entries.add(new TableEntry(strategies[i], points[i]));
        }
    }

    public Table(Strategy[] strategies, AtomicInteger[] points) {
        entries = new ArrayList<>(strategies.length);
        for (int i = 0; i < strategies.length; i++) {
            entries.add(new TableEntry(strategies[i], points[i].get()));
        }
    }

    public List<TableEntry> getAllEntries() {
        List<TableEntry> result = new ArrayList<>(entries);
        Collections.sort(result);
        return result;
    }

    public Collection<TableEntry> getTopEntries(int count) {

        return getAllEntries().subList(0, count);

/*
        SortedSet<TableEntry> result = new TreeSet<>();
        TableEntry cutoffEntry = null;

        for (TableEntry entry : entries) {
            int comp = (cutoffEntry != null) ? entry.compareTo(cutoffEntry) : +1;
            if (comp > 0) {
                if (result.size() < count) {
                    result.add(entry);
                    cutoffEntry = entry;
                }
            } else if (comp == 0) {
                if (result.size() < count)
                    result.add(entry);
            } else if (comp < 0) {
                if (result.size() == count)
                    result.remove(cutoffEntry);
                result.add(entry);
                cutoffEntry = result.last();
            }
        }

        return result;
*/

    }

}
