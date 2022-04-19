package com.stakhiyevich.openadboard.model.entity.dto;

import java.math.BigDecimal;

public class BookmarkEntityDto {

    private long userId;
    private long itemId;
    private String itemTitle;
    private BigDecimal itemPrice;
    private String itemDescription;
    private String itemPicture;
    private String itemCity;

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

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemPicture() {
        return itemPicture;
    }

    public void setItemPicture(String itemPicture) {
        this.itemPicture = itemPicture;
    }

    public String getItemCity() {
        return itemCity;
    }

    public void setItemCity(String itemCity) {
        this.itemCity = itemCity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookmarkEntityDto)) return false;

        BookmarkEntityDto that = (BookmarkEntityDto) o;

        if (getUserId() != that.getUserId()) return false;
        if (getItemId() != that.getItemId()) return false;
        if (getItemTitle() != null ? !getItemTitle().equals(that.getItemTitle()) : that.getItemTitle() != null)
            return false;
        if (getItemPrice() != null ? !getItemPrice().equals(that.getItemPrice()) : that.getItemPrice() != null)
            return false;
        if (getItemDescription() != null ? !getItemDescription().equals(that.getItemDescription()) : that.getItemDescription() != null)
            return false;
        if (getItemPicture() != null ? !getItemPicture().equals(that.getItemPicture()) : that.getItemPicture() != null)
            return false;
        return getItemCity() != null ? getItemCity().equals(that.getItemCity()) : that.getItemCity() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getUserId() ^ (getUserId() >>> 32));
        result = 31 * result + (int) (getItemId() ^ (getItemId() >>> 32));
        result = 31 * result + (getItemTitle() != null ? getItemTitle().hashCode() : 0);
        result = 31 * result + (getItemPrice() != null ? getItemPrice().hashCode() : 0);
        result = 31 * result + (getItemDescription() != null ? getItemDescription().hashCode() : 0);
        result = 31 * result + (getItemPicture() != null ? getItemPicture().hashCode() : 0);
        result = 31 * result + (getItemCity() != null ? getItemCity().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BookmarkEntityDto{");
        sb.append("userId=").append(userId);
        sb.append(", itemId=").append(itemId);
        sb.append(", itemTitle='").append(itemTitle).append('\'');
        sb.append(", itemPrice=").append(itemPrice);
        sb.append(", itemDescription='").append(itemDescription).append('\'');
        sb.append(", itemPicture='").append(itemPicture).append('\'');
        sb.append(", itemCity='").append(itemCity).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
