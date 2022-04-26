package com.stakhiyevich.openadboard.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Item extends AbstractEntity {

    private String title;
    private BigDecimal price;
    private String description;
    private String contact;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String picture;
    private Category category;
    private User user;
    private City city;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        if (!super.equals(o)) return false;

        Item item = (Item) o;

        if (getTitle() != null ? !getTitle().equals(item.getTitle()) : item.getTitle() != null) return false;
        if (getPrice() != null ? !getPrice().equals(item.getPrice()) : item.getPrice() != null) return false;
        if (getDescription() != null ? !getDescription().equals(item.getDescription()) : item.getDescription() != null)
            return false;
        if (getContact() != null ? !getContact().equals(item.getContact()) : item.getContact() != null) return false;
        if (getCreateTime() != null ? !getCreateTime().equals(item.getCreateTime()) : item.getCreateTime() != null)
            return false;
        if (getUpdateTime() != null ? !getUpdateTime().equals(item.getUpdateTime()) : item.getUpdateTime() != null)
            return false;
        if (getPicture() != null ? !getPicture().equals(item.getPicture()) : item.getPicture() != null) return false;
        if (getCategory() != null ? !getCategory().equals(item.getCategory()) : item.getCategory() != null)
            return false;
        if (getUser() != null ? !getUser().equals(item.getUser()) : item.getUser() != null) return false;
        return getCity() != null ? getCity().equals(item.getCity()) : item.getCity() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getContact() != null ? getContact().hashCode() : 0);
        result = 31 * result + (getCreateTime() != null ? getCreateTime().hashCode() : 0);
        result = 31 * result + (getUpdateTime() != null ? getUpdateTime().hashCode() : 0);
        result = 31 * result + (getPicture() != null ? getPicture().hashCode() : 0);
        result = 31 * result + (getCategory() != null ? getCategory().hashCode() : 0);
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        result = 31 * result + (getCity() != null ? getCity().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Item{");
        sb.append("title='").append(title).append('\'');
        sb.append(", price=").append(price);
        sb.append(", description='").append(description).append('\'');
        sb.append(", contact='").append(contact).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", picture='").append(picture).append('\'');
        sb.append(", category=").append(category);
        sb.append(", user=").append(user);
        sb.append(", city=").append(city);
        sb.append('}');
        return sb.toString();
    }
}
