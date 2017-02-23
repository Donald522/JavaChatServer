package network;

import network.sender.Sender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

/**
 * Created by Anton Tolkachev.
 * Since 21.01.17
 */

class SocketProviderTest {

    private SocketProvider socketProvider;
    private ExecutorService executorService;
    private Socket s1;
    private Socket s2;

    Sender sender;

    @BeforeAll
    void mockSetUp() throws IOException {
        sender = mock(Sender.class);
        doNothing().when(sender).send(any(), any());
    }

    @BeforeEach
    void setUp() {
        socketProvider = new SocketProvider(sender);
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