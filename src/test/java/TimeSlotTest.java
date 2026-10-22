
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class TimeSlotTest {

    @Test
    void endBeforeStartIsRejected() {
        LocalDateTime ten = LocalDateTime.of(2027, 1, 11, 10, 0);
        LocalDateTime nine = LocalDateTime.of(2027, 1, 11, 9, 0);

        assertThrows(
            IllegalArgumentException.class,
            () -> new TimeSlot(ten, nine)
        );
    }

    @Test
    void equalStartAndEndIsRejected() {
        LocalDateTime ten = LocalDateTime.of(2027, 1, 11, 10, 0);

        assertThrows(
            IllegalArgumentException.class,
            () -> new TimeSlot(ten, ten)
        );
    }

    @Test
    void nullStartIsRejected() {
        LocalDateTime end = LocalDateTime.of(2027, 1, 11, 11, 0);

        assertThrows(
            IllegalArgumentException.class,
            () -> new TimeSlot(null, end)
        );
    }

    @Test
    void nullEndIsRejected() {
        LocalDateTime start = LocalDateTime.of(2027, 1, 11, 10, 0);

        assertThrows(
            IllegalArgumentException.class,
            () -> new TimeSlot(start, null)
        );
    }

    @Test
    void durationIsCalculatedInMinutes() {
        LocalDateTime start = LocalDateTime.of(2027, 1, 11, 10, 0);
        LocalDateTime end = LocalDateTime.of(2027, 1, 11, 11, 30);

        TimeSlot slot = new TimeSlot(start, end);

        assertEquals(Duration.ofMinutes(90), slot.duration());
    }

    private static TimeSlot slot(int startHour, int endHour) {
        return new TimeSlot(
            LocalDateTime.of(2027, 1, 11, startHour, 0),
            LocalDateTime.of(2027, 1, 11, endHour, 0)
        );
    }

    @ParameterizedTest(name = "{0}-{1} vs {2}-{3} overlaps: {4}")
    @CsvSource({
        "10, 11, 11, 12, false",
        "10, 12, 11, 13, true",
        "10, 11, 12, 13, false",
        "10, 12, 10, 11, true",
        "10, 12, 10, 12, true",
        "12, 13, 10, 12, false"
    })
    void overlapCases(
        int aStart,
        int aEnd,
        int bStart,
        int bEnd,
        boolean expected
    ) {
        assertEquals(
            expected,
            slot(aStart, aEnd).overlaps(slot(bStart, bEnd))
        );

        assertEquals(
            expected,
            slot(bStart, bEnd).overlaps(slot(aStart, aEnd))
        );
    }
}
