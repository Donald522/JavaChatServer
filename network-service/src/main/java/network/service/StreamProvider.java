package network.service;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * Created by Anton Tolkachev.
 * Since 29.01.17
 */

public interface StreamProvider {

    Reader getReader(Closeable from) throws IOException;

    Writer getWriter(Closeable to) throws IOException;

    boolean isBuffered();

}
