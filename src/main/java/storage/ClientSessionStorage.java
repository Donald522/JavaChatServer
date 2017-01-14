package storage;

import dao.core.ClientSessionDao;
import model.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.RefreshFailedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Anton Tolkachev.
 * Since 08.01.17
 */

public class ClientSessionStorage implements Refreshable {

    private static final Logger logger = LogManager.getLogger(ClientSessionStorage.class);

    private ClientSessionDao clientSessionDao;
    private ExecutorService executor = Executors.newFixedThreadPool(1);

    private Map<String, User> usersCache;

    public ClientSessionStorage(ClientSessionDao clientSessionDao) throws RefreshFailedException {
        this.clientSessionDao = clientSessionDao;
        usersCache = new HashMap<>();
        refresh();
    }

    @Override
    public void refresh() throws RefreshFailedException {
        int maxTries = 10;
        for (int retries = 0;; retries++)  {
            try {
                Future<Map<String , User>> future = executor.submit(new Callable<Map<String, User>>() {
                    @Override
                    public Map<String, User> call() throws Exception {
                        logger.info("[START] Users loading has been started");
                        List<User> users = clientSessionDao.loadAllUsers();
                        logger.info("[END] Users loading has been ended");
                        return users.stream().collect(
                                Collectors.toMap(User::getName, Function.identity())
                        );
                    }
                });

                usersCache = future.get();
                break;
            } catch (InterruptedException | ExecutionException e) {
                if(retries < maxTries) {
                    logger.warn("Cannot refresh users cache. Will try again.");
                } else {
                    logger.error("Cannot refresh users cache after 10 attempts.");
                    throw new RefreshFailedException("Cannot refresh users cache after 10 attempts.");
                }
            }
        }
    }

    public User getUser(String userName) {
        return usersCache.get(userName);
    }

    public void storeUser(User user) {
        clientSessionDao.storeUser(user);
        usersCache.put(user.getName(), user);
    }
}
