import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryManager {
    private Map<Integer, Book> bookMap = new HashMap<>();
    private List<User> userList = new ArrayList<>();
    private User currentUser;
    private final LibraryRepository libraryRepository;

    public LibraryManager(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
        this.initialize();
    }

    public void initialize() {
        this.bookMap = this.libraryRepository.getBookMap();
        this.userList = this.libraryRepository.loadUsers();
    }

    public boolean login(String id, String password) {
        for(User user : this.userList) {
            if (user.getId().equals(id) && user.getPassword().equals(password)) {
                this.currentUser = user;
                return true;
            }
        }

        return false;
    }

    public void addBook(String title, String author) {
        int newId = this.libraryRepository.getBookCount() + 1;
        Book newBook = new Book(newId, title, author, true);
        this.libraryRepository.getBookMap().put(newId, newBook);
    }

    public void editBook(int id, String title, String author) {
        Map<Integer, Book> books = this.libraryRepository.getBookMap();
        if (books.containsKey(id)) {
            ((Book)books.get(id)).updateBook(title, author);
        }
    }

    public boolean deleteBook(int id) {
        return this.libraryRepository.getBookMap().remove(id) != null;
    }

    public void saveChanges() {
        this.libraryRepository.saveBooks((HashMap)this.bookMap);
    }

    public User getCurrentUser() {
        return this.currentUser;
    }
}
