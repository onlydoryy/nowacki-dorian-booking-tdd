
import java.time.Clock;
import java.time.LocalDateTime;

public class BookingMustStartInFuture implements BookingRule {

    private final Clock clock;

    public BookingMustStartInFuture(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void check(String userId, String roomId, TimeSlot slot) {
        if (!slot.getStart().isAfter(LocalDateTime.now(clock))) {
            throw new IllegalArgumentException(
                    "Booking must start in the future"
            );
        }
    }
}
