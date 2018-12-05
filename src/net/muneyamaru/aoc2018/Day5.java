package net.muneyamaru.aoc2018;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day5 extends Day {
    public Day5(File input) {
        super(input);
    }

    @Override
    protected String doProblem1(String input) {
        String oldPolymer = "";
        String newPolymer = input;

        while(!oldPolymer.equals(newPolymer)) {
            oldPolymer = newPolymer;
            newPolymer = react(newPolymer);
        }

        return String.valueOf(newPolymer.length());
    }

    private String react(String polymer) {
        for(int i = 0; i < polymer.length() - 1; i++) {
            char a = polymer.charAt(i), b = polymer.charAt(i+1);
            if(a == b+0x20 || a == b-0x20) {
                polymer = polymer.substring(0,i) + polymer.substring(i+2);
            }
        }
        return polymer;
    }

    @Override
    protected String doProblem2(String input) {
        int shortest = Integer.MAX_VALUE;
        for(char i = 'A'; i <= 'Z'; i++) {
            String polymer = input.replace(new String(new char[] { i }),"").replace(new String(new char[] { (char)(i+0x20) }),"");
            String oldPolymer = "";
            String newPolymer = polymer;

            while(!oldPolymer.equals(newPolymer)) {
                oldPolymer = newPolymer;
                newPolymer = react(newPolymer);
            }

            if(newPolymer.length() < shortest) shortest = newPolymer.length();
        }

        return String.valueOf(shortest);
    }
}
