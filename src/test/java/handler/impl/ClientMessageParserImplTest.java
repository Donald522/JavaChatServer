package handler.impl;

import model.command.AbstractCommand;
import model.command.Argument;
import model.command.Command;
import model.command.impl.CreateDialogCommand;
import model.command.impl.DefaultCommand;
import model.command.impl.SignInCommand;
import model.command.impl.SignUpCommand;
import model.dialog.Dialog;
import model.user.Credentials;
import model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import service.ClientSessionService;
import util.Factory;

import java.io.IOException;
import java.util.Arrays;

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
    private ClientSessionService service;

    @BeforeEach
    void setUp() {

        service = mock(ClientSessionService.class);
        Mockito.doReturn(new User("u1", "p1")).when(service).getUserByName("u1");
        Mockito.doReturn(new User("u2", "p2")).when(service).getUserByName("u2");

        factory = (Factory<?>) mock(Factory.class);
        Mockito.doReturn(new DefaultCommand()).when(factory).getObject(any());
        Mockito.doReturn(new SignUpCommand()).when(factory).getObject("signup");
        Mockito.doReturn(new SignInCommand()).when(factory).getObject("signin");
        Mockito.doReturn(new CreateDialogCommand().withService(service)).when(factory).getObject("newdlg");

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

    @Test
    void testParseArrays() throws IOException {
        Command<?> actual = clientMessageParser.parseInput("{ \"COMMAND\":\"newdlg\", \"USERS\" : [\"u1\", \"u2\"] }");
        AbstractCommand<Dialog> expected = new CreateDialogCommand();
        Dialog dialog = new Dialog(Arrays.asList(
                new User("u1", "p1"),
                new User("u2", "p2")));
        expected.setArgument(new Argument<>(dialog));
        assertEquals(expected, actual, "CreateDialog should be returned");
    }

}