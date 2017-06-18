package network.model.network.impl;

import network.model.network.RequestStatus;
import network.model.network.Sendable;
import network.model.network.TcpPackets;
import org.apache.commons.lang3.StringUtils;


/**
 * Created by Anton Tolkachev.
 * Since 23.02.17
 */

public class Popup implements Sendable {

    private final TcpPackets header = TcpPackets.POPUP;
    private RequestStatus status = RequestStatus.OK;
    private String message = StringUtils.EMPTY;

    public Popup() {
    }

    public static Popup.Builder newBuilder() {
        return new Popup().new Builder();
    }

    public TcpPackets getHeader() {
        return header;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Builder {

        private Builder() {}

        public Popup.Builder setStatus(RequestStatus status) {
            Popup.this.status = status;
            return this;
        }

        public Popup.Builder setMessage(String message) {
            Popup.this.message = message;
            return this;
        }

        public Popup build() {
            return Popup.this;
        }
    }

}
