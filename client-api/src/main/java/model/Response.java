package model;


import org.apache.commons.lang3.StringUtils;

/**
 * Created by Anton Tolkachev.
 * Since 12.02.17
 */

public final class Response {

    private TcpPackets header = TcpPackets.RESPONSE;
    private RequestStatus status = RequestStatus.OK;
    private String message = StringUtils.EMPTY;
    private int reqId;

    public Response() {
    }

    public TcpPackets getHeader() {
        return header;
    }

    public void setHeader(TcpPackets header) {
        this.header = header;
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

    public int getReqId() {
        return reqId;
    }

    public void setReqId(int reqId) {
        this.reqId = reqId;
    }
}
