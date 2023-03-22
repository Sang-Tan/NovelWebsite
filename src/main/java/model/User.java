package model;

import core.DatabaseObject;

public class User implements DatabaseObject {
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_USER = "user";
    public static final String DEFAULT_AVATAR = "/images/default-avatar.jfif";

    private int id;
    private String password;
    private String username;
    private String displayName;
    private boolean active;
    private String avatar;
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

    public Integer getId() {
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
