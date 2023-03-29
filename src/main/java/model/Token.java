package model;

import core.DatabaseObject;
import repository.UserRepository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DateTimeException;

public class Token implements DatabaseObject {
    private int id;
    private int userId ;
    private String tokenHash;
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

    public void setHashToken(String tokenHash) {
        this.tokenHash = tokenHash;
    }

    public Timestamp getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Timestamp expiredTime) {
        this.expiredTime = expiredTime;
    }

}
