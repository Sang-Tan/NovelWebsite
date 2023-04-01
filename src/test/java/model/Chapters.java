package model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "chapters")
public class Chapters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "order_index", nullable = false)
    private int orderIndex;

    @Column(name = "volume_id", nullable = false)
    private int volumeId;

    @Column(name = "content", nullable = true)
    private String content;

    @Column(name = "modify_time", nullable = false)
    private Timestamp modifyTime;

    @Column(name = "is_pending", nullable = false)
    private byte isPending;


    @ManyToOne
    @JoinColumn(name = "volume_id", referencedColumnName = "id", nullable = false)
    private Volumes volumesByVolumeId;

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

    public byte getIsPending() {
        return isPending;
    }

    public void setIsPending(byte isPending) {
        this.isPending = isPending;
    }


    public Volumes getVolumesByVolumeId() {
        return volumesByVolumeId;
    }

    public void setVolumesByVolumeId(Volumes volumesByVolumeId) {
        this.volumesByVolumeId = volumesByVolumeId;
    }



}
