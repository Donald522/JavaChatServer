package service.impl;

import model.contact.Relation;
import model.contact.RelationRequest;
import model.dialog.Dialog;
import model.dialog.Message;
import model.user.Credentials;
import model.user.Profile;
import model.user.Status;
import model.user.User;
import network.SocketProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ClientSessionService;
import service.ContactService;
import service.DialogService;
import storage.ClientSessionStorage;

import java.util.Set;

/**
 * Created by Anton Tolkachev.
 * Since 10.01.17
 */

public class ClientSessionServiceImpl implements ClientSessionService {

    private static final Logger logger = LogManager.getLogger(ClientSessionServiceImpl.class);

    private final ClientSessionStorage storage;
    private final DialogService dialogService;
    private final ContactService contactService;
    private final SocketProvider socketProvider;

    public ClientSessionServiceImpl(ClientSessionStorage storage,
                                    DialogService dialogService,
                                    ContactService contactService,
                                    SocketProvider socketProvider) {
        this.storage = storage;
        this.dialogService = dialogService;
        this.contactService = contactService;
        this.socketProvider = socketProvider;
    }

    @Override
    public User getUserByName(String username) {
        return storage.getUser(username);
    }

    @Override
    public User getCurrentUser() {
        return storage.getCurrentUser();
    }

    @Override
    public boolean signUpUser(Credentials credentials) {
        logger.info("Request on register new user {}", credentials);
        if(storage.getUser(credentials.getName()) != null) {
            logger.info("Attempt to sign up with already used username {}", credentials.getName());
            return false;
        }
        User user = new User(credentials);
        user.setSocket(socketProvider.getSocket());
        storage.storeUser(user);
        logger.info("New user {} was successfully registered", credentials);
        return true;
    }

    @Override
    public boolean signInUser(Credentials credentials) {
        logger.info("Request on sign in user {}", credentials);
        User user = storage.getUser(credentials.getName());
        if(user == null) {
            logger.info("Attempt to sign in with unknown username {}", credentials.getName());
            return false;
        }
        if(!user.getPassword().equals(credentials.getPassword())) {
            logger.info("Attempt to sign in with wrong password {}", credentials);
            return false;
        }
        if (!socketProvider.contains()) {
            logger.error("Can't map user on socket");
            return false;
        }
        user.setSocket(socketProvider.getSocket());
        if(user.getStatus() == Status.ONLINE) {
            logger.info("User {} is already online", user);
            return true;
        }
        user.setStatus(Status.ONLINE);
        Thread.currentThread().setName(user.getName());
        storage.addActiveUser(user);
        logger.info("User {} signed in successfully", user);
        return true;
    }

    @Override
    public boolean updateUserProfile(Profile profile) {
        User currentUser = getCurrentUser();
        if(currentUser == null) {
            logger.warn("Attempt to update profile without signing in");
            return false;
        }
        logger.info("Request from user {} on updating profile. New profile: {}", currentUser, profile);
        storage.updateUserProfile(currentUser, profile);
        logger.info("Profile for user {} has been updated", currentUser);
        return true;
    }

    @Override
    public boolean signOut() {
        User currentUser = getCurrentUser();
        logger.info("Request on sign out user {}", currentUser);
        if(currentUser.getStatus() == Status.OFFLINE) {
            logger.warn("User {} is already offline", currentUser);
            return false;
        }
        currentUser.setStatus(Status.OFFLINE);
        User remove = storage.getOnlineUsers().remove(currentUser.getName());
        if (remove != null) {
            logger.info("User {} signed out successfully", currentUser);
            return true;
        }
        return false;
    }

    @Override
    public void handleDefaultCommand() {
        logger.warn("Default command has been invoked");
    }

    @Override
    public boolean createNewDialog(Dialog dialog) {
        logger.info("Request on create new dialog with users {}", dialog.getUsers());
        if(dialog.getLeader() == null) {
            logger.warn("Attempt to create dialog from unknown user");
            return false;
        }
        boolean response = dialogService.createNewDialog(dialog);
        if(!response) {
            logger.info("Failed to create new dialog #{}. Dialog already exists", dialog.getId());
        }
        logger.info("New dialog #{} has been successfully created", dialog.getId());
        return response;
    }

    @Override
    public boolean sendMessage(Message message) {
        User fromUser = message.getFromUser();
        if(fromUser == null) {
            logger.warn("Attempt to send message from unknown user");
            return false;
        }
        Dialog dialog = dialogService.getDialogById(message.getDialogId());
        if(!dialog.getUsers().contains(fromUser)) {
            logger.warn("User {} send message to dialog where he doesn't exist.", fromUser);
            return false;
        }
        return dialogService.sendMessage(message);
    }

    @Override
    public boolean contactRequest(RelationRequest request) {
        return contactService.request(request);
    }

    @Override
    public Set<User> getContactsForUser(User user) {
        return contactService.getContacts(user);
    }

    @Override
    public Set<RelationRequest> getInboundRequests(User user) {
        return contactService.getInboundRequests(user);
    }

    @Override
    public Set<RelationRequest> getOutboundRequests(User user) {
        return contactService.getOutboundRequests(user);
    }

    @Override
    public boolean approveRequest(RelationRequest request) {
        return contactService.approve(request);
    }

    @Override
    public boolean rejectRequest(RelationRequest request) {
        return contactService.reject(request);
    }

    @Override
    public boolean removeContact(Relation relation) {
        return contactService.removeContact(relation);
    }


}
