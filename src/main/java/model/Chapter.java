
package model;

import model.intermediate.ChapterMark;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "chapters", schema = "novelweb", catalog = "")
public class Chapter {
    public static final String DEFAULT_CONTENT = "Không có nội dung";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Basic
    @Column(name = "order_index", nullable = false)
    private int orderIndex;


    @Basic
    @Column(name = "content", nullable = true, length = -1)
    private String content;

    @Basic
    @Column(name = "modify_time", nullable = false)
    private Timestamp modifyTime;

    @Basic
    @Column(name = "is_pending", nullable = false)
    private byte isPending;

    @OneToMany(mappedBy = "chapter")
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

    public Volume getBelongVolume() {
        return belongVolume;
    }

    public void setBelongVolume(Volume volume) {
        this.belongVolume = volume;
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
        Chapter chapter = (Chapter) o;
        return id == chapter.id && orderIndex == chapter.orderIndex && belongVolume == chapter.belongVolume
                && isPending == chapter.isPending && Objects.equals(name, chapter.name)
                && Objects.equals(content, chapter.content) && Objects.equals(modifyTime, chapter.modifyTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, orderIndex, belongVolume, content, modifyTime, isPending);
    }

    public List<ChapterMark> getOwnershipChapterMarks() {
        return ownershipChapterMarks;
    }

    public void addOwnershipChapterMark(ChapterMark chapterMark) {
        ownershipChapterMarks.add(chapterMark);
    }

    public void updateOwnershipChapterMark(ChapterMark chapterMark) {
        for (int i = 0; i < ownershipChapterMarks.size(); i++) {
            if (ownershipChapterMarks.get(i).getChapter() == chapterMark.getChapter()
                    && ownershipChapterMarks.get(i).getUser() == chapterMark.getUser()) {
                ownershipChapterMarks.set(i, chapterMark);
                break;
            }
        }
    }

    public void deleteOwnershipChapterMark(ChapterMark chapterMark) {
        deleteOwnershipChapterMark(chapterMark.getChapter().getId(), chapterMark.getUser().getId());
    }

    public void deleteOwnershipChapterMark(int chapterId, int userId) {
        for (int i = 0; i < ownershipChapterMarks.size(); i++) {
            if (ownershipChapterMarks.get(i).getChapter().getId() == chapterId
                    && ownershipChapterMarks.get(i).getUser().getId() == userId) {
                ownershipChapterMarks.remove(i);
                break;
            }
        }
    }
}