package net.muneyamaru.aoc2018;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day2 extends Day {

    public Day2(File input) { super(input); }

    @Override
    protected String doProblem1(String input) {
        int twos = 0, threes = 0;
        String[] ids = input.split("\n");
        for(String id : ids) {
            Map<Character, Integer> freq = new HashMap<>();
            for(char c : id.toCharArray()) {
                if(freq.containsKey(c)) freq.put(c,freq.get(c)+1);
                else freq.put(c,1);
            }

            if(freq.containsValue(2)) twos++;
            if(freq.containsValue(3)) threes++;
        }
        return String.valueOf(twos*threes);
    }

    @Override
    protected String doProblem2(String input) {
        List<String> ids = Arrays.asList(input.split("\n"));
        for(int i = 0; i < ids.size(); i++) {
            for(int j = i+1; j < ids.size(); j++) {
                String id1 = ids.get(i), id2 = ids.get(j);
                for(int k = 0; k < id1.length(); k++) {
                    String id1c = id1.substring(0,k) + id1.substring(k+1);
                    String id2c = id2.substring(0,k) + id2.substring(k+1);
                    if(id1c.equals(id2c)) return id1c;
                }
            }
        }
        return null;
    }
}
