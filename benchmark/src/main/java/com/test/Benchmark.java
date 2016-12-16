package com.test;

import org.junit.Test;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * Created by henry on 12/8/2016.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class Benchmark {
    private static final int WARMUP_ITERATIONS = 10;
    private static final int MEASURE_ITERATIONS = 10;
    private static final int FORKS = 1;


    //**DADA UNA LISTA DE NUMEROS, BUSCAR EL DE MENOR VALOR *//


    /**********************************

     FUNCIONAL (STREAMS)

     **********************************/
    @org.openjdk.jmh.annotations.Benchmark
    public Double streamGetMin(State state) {
        return state.list.stream().min(Double::compare).get();
    }

    @org.openjdk.jmh.annotations.Benchmark
    public double streamMapGetMin(State state) {
        return state.list.stream().mapToDouble(i -> i).min().getAsDouble();
    }

    @org.openjdk.jmh.annotations.Benchmark
    public double streamMapGetMinParallel(State state) {
        return state.list.parallelStream().mapToDouble(i -> i).min().getAsDouble();
    }

    @org.openjdk.jmh.annotations.Benchmark
    public Double streamGetMinParallel(State state) {
        return state.list.parallelStream().min(Double::compare).get();
    }



    /**********************************

     IMPERATIVO (CLASICO)

     **********************************/
    @org.openjdk.jmh.annotations.Benchmark
    public double loopGetMin(State state) {
        double min = Double.MAX_VALUE;
        for (Double d : state.list) {
            if (d < min) {
                min = d;
            }
        }
        return min;
    }

    @org.openjdk.jmh.annotations.Benchmark
    public double forEachGetMin(State state) {
        double[] min = {Double.MAX_VALUE};
        state.list.forEach(d -> {
            if (d < min[0]) {
                min[0] = d;
            }
        });
        return min[0];
    }




    /**********************************

     CONFIGURACION DEL TEST

     **********************************/
    @Test
    public void runJmh() throws RunnerException, IOException {
        final Options opt = new OptionsBuilder()
//                .jvmArgs("-XX:+UnlockCommercialFeatures")
                .include(Benchmark.class.getSimpleName())
                .warmupIterations(WARMUP_ITERATIONS)
                .measurementIterations(MEASURE_ITERATIONS)
                .forks(FORKS)
                .build();
        new Runner(opt).run();
    }
}
