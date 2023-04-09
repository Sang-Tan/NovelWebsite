package model;

import core.DatabaseObject;
import core.logging.BasicLogger;
import core.metadata.JSONSerializable;
import org.json.JSONException;
import org.json.JSONObject;
import repository.NovelRepository;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User implements DatabaseObject, JSONSerializable {
    public static final String DEFAULT_AVATAR = "images/default-avatar.jpg";
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_MODERATOR = "moderator";
    public static final String ROLE_MEMBER = "member";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "is_active", nullable = false)
    private boolean active;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "role", nullable = false)
    private String role;

    @OneToMany
    Collection<Novel> favouriteNovels;

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

    public Collection<Novel> getFavouriteNovels() {
        try {
            if (favouriteNovels == null) {
                favouriteNovels = NovelRepository.getInstance().getFavoriteNovelsByUserID(id);
            }
        } catch (Exception e) {
            BasicLogger.getInstance().printStackTrace(e);
        }
        return favouriteNovels;
    }

    public void setFavouriteNovels(Collection<Novel> favouriteNovels) {
        this.favouriteNovels = favouriteNovels;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("username", username);
        json.put("display_name", displayName);
        json.put("avatar", avatar);
        json.put("role", role);
        return json;
    }
}
