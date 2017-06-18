package network;

import network.sender.Sender;
import org.junit.gen5.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import service.impl.SocketProvider;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Anton Tolkachev.
 * Since 21.01.17
 */

class SocketProviderTest {

    private SocketProvider socketProvider;
    private ExecutorService executorService;
    private Socket s1;
    private Socket s2;

    private Sender sender;

    @BeforeEach
    void setUp() throws IOException {

        sender = Mockito.mock(Sender.class);
        Mockito.doNothing().when(sender).send(Matchers.any(), Matchers.any());

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
                Assertions.assertEquals(s1, actual);
        });
        executorService.execute(() -> {
            socketProvider.setSocket(s2);
            Socket actual = socketProvider.getSocket();
            Assertions.assertEquals(s2, actual);
        });
    }

    @AfterEach
    void tearDown() {
        executorService.shutdown();
    }

}