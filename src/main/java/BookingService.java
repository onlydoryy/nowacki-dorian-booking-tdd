
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class BookingService {

    public static final Duration MAX_SLOT = Duration.ofHours(3);

    private final IRoomRepository rooms;
    private final IBookingRepository bookings;
    private final Clock clock;

    public BookingService(
            IRoomRepository rooms,
            IBookingRepository bookings,
            Clock clock) {

        this.rooms = rooms;
        this.bookings = bookings;
        this.clock = clock;
    }

    /**
     * Books a room for a user.
     * Returns the newly created booking.
     */
    public Booking book(String userId, String roomId, TimeSlot slot) {

        // R1: The room must exist.
        if (rooms.findById(roomId) == null) {
            throw new IllegalArgumentException(
                    "Room does not exist"
            );
        }

        // R2: The booking must start in the future.
        if (!slot.getStart().isAfter(LocalDateTime.now(clock))) {
            throw new IllegalArgumentException(
                    "Booking must start in the future"
            );
        }

        // R3: A booking cannot exceed three hours.
        if (slot.duration().compareTo(MAX_SLOT) > 0) {
            throw new IllegalArgumentException(
                    "Booking cannot exceed three hours"
            );
        }

        // R4: The room cannot have overlapping bookings.
        for (Booking existing : bookings.findByRoom(roomId)) {
            if (existing.getSlot().overlaps(slot)) {
                throw new IllegalStateException(
                        "Room is already booked for this time"
                );
            }
        }

        // R5: The user cannot have overlapping bookings
        // in different rooms either.
        for (Booking existing : bookings.findByUser(userId)) {
            if (existing.getSlot().overlaps(slot)) {
                throw new IllegalStateException(
                        "User already has an overlapping booking"
                );
            }
        }

        // Create and save the booking.
        Booking booking = new Booking(userId, roomId, slot);
        bookings.save(booking);

        return booking;
    }

    /**
     * Cancels an existing booking.
     */
    public void cancel(String userId, String roomId, TimeSlot slot) {
        throw new UnsupportedOperationException(
                "TODO: implement using TDD"
        );
    }

    /**
     * Returns a user's bookings, earliest first.
     */
    public List<Booking> bookingsFor(String userId) {
        throw new UnsupportedOperationException(
                "TODO: implement using TDD"
        );
    }
}
