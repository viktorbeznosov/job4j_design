package ru.job4j.ood.lsp.store.foodmap;

import java.util.Objects;

public class PercentInterval {

    private int start;

    private int end;

    public PercentInterval() {
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public PercentInterval(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PercentInterval interval = (PercentInterval) o;
        return start == interval.start && end == interval.end;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
