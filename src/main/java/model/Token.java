package model;

import core.DatabaseObject;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tokens", uniqueConstraints = {@UniqueConstraint(columnNames = {"token_hash"})})
public class Token implements DatabaseObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Column(name = "token_hash", nullable = false)
    private String tokenHash;
    @Column(name = "expired_time", nullable = false)
    private Timestamp expiredTime;

    public Token() {
    }

    public Token(int id, int userId, String tokenHash) {
        this.id = id;
        this.userId = userId;
        this.tokenHash = tokenHash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getTokenHash() {
        return tokenHash;
    }

    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }

    public Timestamp getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Timestamp expiredTime) {
        this.expiredTime = expiredTime;
    }

}
