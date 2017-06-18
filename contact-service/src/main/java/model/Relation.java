package model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Created by Anton Tolkachev.
 * Since 02.04.17
 */

public final class Relation {

    private final String first;
    private final String second;

    private Relation(Builder builder) {
        this.first = builder.first;
        this.second = builder.second;
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
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
                .toString();
    }

    public static class Builder {
        private String first;
        private String second;

        public Builder setFirst(String first) {
            this.first = first;
            return this;
        }

        public Builder setSecond(String second) {
            this.second = second;
            return this;
        }

        public Relation build() {
            return new Relation(this);
        }
    }

}
