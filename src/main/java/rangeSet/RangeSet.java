package rangeSet;

import range.Range;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class RangeSet implements java.util.Set<Integer> {

    //region Variables
    private int sizeCurrently;
    private boolean sizeChanged;
    private LinkedList<Range> ranges;
    //endregion

    // Initialize
    {
        sizeCurrently = 0;
        sizeChanged = true;
        ranges = new LinkedList<>();
    }

    public boolean addRange(Integer start, Integer end) {
        boolean setChanged = true;
        Range rangeToBeAdded = new Range(start, end);

        LinkedList<Range> toBeMergedList = takeOutCollidingRanges(rangeToBeAdded);

        if (!toBeMergedList.isEmpty()) {
            if (toBeMergedList.size() == 1 && toBeMergedList.getFirst().contains(rangeToBeAdded))
                setChanged = false;
            rangeToBeAdded.mergeWith(toBeMergedList);
        }
        place(rangeToBeAdded);

        return setChanged;
    }

    private LinkedList<Range> takeOutCollidingRanges(Range rangeToBeAdded) {
        LinkedList<Range> collidingRanges = new LinkedList<>();
        ListIterator<Range> iter = ranges.listIterator();
        boolean foundFirstCollision = false;

        while (iter.hasNext()) {
            Range current = iter.next();

            if (Range.areColliding(rangeToBeAdded, current)) {
                if (!foundFirstCollision) foundFirstCollision = true;
                collidingRanges.offer(current);
                iter.remove();

            } else if (foundFirstCollision)
                break;
        }

        return collidingRanges;
    }

    private void place(Range rangeToBeAdded) {
        ListIterator<Range> iter = ranges.listIterator();
        boolean wasAdded = false;

        while (iter.hasNext())
            if (rangeToBeAdded.shouldBePlacedBefore(iter.next())) {
                iter.add(rangeToBeAdded);
                wasAdded = true;
                break;
            }

        if (!wasAdded)
            ranges.offer(rangeToBeAdded);

        sizeChanged = true;
    }

    //region Set methods
    @Override
    public boolean contains(Object arg) {
        Integer value = (Integer)arg;

        for (Range range : ranges)
            if (range.contains(value))
                return true;

        return false;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private ListIterator<Range> iterator = ranges.listIterator();
            private Range currentRange;
            private Iterator<Integer> rangeIterator;

            @Override
            public boolean hasNext() {
                if (rangeIterator == null) {
                    if (currentRange == null) {
                        if (iterator.hasNext())
                            currentRange = iterator.next();
                        else
                            return false;
                    }
                    rangeIterator = currentRange.iterator();
                }
                return rangeIterator.hasNext() || iterator.hasNext();
            }

            @Override
            public Integer next() {
                if (!rangeIterator.hasNext()) {
                    currentRange = iterator.next();
                    rangeIterator = currentRange.iterator();
                }
                return rangeIterator.next();
            }
        };
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <Integer> Integer[] toArray(Integer[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(Integer integer) {
        return addRange(integer, integer + 1);
    }

    @Override
    public boolean remove(Object arg) {
        Integer value = (Integer) arg;
        ListIterator<Range> iter = ranges.listIterator();

        while (iter.hasNext()) {
            Range current = iter.next();

            if (current.contains(value)) {
                if (current.getNumberOfDigits() == 1) {
                    iter.remove();
                    sizeChanged = true;
                    return true;
                }
                current.remove(value).ifPresent(this::place);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> arg) {
        Collection<Integer> digits = (Collection<Integer>) arg;

        for (Range current : ranges)
            if (!digits.stream().allMatch(current::contains))
                return false;

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Integer> c) {
        return c.stream().anyMatch(this::add);
    }

    @Override
    public boolean retainAll(Collection<?> arg) {
        Collection<Integer> digits = (Collection<Integer>) arg;

        Collection intersectingValues = digits.stream().filter(this::contains).collect(Collectors.toList());
        clear();
        return this.addAll(intersectingValues);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return c.stream().anyMatch(this::remove);
    }

    @Override
    public int size() {
        return sizeChanged ? sizeCalculate() : sizeCurrently;
    }

    private int sizeCalculate() {
        return ranges.stream().mapToInt(Range::getNumberOfDigits).sum();
    }

    @Override
    public boolean isEmpty() {
        return ranges.isEmpty();
    }

    @Override
    public void clear() {
        ranges.clear();
        sizeChanged = false;
        sizeCurrently = 0;
    }
    //endregion
}
