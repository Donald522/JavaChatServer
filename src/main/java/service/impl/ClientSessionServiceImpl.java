package service.impl;

import dao.core.ClientSessionDao;
import model.dialog.Dialog;
import model.user.Credentials;
import model.user.Status;
import model.user.User;
import network.SocketProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ClientSessionService;
import service.DialogService;
import storage.ClientSessionStorage;

/**
 * Created by Anton Tolkachev.
 * Since 10.01.17
 */

public class ClientSessionServiceImpl implements ClientSessionService {

    private static final Logger logger = LogManager.getLogger(ClientSessionServiceImpl.class);

    private ClientSessionDao dao;
    private ClientSessionStorage storage;
    private DialogService dialogService;
    private SocketProvider socketProvider;

    public ClientSessionServiceImpl(ClientSessionDao dao,
                                    ClientSessionStorage storage,
                                    DialogService dialogService,
                                    SocketProvider socketProvider) {
        this.dao = dao;
        this.storage = storage;
        this.dialogService = dialogService;
        this.socketProvider = socketProvider;
    }

    @Override
    public User getUserByName(String username) {
        return storage.getUser(username);
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
        if(user.getStatus() == Status.ONLINE) {
            logger.info("User {} is already online", user);
            user.setSocket(socketProvider.getSocket());
            return true;
        }
        user.setStatus(Status.ONLINE);
        logger.info("User {} signed in successfully", user);
        return true;
    }

    @Override
    public void handleDefaultCommand() {
        logger.warn("Default command has been invoked");
    }

    @Override
    public boolean createNewDialog(Dialog dialog) {
        logger.info("Request on create new dialog with users {}", dialog.getUsers());
        boolean response = dialogService.createNewDialog(dialog);
        if(!response) {
            logger.info("Failed to create new dialog #{}. Dialog already exists", dialog.getId());
        }
        logger.info("New dialog #{} has been successfully created", dialog.getId());
        return response;
    }

}
