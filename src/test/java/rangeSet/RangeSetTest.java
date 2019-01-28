package rangeSet;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RangeSetTest {

    @Test
    void providedTest() {
        RangeSet set = new RangeSet();

        set.add(2);
        set.addRange(10, 20);
        set.addRange(30, 40);
        set.remove(35);

        assertTrue(set.contains(10));
        assertTrue(set.contains(15));
        assertTrue(set.contains(19));
        assertTrue(set.contains(36));
        assertTrue(set.contains(2));

        assertFalse(set.contains(3));
        assertFalse(set.contains(35));
    }

    @Test
    void providedCollectionSet() {
        RangeSet setCollectionTest = new RangeSet();
        List list = Arrays.asList(1, 2, 3, 4, 5);

        setCollectionTest.addRange(1, 5);
        for (Integer value : setCollectionTest) {
            assertTrue(list.contains(value));
        }
    }
}