import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class LibraryRepositoryDBTest {
    private LibraryRepository repository;

    @BeforeEach
    void setUp() {
        repository = new LibraryRepository();
    }

    @Test
    @DisplayName("DB 저장 및 로드 통합 테스트")
    void testSaveAndLoad() {
        //테스트용 데이터 준비
        Map<Integer, Book> testBooks = new HashMap<>();
        Map<String, User> testUsers = new HashMap<>();

        //고유한 ID를 가진 테스트 도서 생성
        int testId = 9999;
        Book testBook = new Book(testId, "DB_TEST_BOOK", "DB_TEST_AUTHOR", true, null);
        testBooks.put(testId, testBook);

        //DB에 저장 (프로그램 종료 시 호출되는 로직
        assertDoesNotThrow (() -> repository.saveChanges(testBooks, testUsers));

        //DB에서 다시 불러오기
        Map<Integer, Book> loadBooks = repository.loadBookData();

        //검증
        assertTrue(loadBooks.containsKey(testId), "저장한 도서 ID가 DB에 존재해야 합니다.");
        Book loadedBook = loadBooks.get(testId);
        assertEquals("DB_TEST_BOOK", loadedBook.getTitle());
        assertEquals("DB_TEST_AUTHOR", loadedBook.getAuthor());

        System.out.println("DB 데이터 정합성 확인 완료");

    }
}