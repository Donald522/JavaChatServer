package model;

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

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
