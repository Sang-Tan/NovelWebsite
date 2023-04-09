package model;

import core.DatabaseObject;
import core.logging.BasicLogger;
import repository.GenreRepository;
import repository.UserRepository;
import repository.VolumeRepository;

import javax.persistence.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "novels")
public class Novel implements DatabaseObject {
    public static final String STATUS_ON_GOING = "on going";
    public static final String STATUS_FINISHED = "finished";
    public static final String STATUS_PAUSED = "paused";
    public static final String DEFAULT_IMAGE = "/images/default-cover.jpg";
    public static final String APPROVE_STATUS_PENDING = "pending";
    public static final String APPROVE_STATUS_REJECTED = "rejected";
    public static final String APPROVE_STATUS_APPROVED = "approved";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "owner", nullable = false)
    private int ownerID;
    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    private User owner;
    @Column(name = "summary", nullable = false)
    private String summary;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "image")
    private String image;
    @Column(name = "approval_status", nullable = false)
    private String approvalStatus;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "created_at")
    private Timestamp createdTime;
    @OneToMany(mappedBy = "belongNovel")
    private List<Volume> volumes = null;

    @OneToMany
    private Collection<Genre> genres = null;

    public Novel() {
    }

    public Novel(int id, User owner, String summary, String name,
                 String image, String approvalStatus, String status, Timestamp createdTime) {
        this.id = id;
        this.owner = owner;
        this.summary = summary;
        this.name = name;
        this.image = image;
        this.approvalStatus = approvalStatus;
        this.status = status;
        this.createdTime = createdTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getOwner() throws SQLException {
        if (owner == null || owner.getId() != ownerID) {
            owner = UserRepository.getInstance().getById(ownerID);
        }
        return owner;
    }

    public void setOwner(User owner) {
        this.ownerID = owner.getId();
        this.owner = owner;
    }


    public int getOwnerID() {
        return ownerID;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String pending) {
        this.approvalStatus = pending;
    }

    public String getStatus() {
        return status;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public List<Volume> getVolumes() {
        if (volumes == null) {
            try {
                volumes = VolumeRepository.getInstance().getByNovelId(id);
            } catch (SQLException e) {
                BasicLogger.getInstance().getLogger().warning(e.getMessage());
                return null;
            }
        }
        return volumes;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }

    public Collection<Genre> getGenres() {
        if (genres == null) {
            try {
                genres = GenreRepository.getInstance().getByNovelId(id);
            } catch (SQLException e) {
                BasicLogger.getInstance().getLogger().warning(e.getMessage());
                return null;
            }
        }
        return genres;
    }

    public void setGenres(Collection<Genre> genres) {
        this.genres = genres;
    }
}
