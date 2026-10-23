
import java.util.List;

public interface IRoomRepository {
    Room findById(String id);
    void save(Room room);
    List<Room> findAll();
}
