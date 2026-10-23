
import java.time.Clock;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BookingService {

    public static final Duration MAX_SLOT = Duration.ofHours(3);

    private final IRoomRepository rooms;
    private final IBookingRepository bookings;
    private final Clock clock;
    private final List<BookingRule> rules;

    public BookingService(
            IRoomRepository rooms,
            IBookingRepository bookings,
            Clock clock) {

        this.rooms = rooms;
        this.bookings = bookings;
        this.clock = clock;

        this.rules = new ArrayList<>();
        rules.add(new RoomMustExist(rooms));
        rules.add(new BookingMustStartInFuture(clock));
        rules.add(new BookingDurationMustNotExceedThreeHours());
        rules.add(new RoomHasNoOverlappingBooking(bookings));
        rules.add(new UserHasNoOverlappingBooking(bookings));
        rules.add(new UserHasAtMostThreeActiveBookings(bookings, clock));
    }

    public Booking book(String userId, String roomId, TimeSlot slot) {
        for (BookingRule rule : rules) {
            rule.check(userId, roomId, slot);
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
