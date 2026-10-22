
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookingTest {

    @Test
    void getUserIdReturnsUserId() {
        TimeSlot slot = new TimeSlot(
                java.time.LocalDateTime.of(2026, 11, 1, 10, 0),
                java.time.LocalDateTime.of(2026, 11, 1, 11, 0)
        );

        Booking booking = new Booking("user-1", "ROOM-101", slot);

        assertEquals("user-1", booking.getUserId());
    }

    @Test
    void getRoomIdReturnsRoomId() {
        TimeSlot slot = new TimeSlot(
                java.time.LocalDateTime.of(2026, 11, 1, 10, 0),
                java.time.LocalDateTime.of(2026, 11, 1, 11, 0)
        );

        Booking booking = new Booking("user-1", "ROOM-101", slot);

        assertEquals("ROOM-101", booking.getRoomId());
    }
}
