
import java.util.Objects;

public class Booking {

    private final String userId;
    private final String roomId;
    private final TimeSlot slot;

    public Booking(String userId, String roomId, TimeSlot slot) {
        this.userId = userId;
        this.roomId = roomId;
        this.slot = slot;
    }

    public String getUserId() {
        return userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public TimeSlot getSlot() {
        return slot;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Booking)) {
            return false;
        }

        Booking other = (Booking) obj;

        return Objects.equals(userId, other.userId)
                && Objects.equals(roomId, other.roomId)
                && Objects.equals(slot, other.slot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roomId, slot);
    }
}
