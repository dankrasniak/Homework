package range;

import javafx.util.Pair;

import java.util.*;

public class Range implements Iterable {

    private Pair<Integer, Integer> pair;

    public Range(Integer start, Integer end) {
        if (start.equals(end))
            throw new IllegalArgumentException();
        pair = new Pair<>(Integer.min(start, end), Integer.max(start, end));
    }

    private Integer getStart() {
        return pair.getKey();
    }

    private Integer getEnd() {
        return pair.getValue();
    }

    public int getNumberOfDigits() {
        return getEnd() - getStart();
    }

    public static boolean areColliding(Range a, Range b) {

        if (a.getStart() < b.getStart())
            if (a.getEnd() >= b.getStart())
                return true;

        if (b.getStart() < a.getStart())
            if (b.getEnd() >= a.getStart())
                return true;

        return Objects.equals(a.getStart(), b.getStart());
    }

    public boolean contains(Integer digit) {
        return digit >= getStart() && digit < getEnd();
    }

    public boolean contains(Range rangeContained) {
        return rangeContained.getStart() >= getStart() && rangeContained.getEnd() <= getEnd();
    }

    public Optional<Range> remove(Integer digit) {

        if (Objects.equals(digit, getStart()))
            this.pair = new Pair<>(getStart() + 1, getEnd());
        else if (Objects.equals(digit, getEnd() - 1))
            this.pair = new Pair<>(getStart(), getEnd() - 1);
        else {
            int end = getEnd();
            this.pair = new Pair<>(getStart(), digit);
            return Optional.of(new Range(digit + 1, end));
        }

        return Optional.empty();
    }

    public void mergeWith(LinkedList<Range> ranges) {
        pair = new Pair<>(
                Integer.min(ranges.getFirst().getStart(), getStart()),
                Integer.max(ranges.getLast().getEnd(), getEnd())
            );
    }

    public boolean shouldBePlacedBefore(Range comparedRange) {
        return this.getStart() < comparedRange.getStart();
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {
            private Integer currentValue = getStart();

            @Override
            public boolean hasNext() {
                return contains(currentValue);
            }

            @Override
            public Integer next() {
                if (!contains(currentValue))
                    throw new NoSuchElementException();
                return currentValue++;
            }
        };
    }
}
