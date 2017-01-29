package model.dialog;

import model.user.User;

import java.util.List;

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
}
