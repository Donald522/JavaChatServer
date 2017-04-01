package model.contact;

import model.user.User;

/**
 * Created by Anton Tolkachev.
 * Since 10.01.17
 */
public class Request {

    private final User from;
    private final User to;
    private final String message;

    private Request(Builder builder) {
        this.from = builder.from;
        this.to = builder.to;
        this.message = builder.message;
    }

    public User getFrom() {
        return from;
    }

    public User getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    private static class Builder {
        private User from;
        private User to;
        private String message;

        public Builder setFrom(User from) {
            this.from = from;
            return this;
        }

        public Builder setTo(User to) {
            this.to = to;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }

}
