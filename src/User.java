public class User {
    private String userId;
    private String password;
    private String role;

    public User(String id, String password, String role) {
        this.userId = id;
        this.password = password;
        this.role = role;
    }

    public String getUserId() {
        return this.userId;
    }
    public String getPassword() {
        return this.password;
    }
    public String getRole() {
        return this.role;
    }
    public boolean isAdmin() { return "admin".equalsIgnoreCase(role); }
}
