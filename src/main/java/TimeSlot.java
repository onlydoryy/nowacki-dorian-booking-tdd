
import java.time.LocalDateTime;
import java.time.Duration;

public final class TimeSlot {

    private final LocalDateTime start;
    private final LocalDateTime end;

    public TimeSlot(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("start and end must not be null");
        }

        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("end must be after start");
        }

        this.start = start;
        this.end = end;
    }

    public Duration duration() {
        return Duration.between(start, end);
    }

    public boolean overlaps(TimeSlot other) {
        return false;
    }
}
