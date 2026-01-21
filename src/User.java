public class User {
    private String id;
    private String password;
    private String role;

    public User(String id, String password, String role) {
        this.id = id;
        this.password = password;
        this.role = role;
    }

    public String getId() {
        return this.id;
    }

    public String getPassword() {
        return this.password;
    }

    public String getRole() {
        return this.role;
    }

    public boolean isAdmin() {
        return this.role.equals("admin");
    }
}
