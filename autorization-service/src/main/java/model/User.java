package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;

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

    @JsonIgnore
    private transient String password;

    @JsonIgnore
    private Status status = Status.OFFLINE;

    @JsonIgnore
    private transient Set<Socket> sockets;

    private Profile profile;

    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.sockets = new HashSet<>();
    }

    public User(String name, String password) {
        this(Objects.hash(name), name, password);
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

    public Set<Socket> getSockets() {
        return sockets;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setSocket(Socket socket) {
        this.sockets.add(socket);
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("status", status)
                .add("profile", profile)
                .toString();
    }
}
