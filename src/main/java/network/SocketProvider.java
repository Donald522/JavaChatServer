package network;

import net.jcip.annotations.GuardedBy;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Anton Tolkachev.
 * Since 18.01.17
 */

public class SocketProvider {

    @GuardedBy("lock")
    private Map<Long, Socket> sockets = new HashMap<>();

    private ReentrantLock lock = new ReentrantLock();

    public Socket getSocket() {
        return sockets.remove(Thread.currentThread().getId());
    }

    public void setSocket(Socket socket) {
        lock.lock();
        sockets.put(Thread.currentThread().getId(), socket);
        lock.unlock();
    }
}
