
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    @Test
    void getIdReturnsRoomId() {
        Room room = new Room("ROOM-101", "Study Room 101");

        assertEquals("ROOM-101", room.getId());
    }
}
