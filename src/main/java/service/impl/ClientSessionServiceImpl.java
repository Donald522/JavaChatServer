package service.impl;

import dao.core.ClientSessionDao;
import model.Credentials;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ClientSessionService;
import storage.ClientSessionStorage;

/**
 * Created by Anton Tolkachev.
 * Since 10.01.17
 */

public class ClientSessionServiceImpl implements ClientSessionService {

    private static final Logger logger = LogManager.getLogger(ClientSessionServiceImpl.class);

    private ClientSessionDao dao;
    private ClientSessionStorage storage;

    public ClientSessionServiceImpl(ClientSessionDao dao, ClientSessionStorage storage) {
        this.dao = dao;
        this.storage = storage;
    }

    @Override
    public void signUpUser(Credentials credentials) {
        logger.info("Request on register new user {}", credentials);
        if(storage.getUser(credentials.getName()) != null) {
            logger.info("Attempt to sign up with already used username");
            throw new IllegalArgumentException("Name is already used");
        }
        User user = new User(credentials);
        storage.storeUser(user);
        logger.info("New user was successfully registered");
    }
}
