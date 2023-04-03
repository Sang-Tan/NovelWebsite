package model;

import core.DatabaseObject;
import core.logging.BasicLogger;
import repository.NovelRepository;
import repository.UserRepository;
import repository.VolumeRepository;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.List;

@Entity
@Table(name = "novels")
public class Novel implements DatabaseObject {
    public static final String STATUS_ON_GOING = "on going";
    public static final String STATUS_FINISHED = "finished";
    public static final String STATUS_PAUSED = "paused";
    public static final String DEFAULT_IMAGE = "/images/default-cover.jpg";
    public static final String DEFAULT_SUMMARY = "Không có tóm tắt";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "owner")
    private int ownerID;
    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;
    @Column(name = "summary")
    private String summary;
    @Column(name = "name")
    private String name;
    @Column(name = "image")
    private String image;
    @Column(name = "is_pending")
    private boolean pending;
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "belongNovel")
    private List<Volume> volumes = null;

    public Novel() {
    }

    public Novel(int id, User owner, String summary, String name,
                 String image, boolean pending, String status) {
        this.id = id;
        this.owner = owner;
        this.summary = summary;
        this.name = name;
        this.image = image;
        this.pending = pending;
        this.status = status;
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

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
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

    public List<Volume> getVolumes() {
        if (volumes == null) {
            try {
                volumes = VolumeRepository.getInstance().getByNovelId(id).stream().toList();
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
}
