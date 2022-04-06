package com.stakhiyevich.openadboard.model.entity;

import java.time.LocalDateTime;

public class Comment {

    private String text;
    private LocalDateTime createTime;
    private long itemId;
    private long userId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;

        Comment comment = (Comment) o;

        if (getItemId() != comment.getItemId()) return false;
        if (getUserId() != comment.getUserId()) return false;
        if (getText() != null ? !getText().equals(comment.getText()) : comment.getText() != null) return false;
        return getCreateTime() != null ? getCreateTime().equals(comment.getCreateTime()) : comment.getCreateTime() == null;
    }

    @Override
    public int hashCode() {
        int result = getText() != null ? getText().hashCode() : 0;
        result = 31 * result + (getCreateTime() != null ? getCreateTime().hashCode() : 0);
        result = 31 * result + (int) (getItemId() ^ (getItemId() >>> 32));
        result = 31 * result + (int) (getUserId() ^ (getUserId() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CommentEntity{");
        sb.append("text='").append(text).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", itemId=").append(itemId);
        sb.append(", userId=").append(userId);
        sb.append('}');
        return sb.toString();
    }
}
