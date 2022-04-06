package com.stakhiyevich.openadboard.model.entity;

public class Bookmark extends AbstractEntity {

    private long userId;
    private long itemId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bookmark)) return false;

        Bookmark bookmark = (Bookmark) o;

        if (getUserId() != bookmark.getUserId()) return false;
        return getItemId() == bookmark.getItemId();
    }

    @Override
    public int hashCode() {
        int result = (int) (getUserId() ^ (getUserId() >>> 32));
        result = 31 * result + (int) (getItemId() ^ (getItemId() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BookmarkEntity{");
        sb.append("userId=").append(userId);
        sb.append(", itemId=").append(itemId);
        sb.append('}');
        return sb.toString();
    }
}
