package net.muneyamaru.aoc2018;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day3 extends Day {
    public Day3(File input) { super(input); }

    @Override
    protected String doProblem1(String input) {
        FabricClaim[] claims = Arrays.stream(input.split("\n")).map(s -> new FabricClaim(s)).toArray(FabricClaim[]::new);
        Set<Coordinate> overlap = new HashSet<>();
        for(int i = 0; i < claims.length; i++) {
            for(int j = i+1; j < claims.length; j++) {
                overlap.addAll(claims[i].getOverlap(claims[j]));
            }
        }

        return String.valueOf(overlap.size());
    }

    @Override
    protected String doProblem2(String input) {
        FabricClaim[] claims = Arrays.stream(input.split("\n")).map(s -> new FabricClaim(s)).toArray(FabricClaim[]::new);

        outer:
        for(int i = 0; i < claims.length; i++) {
            for(int j = 0; j < claims.length; j++) {
                if(i == j) continue;
                if(claims[i].overlap(claims[j]) != 0) continue outer;
            }
            return String.valueOf(claims[i].id);
        }
        return null;
    }

    private static class FabricClaim {
        private static final Pattern claimRegex = Pattern.compile("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)");
        public int id;
        private final Set<Coordinate> region = new HashSet<>();

        public FabricClaim(String claimStr) {
            Matcher m = claimRegex.matcher(claimStr);
            if(!m.find()) throw new IllegalArgumentException("String not a claim string");
            this.id = Integer.parseInt(m.group(1));
            int lx  = Integer.parseInt(m.group(2));
            int ly  = Integer.parseInt(m.group(3));
            int w   = Integer.parseInt(m.group(4));
            int h   = Integer.parseInt(m.group(5));

            for(int i = 0; i < w; i++) {
                for(int j = 0; j < h; j++) {
                    region.add(new Coordinate(i+lx,j+ly));
                }
            }
        }

        public int overlap(FabricClaim fc) {
            return (int)region.stream().filter(c -> fc.region.contains(c)).count();
        }

        public Set<Coordinate> getOverlap(FabricClaim fc) {
            return region.stream().filter(c -> fc.region.contains(c)).collect(Collectors.toSet());
        }

        public int area() {
            return region.size();
        }
    }

    private static class Coordinate {
        public final int x, y;
        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x,y);
        }

        @Override
        public boolean equals(Object o) {
            if(o.getClass() != Coordinate.class) return false;
            Coordinate cast = (Coordinate) o;
            return this.x == cast.x && this.y == cast.y;
        }
    }
}
