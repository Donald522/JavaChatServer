package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Objects;

/**
 * Created by Anton Tolkachev.
 * Since 07.01.17
 */
public class User {

    private String name;
    private transient String password;

    private Profile profile;

    public User(int id, String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, String password) {
        this(Objects.hash(name), name, password);
    }

    public User(Credentials credentials) {
        this(credentials.getName(), credentials.getPassword());
    }

    @JsonProperty("USERNAME")
    public String getName() {
        return name;
    }

    @JsonProperty("PASSWORD")
    public String getPassword() {
        return password;
    }

    @JsonProperty("PROFILE")
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
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("profile", profile)
                .toString();
    }
}
