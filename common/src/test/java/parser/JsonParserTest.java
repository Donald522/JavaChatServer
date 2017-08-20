package parser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import handler.ClientMessageParser;
import handler.impl.ClientMessageParserImpl;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by Anton Tolkachev.
 * Since 18.06.17
 */

public class JsonParserTest {

    private ClientMessageParser parser = new ClientMessageParserImpl();

    private class User {
        private String name;
        @JsonIgnore
        private String password;
        private int age;

        public User(String name, String password, int age) {
            this.name = name;
            this.password = password;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public String getPassword() {
            return password;
        }

        public int getAge() {
            return age;
        }
    }

    @Test
    void test() throws IOException {
        User user = new User("anton", "12345", 20);

        String response = parser.prepareResponse(user);

        assertThat(response, equalTo("{\"name\":\"anton\",\"age\":20}\n"));
    }

}
