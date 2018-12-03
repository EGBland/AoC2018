package net.muneyamaru.aoc2018;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Day> days = new ArrayList<>();

        days.add(new Day1(new File("input_01.txt")));
        days.add(new Day2(new File("input_02.txt")));

        days.forEach(d -> {
            System.out.println(d.getClass().getSimpleName());
            System.out.println(d.solveProblem1());
            System.out.println(d.solveProblem2());
            System.out.println();
        });
    }
}
