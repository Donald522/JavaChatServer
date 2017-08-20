package service.impl;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import model.User;
import network.model.Packet;
import org.junit.jupiter.api.Test;
import util.JsonSerializer;

import java.util.stream.StreamSupport;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Anton Tolkachev.
 * Since 23.07.17
 */

class JsonSerializerTest {

    @Test
    void test() throws JsonProcessingException {
        User user = new User("user", "password");
        Packet packet = new Packet("signup", user);

        String request = JsonSerializer.prepareRequest(packet);

        System.out.println(request);

        assertThat(request, equalTo("{\"COMMAND\":\"signup\",\"ARG\":{\"USERNAME\":\"user\",\"PASSWORD\":\"password\"}}\n"));
    }

    @Test
    void testSerializeList() throws JsonProcessingException {
        class Dialog {

            private Iterable<User> users;

            private Dialog(Iterable<User> users) {
                this.users = users;
            }

            @JsonProperty("USERS")
            public Iterable<String> getUsers() {
                return StreamSupport.stream(users.spliterator(), false).map(User::getName).collect(toList());
            }
        }
        Iterable<User> users = asList(new User("user1", "pass1"), new User("user2", "pass2"));
        Packet packet = new Packet("newdlg", new Dialog(users));

        String request = JsonSerializer.prepareRequest(packet);

        System.out.println(request);
    }

}