package net.muneyamaru.aoc2018;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day4 extends Day {

    public Day4(File input) {
        super(input);
    }

    @Override
    protected String doProblem1(String input) {
        Map<Integer, List<SleepRecord>> records = new TreeMap<>();
        List<GuardRecord> guardRecords = Arrays.stream(input.split("\n")).map(s -> new GuardRecord(s)).collect(Collectors.toList());

        Pattern infoRegex = Pattern.compile("Guard #(\\d+) begins shift$");

        guardRecords.sort(Comparator.naturalOrder());

        int currentGuard = -1;
        Date start = null;
        for(GuardRecord record : guardRecords) {
            Matcher m = infoRegex.matcher(record.information);
            if(m.find()) {
                currentGuard = Integer.parseInt(m.group(1));
                if(!records.containsKey(currentGuard)) records.put(currentGuard, new ArrayList<>());
            } else if(record.information.equals("falls asleep")) {
                start = record.time;
            } else {
                records.get(currentGuard).add(new SleepRecord(start,record.time));
            }
        }
        Map.Entry<Integer,List<SleepRecord>> mostSleep = records.entrySet().stream().max((r1,r2) -> r1.getValue().stream().mapToInt(s -> s.getDuration()).sum() - r2.getValue().stream().mapToInt(s -> s.getDuration()).sum()).get(); // "nice" lambda expression
        Map<Integer,Integer> minuteSleepFreq = new TreeMap<>();

        for(int i = 0; i < 60; i++) {
            minuteSleepFreq.put(i,0);
        }

        for(SleepRecord record : mostSleep.getValue()) {
            int s = record.start.getMinutes(), e = record.stop.getMinutes();
            for(int i = s; i < e; i++) minuteSleepFreq.put(i,minuteSleepFreq.get(i)+1);
        }
        return String.valueOf(mostSleep.getKey() * minuteSleepFreq.entrySet().stream().max(Comparator.comparing(Map.Entry<Integer,Integer>::getValue)).get().getKey());
        //return out;
    }

    @Override
    protected String doProblem2(String input) {
        return null;
    }

    private static class GuardRecord implements Comparable<GuardRecord> {
        private static final Pattern recordRegex = Pattern.compile("\\[(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2})\\] (.*)$");

        public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        public final Date time;
        public final String information;

        public GuardRecord(String recordStr) {
            Date time = null;
            String information = "";

            Matcher m = recordRegex.matcher(recordStr);
            if(!m.find()) throw new IllegalArgumentException("String not a record string");
            try {
                time = TIME_FORMAT.parse(m.group(1));
            } catch(ParseException pe) {
                throw new IllegalArgumentException("Invalid time (" + pe.getLocalizedMessage() + ")");
            }
            information = m.group(2);

            this.time = time;
            this.information = information;
        }

        @Override
        public int compareTo(GuardRecord o) {
            return time.compareTo(o.time);
        }
    }

    private static class SleepRecord {
        public final Date start, stop;

        public SleepRecord(Date start, Date stop) {
            this.start = start; this.stop = stop;
        }

        public int getDuration() {
            return (int)(TimeUnit.MINUTES.convert(stop.getTime() - start.getTime(),TimeUnit.MILLISECONDS));
        }
    }
}
