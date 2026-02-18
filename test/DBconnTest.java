import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBconnTest {

    @Test
    @DisplayName("MariaDB 연결 성공 테스트")
    void testGetConnection() {
        try (Connection conn = DBconn.getConnection()) {
            assertNotNull(conn, "연결 객체는 null일 수 없습니다.");
            assertTrue(conn.isValid(2), "DB 연결이 유효하지 않습니다.");
            System.out.println("DB 연결 성공");

        } catch (SQLException e) {
            fail("DB 연결 실패 : " + e.getMessage());
        }
    }
}