package net.muneyamaru.aoc2018;

import java.io.File;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day6 extends Day {

    public Day6(File input) {
        super(input);
    }

    @Override
    protected String doProblem1(String input) {
        Set<Coord> anchors = Arrays.stream(input.split("\n")).map(s -> new Coord(s)).collect(Collectors.toSet());
        Set<Coord> hull = jarvis(anchors);
        int minx = hull.stream().mapToInt(c -> c.x).min().getAsInt(),
            miny = hull.stream().mapToInt(c -> c.y).min().getAsInt(),
            maxx = hull.stream().mapToInt(c -> c.x).max().getAsInt(),
            maxy = hull.stream().mapToInt(c -> c.y).max().getAsInt();

        Set<Coord> grid = new HashSet<>();
        for(int i = minx; i <= maxx; i++) {
            for(int j = miny; j <= maxy; j++) {
                grid.add(new Coord(i,j));
            }
        }

        Map<Coord,Set<Coord>> closest = new HashMap<>();
        anchors.forEach(a -> closest.put(a,new HashSet<>()));
        grid.forEach(g -> {
            Coord closestAnchor = anchors.stream().min(Comparator.comparing(a -> Math.abs(a.x - g.x) + Math.abs(a.y - g.y))).get();
            closest.get(closestAnchor).add(g);
        });
        hull.forEach(h -> closest.remove(h));
        return String.valueOf(closest.values().stream().mapToInt(s -> s.size()).max().getAsInt());
    }

    @Override
    protected String doProblem2(String input) {
        Set<Coord> anchors = Arrays.stream(input.split("\n")).map(s -> new Coord(s)).collect(Collectors.toSet());
        Set<Coord> hull = jarvis(anchors);
        int minx = hull.stream().mapToInt(c -> c.x).min().getAsInt(),
                miny = hull.stream().mapToInt(c -> c.y).min().getAsInt(),
                maxx = hull.stream().mapToInt(c -> c.x).max().getAsInt(),
                maxy = hull.stream().mapToInt(c -> c.y).max().getAsInt();

        Set<Coord> grid = new HashSet<>();
        for(int i = minx; i <= maxx; i++) {
            for(int j = miny; j <= maxy; j++) {
                grid.add(new Coord(i,j));
            }
        }

        Map<Coord,Integer> sumDists = new HashMap<>();
        for(Coord g : grid) {
            int sum = anchors.stream().mapToInt(a -> Math.abs(a.x - g.x) + Math.abs(a.y - g.y)).sum();
            sumDists.put(g,sum);
        }

        return String.valueOf(sumDists.values().stream().filter(i -> i < 10000).count());
    }

    private static class Coord {
        public final int x,y;

        public Coord(String s) {
            Pattern coordRegex = Pattern.compile("(\\d+), (\\d+)");
            Matcher m = coordRegex.matcher(s);
            if(!m.find()) throw new IllegalArgumentException("String not coord string");
            this.x = Integer.parseInt(m.group(1));
            this.y = Integer.parseInt(m.group(2));
        }

        public Coord(int x, int y) { this.x = x; this.y = y; }

        @Override
        public boolean equals(Object o) {
            if(!o.getClass().equals(Coord.class)) return false;
            Coord c = (Coord)o;
            return this.x==c.x && this.y==c.y;
        }
    }

    private static int orientation(Coord p, Coord q, Coord r) {
        int o = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (o == 0) return 0;
        return (o > 0) ? 1 : 2;
    }

    private static Set<Coord> jarvis(Set<Coord> input) {
        List<Coord> notHull = new ArrayList<>(input);
        Set<Coord> hull = new HashSet<>();
        Coord leftmost = notHull.stream().min(Comparator.comparingInt(c1->c1.x)).get();

        Coord pivot = leftmost;
        Coord next;
        do {
            next = notHull.get(0);
            for(Coord n : notHull) {
                if(orientation(pivot,n,next) == 2) next = n;
            }
            pivot = next;
            hull.add(pivot);
        } while(!pivot.equals(leftmost));

        return new HashSet<>(hull);
    }
}
