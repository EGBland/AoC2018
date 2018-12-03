package net.muneyamaru.aoc2018;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        List<Day> days = new ArrayList<>();

        days.add(new Day1(new File("input_01.txt")));

        days.forEach(d -> {
            System.out.println(d.solveProblem1());
            System.out.println(d.solveProblem2());
        });
    }
}
