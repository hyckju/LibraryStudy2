public class Book {
    private final int id;
    private String title;
    private String author;
    private boolean available;
    private String borrowerId;

    public Book(int id, String title, String author, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public String getBorrowerId() {
        return this.borrowerId;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setBorrowerId(String borrowerId) {
        this.borrowerId = borrowerId;
    }

    public void updateBook(String title, String author) {
        this.title = title;
        this.author = author;
    }
}