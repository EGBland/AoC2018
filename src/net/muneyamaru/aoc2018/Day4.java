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

    private Map<Integer,List<SleepRecord>> getGuardRecords(String input) {
        Pattern infoRegex = Pattern.compile("Guard #(\\d+) begins shift$");
        List<GuardRecord> guardRecords = Arrays.stream(input.split("\n")).map(s -> new GuardRecord(s)).collect(Collectors.toList());
        guardRecords.sort(Comparator.naturalOrder());
        Map<Integer, List<SleepRecord>> records = new TreeMap<>();
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

        return records;
    }

    public Map<Integer,Integer> getMinuteFreqsForGuard(List<SleepRecord> records) {
        Map<Integer,Integer> minuteSleepFreq = new TreeMap<>();

        for(int i = 0; i < 60; i++) {
            minuteSleepFreq.put(i,0);
        }

        for(SleepRecord record : records) {
            int s = record.start.getMinutes(), e = record.stop.getMinutes();
            for(int i = s; i < e; i++) minuteSleepFreq.put(i,minuteSleepFreq.get(i)+1);
        }

        return minuteSleepFreq;
    }

    @Override
    protected String doProblem1(String input) {
        Map<Integer,List<SleepRecord>> records = getGuardRecords(input);
        Map.Entry<Integer,List<SleepRecord>> mostSleep =
                records.entrySet().stream().max((r1,r2) -> r1.getValue().stream().mapToInt(s -> s.getDuration()).sum() - r2.getValue().stream().mapToInt(s -> s.getDuration()).sum()).get(); // "nice" lambda expression
        Map<Integer,Integer> minuteSleepFreq = getMinuteFreqsForGuard(mostSleep.getValue());

        return String.valueOf(mostSleep.getKey() * minuteSleepFreq.entrySet().stream().max(Comparator.comparing(Map.Entry<Integer,Integer>::getValue)).get().getKey());
        //return out;
    }

    @Override
    protected String doProblem2(String input) {
        Map<Integer,List<SleepRecord>> records = getGuardRecords(input);
        Map<Integer,Map<Integer,Integer>> minuteFreqs =
            records
                .entrySet()
                .stream()
                .collect(Collectors.toMap(r -> r.getKey(), r -> getMinuteFreqsForGuard(r.getValue())));

        Map<Integer,Map.Entry<Integer,Integer>> guardMaxMinute =
            minuteFreqs
                .entrySet()
                .stream()
                .collect(Collectors.toMap(r -> r.getKey(), r -> r.getValue().entrySet().stream().max(Comparator.comparing(Map.Entry<Integer,Integer>::getValue)).get()));

        Map.Entry<Integer,Map.Entry<Integer,Integer>> mostMaximal =
            guardMaxMinute.entrySet().stream().max((r1,r2) -> r1.getValue().getValue().compareTo(r2.getValue().getValue())).get();

        return String.valueOf(mostMaximal.getKey()*mostMaximal.getValue().getKey());
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
