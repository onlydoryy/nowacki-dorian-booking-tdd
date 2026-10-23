
public class UserHasNoOverlappingBooking implements BookingRule {

    private final IBookingRepository bookings;

    public UserHasNoOverlappingBooking(IBookingRepository bookings) {
        this.bookings = bookings;
    }

    @Override
    public void check(String userId, String roomId, TimeSlot slot) {
        for (Booking existing : bookings.findByUser(userId)) {
            if (existing.getSlot().overlaps(slot)) {
                throw new IllegalStateException(
                        "User already has an overlapping booking"
                );
            }
        }
    }
}
