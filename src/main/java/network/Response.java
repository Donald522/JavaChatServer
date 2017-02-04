package network;

import com.google.common.base.MoreObjects;
import util.RequestStatus;

/**
 * Created by Anton Tolkachev.
 * Since 04.02.17
 */

public class Response {

    private RequestStatus status;
    private String message;

    public static Response.Builder newBuilder() {
        return new Response().new Builder();
    }

    public RequestStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("status", status)
                .add("message", message)
                .toString();
    }
}
