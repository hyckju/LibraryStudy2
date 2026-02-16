import java.sql.*;

public class DBconn {
    private static final String URL = "jdbc:mariadb://localhost:3306/library_db";
    private static final String USER = "root";
    private static final String PASS = "did0125"; // 본인의 비밀번호

    // DB 연결 객체 반환
    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("[시스템] MariaDB 연결 성공");
        }catch (ClassNotFoundException e) {
            System.err.println("[오류] 드라이버를 찾을 수 없습니다:" + e.getMessage());
        }catch (SQLException e) {
            System.err.println("[오류] DB 연결 실패: " + e.getMessage() + "");
        }
        return conn;
    }

    // 연결 닫기 유틸리티
    public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
        try { if (conn != null) conn.close(); } catch (Exception e) {}
    }
}
