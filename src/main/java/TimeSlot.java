
import java.time.LocalDateTime;
import java.time.Duration;

public final class TimeSlot {

    public TimeSlot(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("start and end must not be null");
        }

        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("end must be after start");
        }
    }

    public Duration duration() {
        return null;
    }
}
