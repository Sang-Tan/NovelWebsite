package model;

import java.sql.Timestamp;

public class Chapter {
    private int id;
    private int orderIndex;
    private Volume volumeId;
    private String content;
    private Timestamp modifyTime;
    private boolean pending;

    public Chapter() {
    }

    public Chapter(int id, int orderIndex, Volume volumeId, String content, Timestamp modifyTime, boolean pending) {
        this.id = id;
        this.orderIndex = orderIndex;
        this.volumeId = volumeId;
        this.content = content;
        this.modifyTime = modifyTime;
        this.pending = pending;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Volume getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(Volume volumeId) {
        this.volumeId = volumeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }
}
