import java.util.*;

public class LibraryManager {
    private Map<Integer, Book> bookMap;
    private Map<String, User> userMap;
    private User currentUser = null;
    private LibraryRepository repository;
    private int bookCount;

    public LibraryManager(LibraryRepository repository) {
        this.repository = repository;
        this.bookMap = repository.loadBookData();
        this.userMap = repository.loadUserData();
        this.bookCount = bookMap.keySet().stream().max(Integer::compare).orElse(0);
    }

    public boolean login(String id, String pw) {
        if (userMap.containsKey(id) && userMap.get(id).getPassword().equals(pw)) {
            currentUser = userMap.get(id);
            return true;
        }
        return false;
    }

    public void addBook(String title, String author) {
        int id = ++bookCount;
        bookMap.put(id, new Book(id, title, author, true, null));
    }

    public boolean editBook(int id, String title, String author) {
        if (bookMap.containsKey(id)) {
            bookMap.get(id).updateBook(title, author);
            return true;
        }
        return false;
    }

    public boolean deleteBook(int id) {
        return bookMap.remove(id) != null;
    }

    public boolean borrowBook(int id) {
        Book b = bookMap.get(id);
        if (b != null && b.isAvailable()) {
            b.setAvailable(false);
            b.setBorrowerId(currentUser.getUserId());
            return true;
        }
        return false;
    }

    public boolean returnBook(int id) {
        Book b = bookMap.get(id);
        if (b != null && !b.isAvailable()) {
            b.setAvailable(true);
            b.setBorrowerId(null);
            return true;
        }
        return false;
    }

    public void saveChanges() {
        repository.saveChanges(bookMap, userMap);
    }

    public Map<Integer, Book> getBookMap() { return bookMap; }
    public User getCurrentUser() { return currentUser; }
}