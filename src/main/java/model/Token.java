package model;

public class Token {
    private int id;
    private User userId;
    private String validatorHash;

    public Token() {
    }

    public Token(int id, User userId, String validatorHash) {
        this.id = id;
        this.userId = userId;
        this.validatorHash = validatorHash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getValidatorHash() {
        return validatorHash;
    }

    public void setValidatorHash(String validatorHash) {
        this.validatorHash = validatorHash;
    }
}
