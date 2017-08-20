package network.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

/**
 * Created by Anton Tolkachev.
 * Since 23.07.17
 */

public class Packet {

    private String command;
    private Object argument;
    private int reqId;

    public Packet(String command, Object argument) {
        this.command = command;
        this.argument = argument;
        this.reqId = hashCode();
    }

    @JsonProperty("COMMAND")
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @JsonProperty("ARG")
    public Object getArgument() {
        return argument;
    }

    public void setArgument(Object argument) {
        this.argument = argument;
    }

    public int getReqId() {
        return reqId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Packet packet = (Packet) o;
        return Objects.equal(command, packet.command) &&
                Objects.equal(argument, packet.argument);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(command, argument);
    }
}
