package network;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Anton Tolkachev.
 * Since 21.01.17
 */

class SocketProviderTest {

    private SocketProvider socketProvider;
    private ExecutorService executorService;
    private Socket s1;
    private Socket s2;

    @BeforeEach
    void setUp() {
        socketProvider = new SocketProvider();
        executorService = Executors.newCachedThreadPool();
        s1 = new Socket();
        s2 = new Socket();
    }

    @Test
    void test() {
        executorService.execute(() -> {
                socketProvider.setSocket(s1);
                Socket actual = socketProvider.getSocket();
                assertEquals(s1, actual);
        });
        executorService.execute(() -> {
            socketProvider.setSocket(s2);
            Socket actual = socketProvider.getSocket();
            assertEquals(s2, actual);
        });
    }

    @AfterEach
    void tearDown() {
        executorService.shutdown();
    }

}