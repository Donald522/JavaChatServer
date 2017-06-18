package model;


import com.google.common.base.Objects;
import network.model.network.Sendable;
import network.model.network.TcpPackets;

import java.time.LocalTime;

import static java.time.LocalTime.now;

/**
 * Created by Anton Tolkachev.
 * Since 12.02.17
 */

public final class DecoratedMessage implements Sendable {

    private final TcpPackets header = TcpPackets.MESSAGE;
    private final String prefix;
    private final String body;

    public DecoratedMessage(Message message) {
        this.body = message.getMessage();
        this.prefix = message.getFromUser().getName() + ' ' +
                LocalTime.of(now().getHour(), now().getMinute());
    }

    public TcpPackets getHeader() {
        return header;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DecoratedMessage that = (DecoratedMessage) o;
        return header == that.header &&
                Objects.equal(prefix, that.prefix) &&
                Objects.equal(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(header, prefix, body);
    }
}
