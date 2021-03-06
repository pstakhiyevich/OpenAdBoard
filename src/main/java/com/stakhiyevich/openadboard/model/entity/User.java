package com.stakhiyevich.openadboard.model.entity;

import java.time.LocalDateTime;


public class User extends AbstractEntity {

    private String name;
    private String email;
    private LocalDateTime registrationDate;
    private String avatar;
    private UserStatus status;
    private UserRole role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (getName() != null ? !getName().equals(user.getName()) : user.getName() != null) return false;
        if (getEmail() != null ? !getEmail().equals(user.getEmail()) : user.getEmail() != null) return false;
        if (getRegistrationDate() != null ? !getRegistrationDate().equals(user.getRegistrationDate()) : user.getRegistrationDate() != null)
            return false;
        if (getAvatar() != null ? !getAvatar().equals(user.getAvatar()) : user.getAvatar() != null) return false;
        if (getStatus() != user.getStatus()) return false;
        return getRole() == user.getRole();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getRegistrationDate() != null ? getRegistrationDate().hashCode() : 0);
        result = 31 * result + (getAvatar() != null ? getAvatar().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        result = 31 * result + (getRole() != null ? getRole().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", registrationDate=").append(registrationDate);
        sb.append(", avatar='").append(avatar).append('\'');
        sb.append(", status=").append(status);
        sb.append(", role=").append(role);
        sb.append('}');
        return sb.toString();
    }
}
