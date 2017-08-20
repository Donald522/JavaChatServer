package model;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public static Builder newBuilder() {
        return new Profile().new Builder();
    }

    @JsonProperty("GENDER")
    public String getGender() {
        return gender;
    }

    @JsonProperty("DOB")
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    @JsonProperty("EMAIL")
    public String getEmail() {
        return email;
    }

    @JsonProperty("COUNTRY")
    public String getCountry() {
        return country;
    }

    @JsonProperty("CITY")
    public String getCity() {
        return city;
    }

    @JsonProperty("INTERESTS")
    public Set<String> getInterests() {
        return interests;
    }

    public class Builder {

        private Builder() {}

        public Builder setGender(String gender) {
            Profile.this.gender = gender;
            return this;
        }

        public Builder setDateOfBirth(Date dateOfBirth) {
            Profile.this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder setEmail(String email) {
            Profile.this.email = email;
            return this;
        }

        public Builder setCountry(String country) {
            Profile.this.country = country;
            return this;
        }

        public Builder setCity(String city) {
            Profile.this.city = city;
            return this;
        }

        public Builder setInterests(Set<String> interests) {
            Profile.this.interests = interests;
            return this;
        }

        public Profile build() {
            return Profile.this;
        }
    }

}
