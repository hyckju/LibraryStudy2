import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class LibraryRepositoryTest {
    @Test
    void testSaveAndLoad() {
        LibraryRepository repo = new LibraryRepository();
        LibraryManager manager = new LibraryManager(repo);

        // 데이터 변경
        manager.addBook("Save Test", "Repo Author");

        // 종료 시 저장 시나리오
        manager.saveChanges();

        // 새로 로드해서 확인
        Map<Integer, Book> loadedBooks = repo.loadBookData();
        boolean found = loadedBooks.values().stream()
                .anyMatch(b -> b.getTitle().equals("Save Test"));

        assertTrue(found, "저장된 데이터가 다시 로드되어야 합니다.");
    }
}