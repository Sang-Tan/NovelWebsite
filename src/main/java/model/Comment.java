package model;

import core.DatabaseObject;
import core.logging.BasicLogger;
import core.metadata.JSONSerializable;
import org.json.JSONException;
import org.json.JSONObject;
import repository.CommentReportRepository;
import repository.CommentRepository;
import repository.UserRepository;

import javax.persistence.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "comments", schema = "novelweb")
public class Comment implements DatabaseObject, JSONSerializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Column(name = "chapter_id", nullable = false)
    private int chapterId;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "deactive_by")
    private Integer deactiveBy;
    @Column(name = "time_comment", nullable = false)
    private Timestamp commentTime;
    @Column(name = "parent_id")
    private Integer parentId;

    @OneToOne
    private User owner;

    @OneToMany
    private List<Comment> replies;

    @OneToMany
    private List<CommentReport> reports;

    public Comment() {
    }

    public Comment(int id, int userId, String content, int deactiveBy, Timestamp commentTime, int parentId) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.deactiveBy = deactiveBy;
        this.commentTime = commentTime;
        this.parentId = parentId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getDeactiveBy() {
        return deactiveBy;
    }

    public void setDeactiveBy(Integer deactiveBy) {
        this.deactiveBy = deactiveBy;
    }
//    public User getDeactiveByUser() throws SQLException {
//
//        if(deactiveByUser == null) {
//            UserRepository.getInstance().getById(deactiveBy);
//        }
//        return deactiveByUser;
//    }
//
//    public void setDeactiveByUser(User user) {
//
//        this.deactiveByUser = user;
//        deactiveBy = user.getId();
//    }

    public Timestamp getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Timestamp commentTime) {
        this.commentTime = commentTime;
    }

    //    public int getParentComment() throws SQLException {
//
//        if(parentComment == null) {
//            parentComment = CommentRepository.getInstance().getById(parentId);
//        }
//        return parentId;
//    }
//
//    public void setParent(Comment parentComment) {
//        this.parentId = parentId;
//        this.parentComment = parentComment;
//    }
    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public List<Comment> getReplies() {
        if (replies == null) {
            try {
                replies = CommentRepository.getInstance().getRepliesComments(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return replies;
    }

    public void setReplies(List<Comment> replies) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("userId", userId);
        json.put("content", content);
        json.put("deactiveBy", deactiveBy);
        json.put("commentTime", commentTime);
        return json;
    }

    public User getOwner() {
        if (owner == null) {
            try {
                owner = UserRepository.getInstance().getById(userId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return owner;
    }

    public void setOwner(User owner) {
        throw new UnsupportedOperationException();
    }

    public List<CommentReport> getReports() {
        if (reports == null) {
            try {
                reports = CommentReportRepository.getInstance().getAllReportContentByCommentId(this.id);
            } catch (SQLException e) {
                BasicLogger.getInstance().getLogger().warning(e.getMessage());
                return null;
            }
        }
        return reports;
    }

    public void setReports(List<CommentReport> reports) {
        this.reports = reports;
    }
}
