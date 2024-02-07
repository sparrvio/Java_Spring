package school21.spring.service.models;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("userBean")
public class User {

    private long identifier;
    private String email = null;
    private String password = null;

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

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return identifier == user.identifier && email.equals(user.email) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, email, password);
    }

    public User() {
    }

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
