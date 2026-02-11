import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LibraryManagerTest {
    private LibraryManager manager;

    @BeforeEach
    void setUp() {
        LibraryRepository repository = new LibraryRepository(); // 실제 파일 대신 Mock을 쓸 수도 있지만, 여기선 기본 환경 사용
        manager = new LibraryManager(repository);
    }

    @Test
    void testAddAndLogin() {
        // 로그인 테스트
        boolean loginSuccess = manager.login("admin", "1234");
        assertTrue(loginSuccess);

        // 도서 추가 테스트
        int initialSize = manager.getBookMap().size();
        manager.addBook("JUnit Test", "Author");
        assertEquals(initialSize + 1, manager.getBookMap().size());
    }

    @Test
    void testBorrowAndReturn() {
        manager.login("user01", "1111");
        manager.addBook("Test Book", "Author");
        int bookId = manager.getBookMap().keySet().stream().max(Integer::compare).get();

        // 대출 검증
        assertTrue(manager.borrowBook(bookId));
        assertFalse(manager.getBookMap().get(bookId).isAvailable());

        // 반납 검증
        assertTrue(manager.returnBook(bookId));
        assertTrue(manager.getBookMap().get(bookId).isAvailable());
    }
}