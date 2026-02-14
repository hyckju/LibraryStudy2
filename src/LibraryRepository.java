import java.io.*;
import java.sql.*;
import java.util.*;

public class LibraryRepository {
    // DB 연결 정보 (본인의 DB 정보에 맞게 수정하세요)
    private final String URL = "jdbc:mariadb://localhost:3306/library_db";
    private final String USER = "root";
    private final String PASS = "did0125"; // 본인이 설정한 비밀번호
    // MariaDB에서 도서 목록 불러오기
    public Map<Integer, Book> loadBookData() {
        Map<Integer, Book> books = new HashMap<>();
        String sql = "SELECT * FROM books";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                books.put(id, new Book(
                        id,
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getBoolean("available"),
                        rs.getString("borrower_id")
                ));
            }
        } catch (SQLException e) {
            System.err.println("DB 로드 중 오류: " + e.getMessage());
        }
        return books;
    }
    // MariaDB에서 사용자 목록 불러오기
    public Map<String, User> loadUserData() {
        Map<String, User> users = new HashMap<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String userId = rs.getString("user_id");
                users.put(userId, new User(userId, rs.getString("password"), rs.getString("role")));
            }
        } catch (SQLException e) {
            // DB에 데이터가 없으면 기본 계정이라도 반환
            users.put("admin", new User("admin", "1234", "ADMIN"));
        }
        return users;
    }
    // 프로그램 종료 시 모든 변경 사항을 DB에 한꺼번에 저장 (Upsert 로직)
    public void saveChanges(Map<Integer, Book> books, Map<String, User> users) {
        // 데이터가 있으면 업데이트, 없으면 삽입 (MariaDB 전용 구문)
        String sql = "INSERT INTO books (id, title, author, available, borrower_id) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE title=?, author=?, available=?, borrower_id=?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (Book b : books.values()) {
                pstmt.setInt(1, b.getId());
                pstmt.setString(2, b.getTitle());
                pstmt.setString(3, b.getAuthor());
                pstmt.setBoolean(4, b.isAvailable());
                pstmt.setString(5, b.getBorrowerId());
                // 업데이트 부분
                pstmt.setString(6, b.getTitle());
                pstmt.setString(7, b.getAuthor());
                pstmt.setBoolean(8, b.isAvailable());
                pstmt.setString(9, b.getBorrowerId());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println(" => 데이터베이스 저장 완료.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}