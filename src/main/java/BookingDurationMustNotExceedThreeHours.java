
import java.time.Duration;

public class BookingDurationMustNotExceedThreeHours implements BookingRule {

    private static final Duration MAX_SLOT = Duration.ofHours(3);

    @Override
    public void check(String userId, String roomId, TimeSlot slot) {
        if (slot.duration().compareTo(MAX_SLOT) > 0) {
            throw new IllegalArgumentException(
                    "Booking cannot exceed three hours"
            );
        }
    }
}
