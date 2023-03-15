package model;

public class Token {
    private int id;
    private int userId;
    private String validatorHash;

    public Token() {
    }

    public Token(int id, int userId, String validatorHash) {
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getValidatorHash() {
        return validatorHash;
    }

    public void setValidatorHash(String validatorHash) {
        this.validatorHash = validatorHash;
    }
}
