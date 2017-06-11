package service.impl;

import model.contact.Relation;
import model.contact.RelationRequest;
import model.user.User;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Anton Tolkachev.
 * Since 10.06.17
 */

public final class ContactServiceUtils {

    public static boolean validateUser(User user) {
        return !(user == null || StringUtils.isBlank(user.getName()));
    }

    public static Predicate<Relation> getRelationsForUser(String userName) {
        return relation -> userName.equals(relation.getFirst()) || userName.equals(relation.getSecond());
    }

    public static Function<Relation, String> getFriendForUser(String userName) {
        return relation -> userName.equals(relation.getFirst()) ? relation.getSecond() : relation.getFirst();
    }

    public static Predicate<RelationRequest> getInboundRequestsForUser(String userName) {
        return request -> userName.equals(request.getSecond());
    }

    public static Predicate<RelationRequest> getOutboundRequestsForUser(String userName) {
        return request -> userName.equals(request.getFirst());
    }

    private ContactServiceUtils() {
        throw new UnsupportedOperationException("Instantiating of utility class is prohibited");
    }
}
