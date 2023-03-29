package model;

import core.DatabaseObject;

import java.sql.Timestamp;

public class Chapter implements DatabaseObject {
    private int id;
    private String name;
    private int orderIndex;
    private int volumeId;
    private String content;
    private Timestamp modifyTime;
    private boolean pending;

    public Chapter() {
    }

    public Chapter(int id, int orderIndex, int volumeId, String content, Timestamp modifyTime, boolean pending) {
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public int getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(int volumeId) {
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
