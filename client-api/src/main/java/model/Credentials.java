package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Created by Anton Tolkachev.
 * Since 10.01.17
 */

public class Credentials {
    private String name;
    private String password;

    public Credentials(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @JsonProperty("USERNAME")
    public String getName() {
        return name;
    }

    @JsonProperty("PASSWORD")
    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credentials that = (Credentials) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Credentials{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
