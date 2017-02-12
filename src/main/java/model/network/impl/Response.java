package model.network.impl;

import model.network.Sendable;
import model.network.TcpPackets;
import org.apache.commons.lang3.StringUtils;
import util.RequestStatus;

/**
 * Created by Anton Tolkachev.
 * Since 12.02.17
 */

public final class Response implements Sendable {

    private final TcpPackets header = TcpPackets.RESPONSE;
    private RequestStatus status = RequestStatus.OK;
    private String message = StringUtils.EMPTY;

    public Response() {
    }

    public static Response.Builder newBuilder() {
        return new Response().new Builder();
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

        public Response.Builder setStatus(RequestStatus status) {
            Response.this.status = status;
            return this;
        }

        public Response.Builder setMessage(String message) {
            Response.this.message = message;
            return this;
        }

        public Response build() {
            return Response.this;
        }
    }

}
