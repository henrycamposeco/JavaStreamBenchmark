package com.test;

import org.openjdk.jmh.annotations.Scope;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by henry on 12/9/2016.
 */
@org.openjdk.jmh.annotations.State(Scope.Benchmark)
public class State {
    private static final int N = 10000000;
    final List<Double> list;

    public State() {
        list = new ArrayList<>(N);
        for (int i = 0; i < N; ++i)
            list.add((double) i);
    }
}