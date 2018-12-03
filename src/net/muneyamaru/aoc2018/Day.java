package net.muneyamaru.aoc2018;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public abstract class Day {
    protected final File input;

    public Day(File input) {
        this.input = input;
    }

    protected abstract String doProblem1(String input);
    protected abstract String doProblem2(String input);

    private String getData(File input) {
        try(InputStream fis = new FileInputStream(input)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n;
            while((n = fis.read(buf)) != -1)
                baos.write(buf,0,n);

            return new String(baos.toByteArray());
        } catch(Exception ex) {
            // TODO handle
            ex.printStackTrace();
            return null;
        }
    }

    public String solveProblem1() {
        String data = getData(input);
        long start = System.currentTimeMillis();
        String soln = doProblem1(data);
        long end = System.currentTimeMillis();
        return soln + "\t(" + (end-start) + "ms)";
    }

    public String solveProblem2() {
        String data = getData(input);
        long start = System.currentTimeMillis();
        String soln = doProblem2(data);
        long end = System.currentTimeMillis();
        return soln + "\t(" + (end-start) + "ms)";
    }
}
