package model;

import core.DatabaseObject;
import core.logging.BasicLogger;
import repository.ChapterRepository;
import repository.NovelRepository;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.List;

@Entity
@Table(name = "volumes", uniqueConstraints = {@UniqueConstraint(columnNames = {"novel_id", "order_index"})})
public class Volume implements DatabaseObject, INovelContent {
    public static final String APPROVE_STATUS_PENDING = "pending";
    public static final String APPROVE_STATUS_REJECTED = "rejected";
    public static final String APPROVE_STATUS_APPROVED = "approved";
    public static final String DEFAULT_IMAGE = "/images/default-cover.jpg";
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "novel_id", nullable = false)
    private int novelId;
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "image", nullable = true, length = 255)
    private String image;
    @Column(name = "order_index", nullable = false)
    private int orderIndex;
    @OneToMany(mappedBy = "belongVolume")
    private List<Chapter> chapters;
    @ManyToOne
    @JoinColumn(name = "novel_id", referencedColumnName = "id", nullable = false)
    private Novel belongNovel;

    @Column(name = "approval_status", nullable = false)
    private String approvalStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNovelId() {
        return novelId;
    }

    public void setNovelId(int novelId) {
        this.novelId = novelId;
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

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public List<Chapter> getChapters() {
        if (chapters == null) {
            try {
                chapters = ChapterRepository.getInstance().getByVolumeId(id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        throw new UnsupportedOperationException("Set chapters in volume entity is not supported");
    }

    public Novel getBelongNovel() {
        if (belongNovel == null) {
            try {
                belongNovel = NovelRepository.getInstance().getById(novelId);
            } catch (SQLException e) {
                BasicLogger.getInstance().printStackTrace(e);
            }
        }
        return belongNovel;
    }

    public void setBelongNovel(Novel belongNovel) {
        throw new UnsupportedOperationException("Set novel in volume entity is not supported");
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
}