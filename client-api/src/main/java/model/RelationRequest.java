package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Created by Anton Tolkachev.
 * Since 01.04.17
 */

public class RelationRequest {

    private final String first;
    private final String second;
    private final String message;

    private RelationRequest(Builder builder) {
        this.first = builder.first;
        this.second = builder.second;
        this.message = builder.message;
    }

    @JsonProperty("FIRST")
    public String getFirst() {
        return first;
    }

    @JsonProperty("SECOND")
    public String getSecond() {
        return second;
    }

    @JsonProperty("MESSAGE")
    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relation relation = (Relation) o;
        return Objects.equal(first, first) &&
                Objects.equal(second, second);
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
                .add("message", message)
                .toString();
    }

    public static class Builder {
        private String first;
        private String second;
        private String message;

        public Builder setFirst(String first) {
            this.first = first;
            return this;
        }

        public Builder setSecond(String second) {
            this.second = second;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public RelationRequest build() {
            return new RelationRequest(this);
        }
    }
}
