package school21.spring.service.models;

public class User {

    private long identifier;
    private String email = null;

    public User(long identifier, String email) {
        this.identifier = identifier;
        this.email = email;
    }

    public void setIdentifier(long identifier) {
        this.identifier = identifier;
    }

    public User(String email) {
        this.email = email;
    }

    public User(){};
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "Identifier=" + identifier +
                ", email='" + email + '\'' +
                '}';
    }

    public long getIdentifier() {
        return identifier;
    }

    public String getEmail() {
        return email;
    }

    public Object getId() {
        return identifier;
    }
}
