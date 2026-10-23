
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookingServiceTest {

    private static final Clock CLOCK =
            Clock.fixed(
                    Instant.parse("2027-01-11T09:00:00Z"),
                    ZoneOffset.UTC
            );

    private BookingService service;

    @BeforeEach
    void setUp() {
        MemoryRoomRepository rooms = new MemoryRoomRepository();
        rooms.save(new Room("R1", "Room 1"));
        rooms.save(new Room("R2", "Room 2"));

        service = new BookingService(
                rooms,
                new MemoryBookingRepository(),
                CLOCK
        );
    }

    private static TimeSlot slot(int startHour, int endHour) {
        return new TimeSlot(
                LocalDateTime.of(2027, 1, 11, startHour, 0),
                LocalDateTime.of(2027, 1, 11, endHour, 0)
        );
    }

    @Test
    void bookingAFreeRoomReturnsTheBooking() {
        Booking booking = service.book("alice", "R1", slot(10, 11));

        assertEquals(
                new Booking("alice", "R1", slot(10, 11)),
                booking
        );
    }

    @Test
    void bookingANonexistentRoomThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.book("alice", "R99", slot(10, 11))
        );
    }

    @Test
    void bookingInThePastThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.book("alice", "R1", slot(8, 9))
        );
    }

    @Test
    void bookingExactlyThreeHoursIsAllowed() {
        Booking booking = service.book("alice", "R1", slot(10, 13));

        assertEquals(
                new Booking("alice", "R1", slot(10, 13)),
                booking
        );
    }

    @Test
    void bookingLongerThanThreeHoursThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.book("alice", "R1", slot(10, 14))
        );
    }

    @Test
    void bookingAnAlreadyBookedRoomAtAnOverlappingTimeThrowsException() {
        service.book("alice", "R1", slot(10, 11));

        assertThrows(
                IllegalStateException.class,
                () -> service.book("bob", "R1", slot(10, 11))
        );
    }

    @Test
    void userCannotMakeOverlappingBookingsInDifferentRooms() {
        service.book("alice", "R1", slot(10, 11));

        assertThrows(
                IllegalStateException.class,
                () -> service.book("alice", "R2", slot(10, 11))
        );
    }

    @Test
    void userCanCancelTheirOwnBooking() {
        service.book("alice", "R1", slot(10, 11));

        service.cancel("alice", "R1", slot(10, 11));

        assertEquals(List.of(), service.bookingsFor("alice"));
    }

    @Test
    void userCannotCancelAnotherUsersBooking() {
        service.book("alice", "R1", slot(10, 11));

        assertThrows(
                IllegalArgumentException.class,
                () -> service.cancel("bob", "R1", slot(10, 11))
        );

        assertEquals(
                List.of(new Booking("alice", "R1", slot(10, 11))),
                service.bookingsFor("alice")
        );
    }

    @Test
    void bookingsForReturnsBookingsInChronologicalOrder() {
        service.book("alice", "R1", slot(12, 13));
        service.book("alice", "R2", slot(10, 11));
        service.book("alice", "R1", slot(14, 15));

        assertEquals(
                List.of(
                        new Booking("alice", "R2", slot(10, 11)),
                        new Booking("alice", "R1", slot(12, 13)),
                        new Booking("alice", "R1", slot(14, 15))
                ),
                service.bookingsFor("alice")
        );
    }

    @Test
    void bookingsForReturnsOnlyTheSpecifiedUsersBookings() {
        service.book("alice", "R1", slot(10, 11));
        service.book("bob", "R2", slot(12, 13));

        assertEquals(
                List.of(new Booking("alice", "R1", slot(10, 11))),
                service.bookingsFor("alice")
        );
    }

    @Test
    void userCannotHaveMoreThanThreeActiveBookings() {
        service.book("alice", "R1", slot(10, 11));
        service.book("alice", "R2", slot(11, 12));
        service.book("alice", "R1", slot(12, 13));

        assertThrows(
                IllegalStateException.class,
                () -> service.book("alice", "R2", slot(13, 14))
        );
    }
}
