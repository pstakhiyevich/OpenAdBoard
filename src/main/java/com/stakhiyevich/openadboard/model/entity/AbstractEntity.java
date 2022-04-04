package com.stakhiyevich.openadboard.model.entity;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable, Cloneable {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntity)) return false;
        AbstractEntity that = (AbstractEntity) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AbstractEntity{");
        sb.append("id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
