
package model;

import core.DatabaseObject;
import repository.VolumeRepository;

import javax.persistence.*;
import java.sql.SQLException;
import java.sql.Timestamp;

@Entity
@Table(name = "chapters", schema = "novelweb")
public class Chapter implements DatabaseObject {

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

    @Column(name = "modify_time", nullable = false)
    private Timestamp modifyTime;

    @Column(name = "approval_status", nullable = false)
    private String approvalStatus;

    @Column(name = "volume_id", nullable = false)
    private int volumeId;

//    @OneToMany(mappedBy = "chapter")
//    private List<ChapterMark> ownershipChapterMarks;

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

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    //
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Chapter chapter = (Chapter) o;
//        return id == chapter.id && orderIndex == chapter.orderIndex && belongVolume == chapter.belongVolume
//                && isPending == chapter.isPending && Objects.equals(name, chapter.name)
//                && Objects.equals(content, chapter.content) && Objects.equals(modifyTime, chapter.modifyTime);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name, orderIndex, belongVolume, content, modifyTime, isPending);
//    }
//
//    public List<ChapterMark> getOwnershipChapterMarks() {
//        return ownershipChapterMarks;
//    }
//
//    public void addOwnershipChapterMark(ChapterMark chapterMark) {
//        ownershipChapterMarks.add(chapterMark);
//    }
//
//    public void updateOwnershipChapterMark(ChapterMark chapterMark) {
//        for (int i = 0; i < ownershipChapterMarks.size(); i++) {
//            if (ownershipChapterMarks.get(i).getChapter() == chapterMark.getChapter()
//                    && ownershipChapterMarks.get(i).getUser() == chapterMark.getUser()) {
//                ownershipChapterMarks.set(i, chapterMark);
//                break;
//            }
//        }
//    }
//
//    public void deleteOwnershipChapterMark(ChapterMark chapterMark) {
//        deleteOwnershipChapterMark(chapterMark.getChapter().getId(), chapterMark.getUser().getId());
//    }
//
//    public void deleteOwnershipChapterMark(int chapterId, int userId) {
//        for (int i = 0; i < ownershipChapterMarks.size(); i++) {
//            if (ownershipChapterMarks.get(i).getChapter().getId() == chapterId
//                    && ownershipChapterMarks.get(i).getUser().getId() == userId) {
//                ownershipChapterMarks.remove(i);
//                break;
//            }
//        }
//    }
}