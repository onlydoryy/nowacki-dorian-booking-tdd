
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingTest {

    @Test
    void getUserIdReturnsUserId() {
        TimeSlot slot = new TimeSlot(
                LocalDateTime.of(2026, 11, 1, 10, 0),
                LocalDateTime.of(2026, 11, 1, 11, 0)
        );

        Booking booking = new Booking("user-1", "ROOM-101", slot);

        assertEquals("user-1", booking.getUserId());
    }

    @Test
    void getRoomIdReturnsRoomId() {
        TimeSlot slot = new TimeSlot(
                LocalDateTime.of(2026, 11, 1, 10, 0),
                LocalDateTime.of(2026, 11, 1, 11, 0)
        );

        Booking booking = new Booking("user-1", "ROOM-101", slot);

        assertEquals("ROOM-101", booking.getRoomId());
    }

    @Test
    void getSlotReturnsTimeSlot() {
        TimeSlot slot = new TimeSlot(
                LocalDateTime.of(2026, 11, 1, 10, 0),
                LocalDateTime.of(2026, 11, 1, 11, 0)
        );

        Booking booking = new Booking("user-1", "ROOM-101", slot);

        assertEquals(slot, booking.getSlot());
    }

    @Test
    void bookingsWithSameDetailsAreEqual() {
        TimeSlot firstSlot = new TimeSlot(
                LocalDateTime.of(2026, 11, 1, 10, 0),
                LocalDateTime.of(2026, 11, 1, 11, 0)
        );

        TimeSlot secondSlot = new TimeSlot(
                LocalDateTime.of(2026, 11, 1, 10, 0),
                LocalDateTime.of(2026, 11, 1, 11, 0)
        );

        Booking first = new Booking("user-1", "ROOM-101", firstSlot);
        Booking second = new Booking("user-1", "ROOM-101", secondSlot);

        assertEquals(first, second);
    }
}
