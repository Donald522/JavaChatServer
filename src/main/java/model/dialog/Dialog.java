package model.dialog;

import com.google.common.base.MoreObjects;
import model.user.User;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Anton Tolkachev.
 * Since 28.01.17
 */

public class Dialog {

    private int id;
    private List<User> users;
    private BlockingQueue<Message> messages;

    public Dialog(List<User> users) {
        this.users = new CopyOnWriteArrayList<>(users);
        messages = new LinkedBlockingQueue<>(100);
        id = hashCode();
    }

    public boolean addUser(User user) {
        return users.add(user);
    }

    public boolean addUsers(List<User> userList) {
        return users.addAll(userList);
    }

    public boolean deleteUser(User user) {
        return users.remove(user);
    }

    public boolean addMessage(Message message) {
        return messages.offer(message);
    }

    public int getId() {
        return id;
    }

    public BlockingQueue<Message> getMessages() {
        return messages;
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dialog dialog = (Dialog) o;
        return Objects.equals(users, dialog.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(users);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("users", users)
                .toString();
    }
}
