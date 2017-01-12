package handler.impl;

import model.command.Command;
import model.command.impl.SignUpCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import util.Factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Mockito.doReturn(new SignUpCommand()).when(factory).getObject("signup");

        clientMessageParser = new ClientMessageParserImpl(factory);
    }

    @Test
    void testParseInputWhenPassedSignUpWithCorrectData() {
        String json = "{\"command\":\"signup\", " +
                "\"username\":\"Anton\", " +
                "\"password\":\"pass123\"}";
        Command<?> actual = clientMessageParser.parseInput(json);
        assertEquals(new SignUpCommand(), actual, "SignUpCommand should be returned");
    }

}