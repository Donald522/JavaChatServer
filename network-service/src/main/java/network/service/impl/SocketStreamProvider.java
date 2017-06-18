package network.service.impl;


import network.service.StreamProvider;

import java.io.*;
import java.net.Socket;

/**
 * Created by Anton Tolkachev.
 * Since 29.01.17
 */

public class SocketStreamProvider implements StreamProvider {
    @Override
    public Reader getReader(Closeable from) throws IOException {
        return new BufferedReader(
                new InputStreamReader(
                        new BufferedInputStream(
                                ((Socket) from).getInputStream()
                        ), "UTF-8"
                )
        );
    }

    @Override
    public Writer getWriter(Closeable to) throws IOException {
        return new PrintWriter(
                new OutputStreamWriter(
                        new BufferedOutputStream(
                                ((Socket) to).getOutputStream()
                        ), "UTF-8"
                )
        );
    }

    @Override
    public boolean isBuffered() {
        return true;
    }
}
