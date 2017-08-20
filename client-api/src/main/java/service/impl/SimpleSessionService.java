package service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Maps;
import model.Credentials;
import model.Message;
import model.Profile;
import model.Relation;
import model.RelationRequest;
import model.Response;
import model.User;
import network.model.Packet;
import network.model.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.SessionService;
import util.JsonSerializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

/**
 * Created by Anton Tolkachev.
 * Since 18.06.17
 */

public class SimpleSessionService implements SessionService {

    private static final Logger logger = LogManager.getLogger(SimpleSessionService.class);

    private PrintWriter writer;
    private Listener listener;

    public SimpleSessionService(Session session) throws IOException {
        this.writer = new PrintWriter(
                new OutputStreamWriter(
                        new BufferedOutputStream(session.getSocket().getOutputStream()), "UTF-8"
                )
        );
        this.listener = new Listener(session.getSocket().getInputStream());
    }

    @Override
    public boolean signUpUser(Credentials credentials) {
        return sendCommand("signup", credentials);
    }

    @Override
    public boolean signInUser(Credentials credentials) {
        return sendCommand("signin", credentials);
    }

    @Override
    public boolean updateUserProfile(Profile profile) {
        return sendCommand("updpfl", profile);
    }

    @Override
    public boolean signOut() {
        return sendCommand("signout", null);
    }

    @Override
    public boolean createNewDialog(Iterable<User> users) {
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
        return sendCommand("newdlg", new Dialog(users));
    }

    @Override
    public boolean sendMessage(Message message) throws InterruptedException {
        return sendCommand("sendmsg", message);
    }

    @Override
    public boolean contactRequest(RelationRequest request) {
        return sendCommand("frdrq", request);
    }

    @Override
    public Set<User> getContactsForUser(User user) {
        sendCommand("getfrnds", user);
        //TODO: wait for response from server. Need to refactor Listener implementation.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Set<RelationRequest> getInboundRequests(User user) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Set<RelationRequest> getOutboundRequests(User user) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean approveRequest(RelationRequest request) {
        return false;
    }

    @Override
    public boolean rejectRequest(RelationRequest request) {
        return false;
    }

    @Override
    public boolean removeContact(Relation relation) {
        return false;
    }

    @Override
    public Listener getListener() {
        return listener;
    }

    private boolean sendCommand(String command, Object args) {
        logger.debug("Preparing tcp packet for /{} command", command);
        Packet packet = new Packet(command, args);
        try {
            String request = JsonSerializer.prepareRequest(packet);
            writer.write(request);
        } catch (JsonProcessingException e) {
            logger.error("Error in serializing object. Cause: {}", e.getMessage());
            return false;
        }
        logger.debug("/{} command was sent to the server successfully", command);
        return true;
    }

    public static class Listener {

        private BufferedReader reader;

        private Map<Integer, Future<Response>> responseMap;

        Listener(InputStream stream) throws IOException {
            this.reader = new BufferedReader(
                    new InputStreamReader(
                            new BufferedInputStream(stream), "UTF-8"
                    )
            );
            this.responseMap = Maps.newConcurrentMap();
        }

        public String receive() throws IOException, InterruptedException {
            String line = reader.readLine();
            if (line != null && line.contains("RESPONSE")) {
                Response response = JsonSerializer.prepareResponse(line);
                int key = response.getReqId();
                if (!responseMap.containsKey(key)) {
                    responseMap.put(key, CompletableFuture.completedFuture(response));
                } else {
                    CompletableFuture<Response> future = (CompletableFuture<Response>) responseMap.get(key);
                    future.complete(response);
                }
            }
            return line;
        }

        private Future<Response> getResponse(int requestId) throws InterruptedException {
            if (responseMap.containsKey(requestId)) {
                return responseMap.get(requestId);
            } else {
                Future<Response> future = new CompletableFuture<>();
                responseMap.put(requestId, future);
                return future;
            }
        }
    }
}
