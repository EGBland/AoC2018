package net.muneyamaru.aoc2018;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day1 extends Day {

    public Day1(File input1) {
        super(input1);
    }

    @Override
    protected String doProblem1(String data) {
        return String.valueOf(Arrays.stream(data.replace("+","").split("\n")).mapToInt(s -> Integer.parseInt(s)).sum());
    }

    @Override
    protected String doProblem2(String data) {
        Set<Integer> encounteredFreqs = new HashSet<>();
        CircularArrayList<Integer> freqs = new CircularArrayList<>();
        Arrays.stream(data.replace("+","").split("\n")).mapToInt(s -> Integer.parseInt(s)).forEach(i -> freqs.add(i));
        int cfreq = 0;
        int n = 0;
        while(encounteredFreqs.add(cfreq)) {
            cfreq += freqs.get(n);
            n++;
        }

        return String.valueOf(cfreq);
    }
}
