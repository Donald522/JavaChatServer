package handler.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.command.AbstractCommand;
import model.command.Argument;
import model.command.impl.*;
import model.dialog.Dialog;
import model.dialog.Message;
import model.network.impl.Response;
import model.user.Credentials;
import model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import service.ClientSessionService;
import util.Factory;
import util.RequestStatus;

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
        Mockito.doReturn(new SendMessageCommand()).when(factory).getObject("sendmsg");

        clientMessageParser = new ClientMessageParserImpl();
    }

    @Test
    void testParseInputWhenPassedSignUpWithCorrectData() throws IOException {
        String json = "{\"COMMAND\":\"signup\", " +
                "\"USERNAME\":\"Anton\", " +
                "\"PASSWORD\":\"pass123\"}";
//        Command<?> actual = clientMessageParser.parseInput(json);
        Credentials credentials = new Credentials("Anton", "pass123");
        AbstractCommand<Credentials> expected = new SignUpCommand();
        expected.setArgument(new Argument<>(credentials));
//        assertEquals(expected, actual, "SignUpCommand should be returned");
    }

    @Test
    void testParseInputWhenPassedUnknownCommand() throws IOException {
        String json = "{\"COMMAND\":\"unknown\", " +
                "\"USERNAME\":\"Anton\", " +
                "\"PASSWORD\":\"pass123\"}";
//        Command<?> actual = clientMessageParser.parseInput(json);
//        assertEquals(DefaultCommand.class, actual.getClass(), "DefaultCommand should be returned");
    }

    @Test
    void testParseArrays() throws IOException {
//        Command<?> actual = clientMessageParser.parseInput("{ \"COMMAND\":\"newdlg\", \"USERS\" : [\"u1\", \"u2\"] }");
        AbstractCommand<Dialog> expected = new CreateDialogCommand();
        Dialog dialog = new Dialog(Arrays.asList(
                new User("u1", "p1"),
                new User("u2", "p2")));
        expected.setArgument(new Argument<>(dialog));
//        assertEquals(expected, actual, "CreateDialog should be returned");
    }

    @Test
    void testSendMessageCommand() throws IOException {
        String json = "{\"COMMAND\":\"sendmsg\", \"MESSAGE\":\"Hello\", \"DIALOG_ID\":\"-724451199\"}";
        AbstractCommand<Message> expected = new SendMessageCommand();
        Message message = Message.newBuilder()
                .setDialogId(-724451199)
                .setMessage("Hello")
                .build();
        expected.setArgument(new Argument<>(message));
//        Command<?> actual = clientMessageParser.parseInput(json);
//        assertEquals(expected, actual, "SendMessage should be returned");
    }

    @Test
    void testResponsePreparationIfMessageExistAndEmpty() throws JsonProcessingException {
        Response response = Response.newBuilder()
                .setStatus(RequestStatus.FAIL)
                .setMessage("")
                .build();
        String expected = "{\"header\":\"RESPONSE\",\"status\":\"FAIL\",\"message\":\"\"}\n";
        String actual = clientMessageParser.prepareResponse(response);
        assertEquals(expected, actual);
    }

    @Test
    void testResponsePreparationIfMessageExist() throws JsonProcessingException {
        Response response = Response.newBuilder()
                .setStatus(RequestStatus.FAIL)
                .setMessage("Some very useful text")
                .build();
        String expected = "{\"header\":\"RESPONSE\",\"status\":\"FAIL\",\"message\":\"Some very useful text\"}\n";
        String actual = clientMessageParser.prepareResponse(response);
        assertEquals(expected, actual);
    }

    @Test
    void testResponsePreparationIfMessageNotExist() throws JsonProcessingException {
        Response response = Response.newBuilder()
                .setStatus(RequestStatus.OK)
                .build();
        String expected = "{\"header\":\"RESPONSE\",\"status\":\"OK\",\"message\":\"\"}\n";
        String actual = clientMessageParser.prepareResponse(response);
        assertEquals(expected, actual);
    }

}