package model;

import core.DatabaseObject;
import repository.VolumeRepository;

import javax.persistence.*;
import java.sql.SQLException;
import java.sql.Timestamp;

@Entity
@Table(name = "chapters", schema = "novelweb")
public class Chapter implements DatabaseObject, INovelContent {
    public static final String APPROVE_STATUS_PENDING = "pending";
    public static final String APPROVE_STATUS_REJECTED = "rejected";
    public static final String APPROVE_STATUS_APPROVED = "approved";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "order_index", nullable = false)
    private int orderIndex;


    @Column(name = "content", nullable = true, length = -1)
    private String content;

    @Column(name = "created_at")
    private Timestamp createdTime;
    
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedTime;

    @Column(name = "approval_status", nullable = false)
    private String approvalStatus;

    @Column(name = "volume_id", nullable = false)
    private int volumeId;

    @ManyToOne
    @JoinColumn(name = "volume_id", referencedColumnName = "id", nullable = false)
    private Volume belongVolume;

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

    public Volume getBelongVolume() throws SQLException {
        if (belongVolume == null)
            belongVolume = VolumeRepository.getInstance().getById(volumeId);
        return belongVolume;
    }

    public void setBelongVolume(Volume volume) {

        this.belongVolume = volume;
        this.volumeId = volume.getId();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }
}