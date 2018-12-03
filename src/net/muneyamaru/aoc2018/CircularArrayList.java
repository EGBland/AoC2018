package net.muneyamaru.aoc2018;

import java.util.ArrayList;

public class CircularArrayList<T> extends ArrayList<T> {
    @Override
    public T get(int index) {
        while(index < 0) index += size();
        return super.get(index%size());
    }
}
