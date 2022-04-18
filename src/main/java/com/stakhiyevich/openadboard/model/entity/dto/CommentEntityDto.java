package com.stakhiyevich.openadboard.model.entity.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CommentEntityDto implements Serializable {
    private long commentId;
    private long itemId;
    private long userId;
    private String text;
    private LocalDateTime createTime;
    private String userName;
    private String userAvatar;
    private String userRole;

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentEntityDto)) return false;

        CommentEntityDto that = (CommentEntityDto) o;

        if (getCommentId() != that.getCommentId()) return false;
        if (getItemId() != that.getItemId()) return false;
        if (getUserId() != that.getUserId()) return false;
        if (getText() != null ? !getText().equals(that.getText()) : that.getText() != null) return false;
        if (getCreateTime() != null ? !getCreateTime().equals(that.getCreateTime()) : that.getCreateTime() != null)
            return false;
        if (getUserName() != null ? !getUserName().equals(that.getUserName()) : that.getUserName() != null)
            return false;
        if (getUserAvatar() != null ? !getUserAvatar().equals(that.getUserAvatar()) : that.getUserAvatar() != null)
            return false;
        return getUserRole() != null ? getUserRole().equals(that.getUserRole()) : that.getUserRole() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getCommentId() ^ (getCommentId() >>> 32));
        result = 31 * result + (int) (getItemId() ^ (getItemId() >>> 32));
        result = 31 * result + (int) (getUserId() ^ (getUserId() >>> 32));
        result = 31 * result + (getText() != null ? getText().hashCode() : 0);
        result = 31 * result + (getCreateTime() != null ? getCreateTime().hashCode() : 0);
        result = 31 * result + (getUserName() != null ? getUserName().hashCode() : 0);
        result = 31 * result + (getUserAvatar() != null ? getUserAvatar().hashCode() : 0);
        result = 31 * result + (getUserRole() != null ? getUserRole().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CommentEntityDto{");
        sb.append("commentId=").append(commentId);
        sb.append(", itemId=").append(itemId);
        sb.append(", userId=").append(userId);
        sb.append(", text='").append(text).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", userAvatar='").append(userAvatar).append('\'');
        sb.append(", userRole='").append(userRole).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
