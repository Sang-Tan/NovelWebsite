package model;

import core.DatabaseObject;
import repository.NovelRepository;

import javax.persistence.*;
import java.sql.SQLException;
import java.sql.Date;

@Entity
@Table(name = "view_in_novel", uniqueConstraints = {@UniqueConstraint(columnNames = {"novel_id", "date_view"})})
public class ViewInNovel implements DatabaseObject {
    @Id
    @Column(name = "novel_id", nullable = false)
    private int novelId;
    @ManyToOne()
    @JoinColumn(name = "novel_id", referencedColumnName = "id", nullable = false)
    private Novel novel;
    @Id
    @Column(name = "date_view", nullable = false, length = 255)
    private Date dateView;

    @Column(name = "view_count", nullable = false)
    private int viewCount;

    public ViewInNovel(int novelId, Date dateView, int viewCount) {
        this.novelId = novelId;
        this.dateView = dateView;
        this.viewCount = viewCount;
    }


    public ViewInNovel() {
    }
    public Novel getNovel() throws SQLException {
        if(novel == null || novel.getId() != novelId)
            novel = NovelRepository.getInstance().getById(novelId);
        return novel;
    }
    public void setNovel(Novel novel) {
        this.novelId = novel.getId();
        this.novel = novel;
    }
    public void setNovelId(Integer novelId) {
        this.novelId = novelId;
    }
    public int getNovelId() {
        return novelId;
    }
    public void setNovelId(int novel_id) {
        this.novelId = novel_id;
    }
    public Date getDateView() {
        return dateView;
    }
    public void setDateView(Date date) {
        this.dateView = date;
    }
    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
    public int getViewCount() {
        return viewCount;
    }
}
