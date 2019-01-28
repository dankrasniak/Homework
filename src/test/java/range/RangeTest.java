package range;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class RangeTest {

    @Test
    void createRanges() {
        new Range(1, 2);
        new Range(3, 2);
        new Range(-1, 2);
        new Range(1, -2);
        new Range(1, 2_000_00);
    }

    @Test
    void createRange_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Range(1, 1));
    }

    @Test
    void getNumberOfDigits() {
        Range testRange = new Range(1, 2);
        assertEquals(testRange.getNumberOfDigits(), 1);
    }

    @ParameterizedTest(name = "|{1} - {0}| = {2}")
    @CsvSource({ "1, 2, 1", "2, 1, 1", "1, 51, 50" })
    void getNumberOfDigits(int value1, int value2, int diff) {
        Range testRange = new Range(value1, value2);
        assertEquals(testRange.getNumberOfDigits(), diff);
    }

    @ParameterizedTest
    @CsvSource({ "1, 5, 3, 6", "1, 2, 2, 3", "2, 3, 1, 2", "1, 2, 1, 2" })
    void areColliding(int startA, int endA, int startB, int endB) {
        Range rangeA = new Range(startA, endA), rangeB = new Range(startB, endB);
        assertTrue(Range.areColliding(rangeA, rangeB));
    }

    @ParameterizedTest
    @CsvSource({ "1, 2, 3, 4", "1, 2, 6, 3", "2, 3, 41, 22"})
    void areColliding_not(int startA, int endA, int startB, int endB) {
        Range rangeA = new Range(startA, endA), rangeB = new Range(startB, endB);
        assertFalse(Range.areColliding(rangeA, rangeB));
    }

    @ParameterizedTest
    @CsvSource({ "1, 3, 2", "12, 23, 16", "2, 3, 2"})
    void containsDigit(int start, int end, int digit) {
        Range rangeTest = new Range(start, end);
        assertTrue(rangeTest.contains(digit));
    }

    @ParameterizedTest
    @CsvSource({ "1, 3, 3", "12, 23, 1", "2, 3, 2000"})
    void containsDigit_not(int start, int end, int digit) {
        Range rangeTest = new Range(start, end);
        assertFalse(rangeTest.contains(digit));
    }

    @ParameterizedTest
    @CsvSource({ "1, 3, 2, 3", "12, 23, 13, 15", "2, 3, 2, 3"})
    void containsRange(int startA, int endA, int startB, int endB) {
        Range rangeA = new Range(startA, endA), rangeB = new Range(startB, endB);
        assertTrue(rangeA.contains(rangeB));
    }

    @ParameterizedTest
    @CsvSource({ "1, 3, 3, 4", "12, 23, 1, 2", "2, 3, 1, 3"})
    void containsRange_not(int startA, int endA, int startB, int endB) {
        Range rangeA = new Range(startA, endA), rangeB = new Range(startB, endB);
        assertFalse(rangeA.contains(rangeB));
    }
}
