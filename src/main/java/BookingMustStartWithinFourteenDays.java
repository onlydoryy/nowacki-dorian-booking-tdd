
import java.time.Clock;
import java.time.LocalDate;

public class BookingMustStartWithinFourteenDays implements BookingRule {

    private final Clock clock;

    public BookingMustStartWithinFourteenDays(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void check(String userId, String roomId, TimeSlot slot) {
        LocalDate today = LocalDate.now(clock);
        LocalDate latestAllowedDate = today.plusDays(14);
        LocalDate bookingDate = slot.getStart().toLocalDate();

        if (bookingDate.isAfter(latestAllowedDate)) {
            throw new IllegalArgumentException(
                    "Booking cannot start more than fourteen days ahead"
            );
        }
    }
}
