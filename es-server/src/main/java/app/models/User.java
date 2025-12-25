package app.models;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

import java.util.Random;

@NamedQuery(name = "Accounts_find_by_email",
        query = "select u from User u where u.email= ?1")
@Entity
@Table(name = "users")
public class User {
    @JsonView(View.Shallow.class)
    @Id
    private long id;
    @JsonView(View.Shallow.class)
    private String name;
    @JsonView(View.Summary.class)
    private String email = "";
    private String hashedPassword = null;
    @JsonView(View.Summary.class)
    private String role = "Regular user";

    public User() {
    }

    public User(long id) {
        this.id = id;
    }

    public User(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public boolean verifyPassword(String password) {
        if (email == null || !email.contains("@")) {
            return false;
        }
        String expect = email.substring(0, email.indexOf("@"));
        return expect.equals(password);
    }

    private static Random randomizer = new Random();

    public static User createSample(long id) {
        return createSample(id, "test");
    }

    public static User createSample(long id, String callName) {
        User newUser = new User(id, callName, callName.toLowerCase() + "@hva.nl");
        // password needs to be reset later, the password hash changes if the account id changes.
        newUser.setRole("registered");

        return newUser;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
