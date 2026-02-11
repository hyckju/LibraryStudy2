import java.io.*;
import java.util.*;

public class LibraryRepository {
    private final String BOOK_CSV = "book.csv";
    private final String USER_CSV = "user.csv";

    public Map<Integer, Book> loadBookData() {
        Map<Integer, Book> books = new HashMap<>();
        File f = new File(BOOK_CSV);
        if (!f.exists()) return books;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.replace("\uFEFF", "").split(",", -1);
                if (d.length < 5) continue;
                int id = Integer.parseInt(d[0]);
                books.put(id, new Book(id, d[1], d[2], Boolean.parseBoolean(d[3]), d[4].isEmpty() ? null : d[4]));
            }
        } catch (IOException e) {}
        return books;
    }

    public Map<String, User> loadUserData() {
        Map<String, User> users = new HashMap<>();
        File f = new File(USER_CSV);
        if (!f.exists()) {
            users.put("admin", new User("admin", "1234", "admin"));
            users.put("user01", new User("user01", "1111", "user"));
            return users;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",", -1);
                users.put(d[0], new User(d[0], d[1], d[2]));
            }
        } catch (IOException e) {}
        return users;
    }

    public void saveChanges(Map<Integer, Book> books, Map<String, User> users) {
        try (PrintWriter pwB = new PrintWriter(new FileWriter(BOOK_CSV));
             PrintWriter pwU = new PrintWriter(new FileWriter(USER_CSV))) {
            for (Book b : books.values()) {
                pwB.printf("%d,%s,%s,%b,%s%n", b.getId(), b.getTitle(), b.getAuthor(), b.isAvailable(), b.getBorrowerId() == null ? "" : b.getBorrowerId());
            }
            for (User u : users.values()) {
                pwU.printf("%s,%s,%s%n", u.getUserId(), u.getPassword(), u.getRole());
            }
        } catch (IOException e) {}
    }
}