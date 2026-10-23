
public class RoomHasNoOverlappingBooking implements BookingRule {

    private final IBookingRepository bookings;

    public RoomHasNoOverlappingBooking(IBookingRepository bookings) {
        this.bookings = bookings;
    }

    @Override
    public void check(String userId, String roomId, TimeSlot slot) {
        for (Booking existing : bookings.findByRoom(roomId)) {
            if (existing.getSlot().overlaps(slot)) {
                throw new IllegalStateException(
                        "Room is already booked for this time"
                );
            }
        }
    }
}
