package model;

import core.DatabaseObject;
import repository.VolumeRepository;

import java.sql.SQLException;
import java.sql.Timestamp;

public class Chapter implements DatabaseObject {
    public static final String DEFAULT_CONTENT = "Không có nội dung";
    private int id;
    private String name;
    private int orderIndex;
    private Volume volume;
    private int volumeId;
    private String content;
    private Timestamp modifyTime;
    private boolean pending;

    public Chapter() {
    }

    public Chapter(int id, int orderIndex, Volume volume, String content, Timestamp modifyTime, boolean pending) {
        this.id = id;
        this.orderIndex = orderIndex;
        this.volume = volume;
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

    public Volume getVolume() throws SQLException {
        volume = VolumeRepository.getInstance().getById(volumeId);
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volumeId = volume.getId();
        this.volume = volume;
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
