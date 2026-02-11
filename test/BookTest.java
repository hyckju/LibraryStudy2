import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    @Test
    void testUpdateBook() {
        Book book = new Book(1, "Old Title", "Old Author", true, null);
        book.updateBook("New Title", "New Author");

        assertEquals("New Title", book.getTitle());
        assertEquals("New Author", book.getAuthor());
    }

    @Test
    void testAvailabilityChange() {
        Book book = new Book(1, "Java", "Kim", true, null);
        book.setAvailable(false);
        book.setBorrowerId("user01");

        assertFalse(book.isAvailable());
        assertEquals("user01", book.getBorrowerId());
    }
}