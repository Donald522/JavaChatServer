package model.contact;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import model.user.User;

/**
 * Created by Anton Tolkachev.
 * Since 02.04.17
 */

public class Relation {

    private final User first;
    private final User second;
    private final RelationStatus status;

    private Relation(Builder builder) {
        this.first = builder.first;
        this.second = builder.second;
        this.status = builder.status;
    }

    public User getFirst() {
        return first;
    }

    public User getSecond() {
        return second;
    }

    public RelationStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relation relation = (Relation) o;
        return Objects.equal(first, relation.first) &&
                Objects.equal(second, relation.second);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(first, second);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("first", first)
                .add("second", second)
                .add("status", status)
                .toString();
    }

    private static class Builder {
        private User first;
        private User second;
        private RelationStatus status;

        public Builder setFirst(User first) {
            this.first = first;
            return this;
        }

        public Builder setSecond(User second) {
            this.second = second;
            return this;
        }

        public Builder setStatus(RelationStatus status) {
            this.status = status;
            return this;
        }

        private Relation build() {
            return new Relation(this);
        }
    }

}
