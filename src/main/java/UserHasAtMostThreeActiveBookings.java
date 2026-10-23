
import java.time.Clock;
import java.time.LocalDateTime;

public class UserHasAtMostThreeActiveBookings implements BookingRule {

    private final IBookingRepository bookings;
    private final Clock clock;

    public UserHasAtMostThreeActiveBookings(
            IBookingRepository bookings,
            Clock clock) {
        this.bookings = bookings;
        this.clock = clock;
    }

    @Override
    public void check(String userId, String roomId, TimeSlot slot) {
        LocalDateTime now = LocalDateTime.now(clock);

        long activeBookings = bookings.findByUser(userId)
                .stream()
                .filter(booking ->
                        booking.getSlot().getEnd().isAfter(now))
                .count();

        if (activeBookings >= 3) {
            throw new IllegalStateException(
                    "User cannot have more than three active bookings"
            );
        }
    }
}
