package handler.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.net.Socket;
import java.util.Set;

/**
 * Created by Anton Tolkachev.
 * Since 25.02.17
 */

public abstract class UserMixIn {
    @JsonIgnore abstract String getPassword();
    @JsonIgnore abstract Set<Socket> getSockets();
}
