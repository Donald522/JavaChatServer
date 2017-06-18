package model;

import com.google.common.base.MoreObjects;

import java.util.List;
import java.util.Objects;

/**
 * Created by Anton Tolkachev.
 * Since 28.01.17
 */

public class Message {

    private int id;
    private String message;
    private int dialogId;
    private User fromUser;
    private User toUser;
    private List<User> toUsers;

    public static Builder newBuilder() {
        return new Message().new Builder();
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public int getDialogId() {
        return dialogId;
    }

    public User getFromUser() {
        return fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public List<User> getToUsers() {
        return toUsers;
    }

    public class Builder {

        private Builder() {}

        public Builder setId(int id) {
            Message.this.id = id;
            return this;
        }

        public Builder setMessage(String message) {
            Message.this.message = message;
            return this;
        }

        public Builder setDialogId(int dialogId) {
            Message.this.dialogId = dialogId;
            return this;
        }

        public Builder setFromUser(User fromUser) {
            Message.this.fromUser = fromUser;
            return this;
        }

        public Builder setToUser(User toUser) {
            Message.this.toUser = toUser;
            return this;
        }

        public Builder setToUsers(List<User> toUsers) {
            Message.this.toUsers = toUsers;
            return this;
        }

        public Message build() {
            return Message.this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return dialogId == message1.dialogId &&
                Objects.equals(message, message1.message) &&
                Objects.equals(fromUser, message1.fromUser) &&
                Objects.equals(toUser, message1.toUser) &&
                Objects.equals(toUsers, message1.toUsers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, dialogId, fromUser, toUser, toUsers);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("message", message)
                .add("dialogId", dialogId)
                .add("fromUser", fromUser)
                .add("toUser", toUser)
                .add("toUsers", toUsers)
                .toString();
    }
}
