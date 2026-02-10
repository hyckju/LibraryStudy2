public class Book {
    private final int id;
    private String title;
    private String author;
    private boolean available;
    private String borrowerId;

    public Book(int id, String title, String author, boolean available, String borrowerId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = true;
        this.borrowerId = borrowerId;
    }
    public void borrowBook(String userId) {
        this.available = false;
        this.borrowerId = userId;
    }
    public void returnBook() {
        this.available = true;
        this.borrowerId = null;
    }

    public void updateInfo(String title, String author) {
        this.title = this.title;
        this.author = this.author;
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
    //가용성을 위해 setter사용
    public void setAvailable(boolean available) {
        this.available = available;
    }
    //위와 동일
    public void setBorrowerId(String borrowerId) {
        this.borrowerId = borrowerId;
    }
    public void updateBook(String title, String author) {//객체지향 프로그램의 중요성인 응집도 높이기
        this.title = title;
        this.author = author;
    }
}