
import org.junit.jupiter.api.Test;
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
}
