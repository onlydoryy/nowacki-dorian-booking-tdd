
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
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

    public Booking book(String userId, String roomId, TimeSlot slot) {
        if (rooms.findById(roomId) == null) {
            throw new IllegalArgumentException("Room does not exist");
        }

        if (!slot.getStart().isAfter(LocalDateTime.now(clock))) {
            throw new IllegalArgumentException(
                    "Booking must start in the future"
            );
        }

        if (slot.duration().compareTo(MAX_SLOT) > 0) {
            throw new IllegalArgumentException(
                    "Booking cannot exceed three hours"
            );
        }

        for (Booking existing : bookings.findByRoom(roomId)) {
            if (existing.getSlot().overlaps(slot)) {
                throw new IllegalStateException(
                        "Room is already booked for this time"
                );
            }
        }

        for (Booking existing : bookings.findByUser(userId)) {
            if (existing.getSlot().overlaps(slot)) {
                throw new IllegalStateException(
                        "User already has an overlapping booking"
                );
            }
        }

        Booking booking = new Booking(userId, roomId, slot);
        bookings.save(booking);

        return booking;
    }

    public void cancel(String userId, String roomId, TimeSlot slot) {
        for (Booking existing : bookings.findByUser(userId)) {
            if (existing.getRoomId().equals(roomId)
                    && existing.getSlot().equals(slot)) {
                bookings.delete(existing);
                return;
            }
        }

        throw new IllegalArgumentException(
                "Booking not found for this user"
        );
    }

    public List<Booking> bookingsFor(String userId) {
        return bookings.findByUser(userId)
                .stream()
                .sorted(Comparator.comparing(
                        booking -> booking.getSlot().getStart()
                ))
                .toList();
    }
}
