
package model;

import model.intermediate.ChapterMark;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "chapters", schema = "novelweb", catalog = "")
public class Chapters {
    public static final String DEFAULT_CONTENT = "Không có nội dung";
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic
    @Column(name = "order_index", nullable = false)
    private int orderIndex;
    @Basic
    @Column(name = "volume_id", nullable = false)
    private int volumeId;
    @Basic
    @Column(name = "content", nullable = true, length = -1)
    private String content;
    @Basic
    @Column(name = "modify_time", nullable = false)
    private Timestamp modifyTime;
    @Basic
    @Column(name = "is_pending", nullable = false)
    private byte isPending;
    @OneToMany(mappedBy = "belongChapter")
    private List<ChapterMark> ownershipChapterMarks;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chapters chapters = (Chapters) o;
        return id == chapters.id && orderIndex == chapters.orderIndex && volumeId == chapters.volumeId && isPending == chapters.isPending && Objects.equals(name, chapters.name) && Objects.equals(content, chapters.content) && Objects.equals(modifyTime, chapters.modifyTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, orderIndex, volumeId, content, modifyTime, isPending);
    }

    public List<ChapterMark> getOwnershipChapterMarks() {
        return ownershipChapterMarks;
    }

    public void addOwnershipChapterMark(ChapterMark chapterMark) {
        ownershipChapterMarks.add(chapterMark);
    }

    public void updateOwnershipChapterMark(ChapterMark chapterMark) {
        for (int i = 0; i < ownershipChapterMarks.size(); i++) {
            if (ownershipChapterMarks.get(i).getChapterId() == chapterMark.getChapterId()
                    && ownershipChapterMarks.get(i).getUserId() == chapterMark.getUserId()) {
                ownershipChapterMarks.set(i, chapterMark);
                break;
            }
        }
    }
    public void deleteOwnershipChapterMark(ChapterMark chapterMark) {
        deleteOwnershipChapterMark(chapterMark.getChapterId(), chapterMark.getUserId());
    }
    public void deleteOwnershipChapterMark(int chapterId, int userId) {
        for (int i = 0; i < ownershipChapterMarks.size(); i++) {
            if (ownershipChapterMarks.get(i).getChapterId() == chapterId
                    && ownershipChapterMarks.get(i).getUserId() == userId) {
                ownershipChapterMarks.remove(i);
                break;
            }
        }
    }

    public Volume getBelongVolume() {
        return belongVolume;
    }

    public void setBelongVolume(Volume volume) {
        this.belongVolume = volume;
    }
}