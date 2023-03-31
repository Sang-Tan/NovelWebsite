package model;

import core.DatabaseObject;

import javax.annotation.processing.Generated;
import javax.persistence.*;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User implements DatabaseObject {
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_MODERATOR = "moderator";
    public static final String ROLE_MEMBER = "member";
    public static final String DEFAULT_AVATAR = "/images/default-user-avatar.jpg";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "password")
    private String password;
    @Column(name = "username")
    private String username;
    @Column(name = "display_name")
    private String displayName;
    @Column(name = "is_active")
    private boolean active;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "role")
    private String role;

    public User() {
    }

    public User(int id, String password, String username, String displayName,
                boolean active, String avatar, String role) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.displayName = displayName;
        this.active = active;
        this.avatar = avatar;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
