package model;

import java.sql.Date;
import java.util.Set;

/**
 * Created by Anton Tolkachev.
 * Since 25.02.17
 */

public class Profile {

    private String gender;
    private Date dateOfBirth;
    private String email;
    private String country;
    private String city;
    private Set<String> interests;

    public Profile() {
    }

    public static Profile.Builder newBuilder() {
        return new Profile().new Builder();
    }

    public String getGender() {
        return gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public Set<String> getInterests() {
        return interests;
    }

    public class Builder {

        private Builder() {}

        public Profile.Builder setGender(String gender) {
            Profile.this.gender = gender;
            return this;
        }

        public Profile.Builder setDateOfBirth(Date dateOfBirth) {
            Profile.this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Profile.Builder setEmail(String email) {
            Profile.this.email = email;
            return this;
        }

        public Profile.Builder setCountry(String country) {
            Profile.this.country = country;
            return this;
        }

        public Profile.Builder setCity(String city) {
            Profile.this.city = city;
            return this;
        }

        public Profile.Builder setInterests(Set<String> interests) {
            Profile.this.interests = interests;
            return this;
        }

        public Profile build() {
            return Profile.this;
        }
    }

}
