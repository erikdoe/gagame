package com.mullekybernetik.ipd.tournament;

import com.mullekybernetik.ipd.strategies.Strategy;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Table {

    List<TableEntry> entries;

    public Table(Strategy[] strategies, AtomicInteger[] points) {
        entries = new ArrayList<>(strategies.length);
        for (int i = 0; i < strategies.length; i++) {
            entries.add(new TableEntry(strategies[i], points[i].get()));
        }
        Collections.sort(entries);
    }

    public List<TableEntry> getAllEntries() {
        return entries;
    }

    public List<TableEntry> getTopEntries(int count) {
        return entries.subList(0, count);
    }

}
