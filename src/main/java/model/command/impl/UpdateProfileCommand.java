package model.command.impl;

import model.command.AbstractCommand;
import model.command.Argument;
import model.command.Command;
import model.network.impl.Response;
import model.user.Profile;
import org.apache.commons.lang3.StringUtils;
import util.JsonNodes;
import util.RequestStatus;
import util.Utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 25.02.17
 */

public class UpdateProfileCommand extends AbstractCommand<Profile> {
    @Override
    public Response handle() {
        boolean update = service.updateUserProfile(argument.getArgument());
        return Response.newBuilder()
                .setStatus(update ? RequestStatus.OK : RequestStatus.FAIL)
                .build();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Command<Profile> withArguments(Map<?, ?> args) {
        try {
            Map<String, String> map = (Map<String, String>) args.get(JsonNodes.PROFILE);
            Profile profile = Profile.newBuilder()
                    .setGender(map.getOrDefault("GENDER", StringUtils.EMPTY))
                    .setDateOfBirth(Utils.getDate(map.getOrDefault("DOB", StringUtils.EMPTY)))
                    .setEmail(map.getOrDefault("EMAIL", StringUtils.EMPTY))
                    .setCountry(map.getOrDefault("COUNTRY", StringUtils.EMPTY))
                    .setCity(map.getOrDefault("CITY", StringUtils.EMPTY))
                    .setInterests(new HashSet<>(Arrays.asList(map.getOrDefault("INTERESTS", StringUtils.EMPTY)
                            .split(","))))
                    .build();
            setArgument(new Argument<>(profile));
        } catch (ClassCastException e) {
            throw new RuntimeException("Not a map of profile properties passed", e);
        }
        return this;
    }
}
