import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LibraryRepository {
    private HashMap<Integer, Book> bookMap = new HashMap<>();
    private final List<User> users = new ArrayList<>();

    public HashMap<Integer, Book> getBookMap() {
        if (this.bookMap.isEmpty()) {
            this.bookMap.put(1, new Book(1, "Java", "Kim", true));
            this.bookMap.put(2, new Book(2, "Python", "Lee", true));
        }

        return this.bookMap;
    }

    public int getBookCount() {
        return this.bookMap.size();
    }

    public void saveBooks(HashMap<Integer, Book> books) {
        this.bookMap = books;
    }

    public List<User> loadUsers() {
        if (this.users.isEmpty()) {
            this.users.add(new User("admin", "1234", "admin"));
            this.users.add(new User("user", "1111", "user"));
        }

        return this.users;
    }
}

