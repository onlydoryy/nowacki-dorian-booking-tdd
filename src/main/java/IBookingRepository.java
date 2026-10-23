
import java.util.List;

public interface IBookingRepository {
    void save(Booking booking);
    void delete(Booking booking);
    List<Booking> findByRoom(String roomId);
    List<Booking> findByUser(String userId);
}
