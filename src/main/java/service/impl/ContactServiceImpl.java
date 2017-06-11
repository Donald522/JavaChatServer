package service.impl;

import model.contact.Relation;
import model.contact.RelationRequest;
import model.network.impl.Popup;
import model.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ContactRequestSender;
import service.ContactService;
import storage.ClientSessionStorage;
import storage.ContactListStorage;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static service.impl.ContactServiceUtils.getFriendForUser;
import static service.impl.ContactServiceUtils.getInboundRequestsForUser;
import static service.impl.ContactServiceUtils.getOutboundRequestsForUser;
import static service.impl.ContactServiceUtils.getRelationsForUser;
import static service.impl.ContactServiceUtils.validateUser;


/**
 * Created by Anton Tolkachev.
 * Since 01.04.17
 */

public class ContactServiceImpl implements ContactService {

    private static final Logger logger = LogManager.getLogger(ContactServiceImpl.class);

    private final Cache cache;
    private final ContactRequestSender sender;

    public ContactServiceImpl(ClientSessionStorage userStorage, ContactListStorage contactListStorage, ContactRequestSender sender) {
        this.cache = new Cache(userStorage, contactListStorage);
        this.sender = sender;
    }

    @Override
    public boolean request(RelationRequest request) {
        if(request.getFirst() == null || request.getSecond() == null) {
            logger.warn("Both users in contact requests should be not null");
            return false;
        }
        logger.info("User {} request to add User {} in his contact list", request.getFirst(), request.getSecond());
        boolean newRequest = cache.getContactsStorage().newRequest(request);
        if(!newRequest) {
            return false;
        }
        Popup popup = new Popup();
        popup.setMessage("User " + request.getFirst() + " wants to add you in his friendlist");
        sender.send(popup, cache.getUser(request.getSecond()));
        return true;
    }

    @Override
    public boolean approve(RelationRequest request) {
        if(request.getFirst() == null || request.getSecond() == null) {
            logger.warn("Both users in contact requests should be not null");
            return false;
        }
        logger.info("User {} approved contact request from User {}", request.getSecond(), request.getFirst());
        boolean approveRequest = cache.getContactsStorage().approveRequest(request);
        if(!approveRequest) {
            logger.error("Request was not approved");
            return false;
        }
        Popup popup = new Popup();
        popup.setMessage("User " + request.getSecond() + " added you in his friendlist");
        sender.send(popup, cache.getUser(request.getFirst()));
        return true;
    }

    @Override
    public boolean reject(RelationRequest request) {
        if(request.getFirst() == null || request.getSecond() == null) {
            logger.warn("Both users in contact requests should be not null");
            return false;
        }
        logger.info("User {} rejected contact request from User {}", request.getSecond(), request.getFirst());
        boolean rejectRequest = cache.getContactsStorage().rejectRequest(request);
        if(!rejectRequest) {
            logger.error("Request was not rejected");
            return false;
        }
        return true;
    }

    @Override
    public boolean removeContact(Relation relation) {
        if(relation == null) {
            logger.error("Null relation was passed for removing");
            return false;
        }
        if(isBlank(relation.getFirst()) ||
                isBlank(relation.getSecond())) {
            logger.warn("One or both users in relation have invalid usernames. Cannot perform removing");
        }
        logger.info("User {} requested removing User {} from his contact list", relation.getFirst(), relation.getSecond());
        boolean removeContact = cache.getContactsStorage().removeContact(relation);
        if(!removeContact) {
            logger.error("User {} was not removed from {}'s contact list", relation.getFirst(), relation.getSecond());
            return false;
        }
        return true;
    }

    @Override
    public Set<User> getContacts(User user) {
        if (!validateUser(user)) {
            logger.warn("Cannot find contacts for user (invalid username)");
            return Collections.emptySet();
        }
        String userName = user.getName();
        return cache.getContactsStorage().getRelationsList().stream()
                .filter(getRelationsForUser(userName))
                .map(getFriendForUser(userName))
                .map(cache::getUser)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<RelationRequest> getInboundRequests(User user) {
        if (!validateUser(user)) {
            logger.warn("Cannot find inbound requests for user (invalid username)");
            return Collections.emptySet();
        }
        String userName = user.getName();
        return cache.getContactsStorage().getRequestsList().stream()
                .filter(getInboundRequestsForUser(userName))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<RelationRequest> getOutboundRequests(User user) {
        if (!validateUser(user)) {
            logger.warn("Cannot find outbound requests for user (invalid username)");
            return Collections.emptySet();
        }
        String userName = user.getName();
        return cache.getContactsStorage().getRequestsList().stream()
                .filter(getOutboundRequestsForUser(userName))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<User> findContactsByName(String name) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    private static class Cache {

        private final ClientSessionStorage usersStorage;

        private final ContactListStorage contactsStorage;
        Cache(ClientSessionStorage usersStorage, ContactListStorage contactsStorage) {
            this.usersStorage = usersStorage;
            this.contactsStorage = contactsStorage;
        }
        User getUser(String name) {
            return usersStorage.getUser(name);
        }

        ContactListStorage getContactsStorage() {
            return contactsStorage;
        }

    }

}
