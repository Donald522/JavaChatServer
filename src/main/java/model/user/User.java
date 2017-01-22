package model.user;

import java.net.Socket;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Anton Tolkachev.
 * Since 07.01.17
 */
public class User {

    private int id;
    private String name;
    private String password;

    private Status status = Status.OFFLINE;

    private Set<Socket> sockets = new HashSet<>();

    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.id = hashCode();
    }

    public User(Credentials credentials) {
        this(credentials.getName(), credentials.getPassword());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setSocket(Socket socket) {
        this.sockets.add(socket);
    }

    public static class Builder {
        private int id;
        private String name;
        private String password;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            return new User(id, name, password);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
