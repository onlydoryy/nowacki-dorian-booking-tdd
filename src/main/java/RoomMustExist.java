
public class RoomMustExist implements BookingRule {

    private final IRoomRepository rooms;

    public RoomMustExist(IRoomRepository rooms) {
        this.rooms = rooms;
    }

    @Override
    public void check(String userId, String roomId, TimeSlot slot) {
        if (rooms.findById(roomId) == null) {
            throw new IllegalArgumentException("Room does not exist");
        }
    }
}
