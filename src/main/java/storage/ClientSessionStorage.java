package storage;

import dao.core.impl.ClientSessionDao;
import model.user.Profile;
import model.user.User;
import net.jcip.annotations.GuardedBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.RefreshFailedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
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

    private Lock lock = new ReentrantLock();

    @GuardedBy("lock")
    private Map<String, User> usersCache;

    private Map<String, User> onlineUsers;

    public ClientSessionStorage(ClientSessionDao clientSessionDao) throws RefreshFailedException {
        this.clientSessionDao = clientSessionDao;
        usersCache = new HashMap<>();
        onlineUsers = new ConcurrentHashMap<>();
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
        lock.lock();
        clientSessionDao.storeUser(user);
        usersCache.put(user.getName(), user);
        lock.unlock();
    }

    public void updateUserProfile(User user, Profile profile) {
        lock.lock();
        clientSessionDao.storeUserProfile(user, profile);
        user.setProfile(profile);
        lock.unlock();
    }

    public void addActiveUser(User user) {
        User old = onlineUsers.put(Thread.currentThread().getName(), user);
        if(old != null) {
            logger.info("User {} was removed from online users", old);
        }
        logger.info("User {} was added to online users", user);
    }

    public Map<String, User> getOnlineUsers() {
        return onlineUsers;
    }

    public User getCurrentUser() {
        return onlineUsers.get(Thread.currentThread().getName());
    }
}
