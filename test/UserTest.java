import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void testIsAdmin() {
        User admin = new User("admin", "1234", "ADMIN");
        User user = new User("user01", "1111", "USER");

        assertTrue(admin.isAdmin());
        assertFalse(user.isAdmin());
    }
}