package handler.impl;

import model.command.AbstractCommand;
import model.command.Argument;
import model.command.Command;
import model.command.impl.DefaultCommand;
import model.command.impl.SignUpCommand;
import model.user.Credentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import util.Factory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

/**
 * Created by Anton Tolkachev.
 * Since 12.01.17
 */

class ClientMessageParserImplTest {


    private ClientMessageParserImpl clientMessageParser;
    private Factory<?> factory;

    @BeforeEach
    void setUp() {
        factory = (Factory<?>) mock(Factory.class);
        Mockito.doReturn(new DefaultCommand()).when(factory).getObject(any());
        Mockito.doReturn(new SignUpCommand()).when(factory).getObject("signup");

        clientMessageParser = new ClientMessageParserImpl(factory);
    }

    @Test
    void testParseInputWhenPassedSignUpWithCorrectData() throws IOException {
        String json = "{\"command\":\"signup\", " +
                "\"username\":\"Anton\", " +
                "\"password\":\"pass123\"}";
        Command<?> actual = clientMessageParser.parseInput(json);
        Credentials credentials = new Credentials("Anton", "pass123");
        AbstractCommand<Credentials> expected = new SignUpCommand();
        expected.setArgument(new Argument<>(credentials));
        assertEquals(expected, actual, "SignUpCommand should be returned");
    }

    @Test
    void testParseInputWhenPassedUnknownCommand() throws IOException {
        String json = "{\"command\":\"unknown\", " +
                "\"username\":\"Anton\", " +
                "\"password\":\"pass123\"}";
        Command<?> actual = clientMessageParser.parseInput(json);
        assertEquals(DefaultCommand.class, actual.getClass(), "DefaultCommand should be returned");
    }

}