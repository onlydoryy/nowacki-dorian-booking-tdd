
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TimeSlotTest {

    @Test
    void rejectsNullStart() {
        LocalDateTime end = LocalDateTime.of(2026, 10, 22, 11, 0);

        assertThrows(
            IllegalArgumentException.class,
            () -> new TimeSlot(null, end)
        );
    }

    @Test
    void rejectsNullEnd() {
        LocalDateTime start = LocalDateTime.of(2026, 10, 22, 10, 0);

        assertThrows(
            IllegalArgumentException.class,
            () -> new TimeSlot(start, null)
        );
    }

    @Test
    void rejectsEndBeforeStart() {
        LocalDateTime start = LocalDateTime.of(2026, 10, 22, 11, 0);
        LocalDateTime end = LocalDateTime.of(2026, 10, 22, 10, 0);

        assertThrows(
            IllegalArgumentException.class,
            () -> new TimeSlot(start, end)
        );
    }

    @Test
    void rejectsEqualStartAndEnd() {
        LocalDateTime time = LocalDateTime.of(2026, 10, 22, 10, 0);

        assertThrows(
            IllegalArgumentException.class,
            () -> new TimeSlot(time, time)
        );
    }

    @Test
    void calculatesDurationInMinutes() {
        LocalDateTime start = LocalDateTime.of(2026, 10, 22, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 10, 22, 11, 30);

        TimeSlot slot = new TimeSlot(start, end);

        assertEquals(Duration.ofMinutes(90), slot.duration());
    }

    @ParameterizedTest
    @CsvSource({
        "10, 11, 10, 11, true",
        "10, 11, 9, 10, false",
        "10, 11, 11, 12, false",
        "10, 11, 9, 11, true",
        "10, 11, 10, 12, true",
        "10, 12, 11, 13, true",
        "10, 12, 9, 13, true"
    })
    void checksWhetherSlotsOverlap(
        int startHourA,
        int endHourA,
        int startHourB,
        int endHourB,
        boolean expected
    ) {
        LocalDateTime startA =
            LocalDateTime.of(2026, 10, 22, startHourA, 0);
        LocalDateTime endA =
            LocalDateTime.of(2026, 10, 22, endHourA, 0);

        LocalDateTime startB =
            LocalDateTime.of(2026, 10, 22, startHourB, 0);
        LocalDateTime endB =
            LocalDateTime.of(2026, 10, 22, endHourB, 0);

        TimeSlot a = new TimeSlot(startA, endA);
        TimeSlot b = new TimeSlot(startB, endB);

        assertEquals(expected, a.overlaps(b));
        assertEquals(a.overlaps(b), b.overlaps(a));
    }

    @Test
    void equalSlotsAreEqualAndHaveSameHashCode() {
        LocalDateTime start = LocalDateTime.of(2026, 10, 22, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 10, 22, 11, 0);

        TimeSlot first = new TimeSlot(start, end);
        TimeSlot second = new TimeSlot(start, end);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void differentSlotsAreNotEqual() {
        LocalDateTime start = LocalDateTime.of(2026, 10, 22, 10, 0);
        LocalDateTime endA = LocalDateTime.of(2026, 10, 22, 11, 0);
        LocalDateTime endB = LocalDateTime.of(2026, 10, 22, 12, 0);

        TimeSlot first = new TimeSlot(start, endA);
        TimeSlot second = new TimeSlot(start, endB);

        assertNotEquals(first, second);
    }

    @Test
    void getStartReturnsTheStartTime() {
        LocalDateTime start = LocalDateTime.of(2026, 10, 22, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 10, 22, 11, 30);

        TimeSlot slot = new TimeSlot(start, end);

        assertEquals(start, slot.getStart());
    }

    @Test
    void getEndReturnsTheEndTime() {
        LocalDateTime start = LocalDateTime.of(2026, 10, 22, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 10, 22, 11, 30);

        TimeSlot slot = new TimeSlot(start, end);

        assertEquals(end, slot.getEnd());
    }

    @Test
    void toStringIncludesStartAndEnd() {
        LocalDateTime start =
            LocalDateTime.of(2026, 10, 22, 10, 0);
        LocalDateTime end =
            LocalDateTime.of(2026, 10, 22, 11, 30);

        TimeSlot slot = new TimeSlot(start, end);

        assertEquals(
            "TimeSlot{start=2026-10-22T10:00, end=2026-10-22T11:30}",
            slot.toString()
        );
    }
}
