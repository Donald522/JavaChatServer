package storage;

import dao.core.impl.ContactListDao;
import model.contact.Relation;
import model.contact.RelationRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.RefreshFailedException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Anton Tolkachev.
 * Since 20.05.17
 */

public class ContactListStorage implements Refreshable {

    private static final Logger logger = LogManager.getLogger(ContactListStorage.class);

    private ContactListDao dao;

    private List<Relation> relationsList;
    private List<RelationRequest> requestsList;

    private ExecutorService executor = Executors.newFixedThreadPool(2);

    public ContactListStorage(ContactListDao dao) {
        this.dao = dao;
    }

    public boolean newRequest(RelationRequest request) {
        logger.info("Storing new request...");
        boolean add = requestsList.add(request);
        if(!add) {
            logger.error("Error during storing new request to cache");
            return false;
        }
        boolean storeRequest = dao.storeRequest(request);
        if(!storeRequest) {
            logger.error("Error during storing new request to database");
            requestsList.remove(request);
            return false;
        }
        return true;
    }

    public boolean approveRequest(RelationRequest request) {
        logger.info("Removing approved request from cache...");
        boolean remove = requestsList.remove(request);
        if(!remove) {
            logger.error("Error when removing request from cache");
            return false;
        }
        Relation relation = new Relation.Builder()
                .setFirst(request.getFirst())
                .setSecond(request.getSecond()).build();
        boolean add = relationsList.add(relation);
        if(!add) {
            logger.error("Error during storing new relation to cache");
            return false;
        }
        boolean approveRequest = dao.approveRequest(request);
        if(!approveRequest) {
            logger.error("Error during storing new relation to database");
            relationsList.remove(relation);
            requestsList.add(request);
            return false;
        }
        return true;
    }

    public boolean rejectRequest(RelationRequest request) {
        logger.info("Removing rejected request from cache...");
        boolean remove = requestsList.remove(request);
        if(!remove) {
            logger.error("Error when removing request from cache");
            return false;
        }
        boolean rejectRequest = dao.rejectRequest(request);
        if(!rejectRequest) {
            logger.error("Error during updating rejected request in database");
            requestsList.add(request);
            return false;
        }
        return true;
    }

    public boolean removeContact(Relation relation) {
        logger.info("Removing relation {} from cache...", relation);
        Relation resolvedRelation = null;
        boolean contains = relationsList.contains(relation);
        if(contains) {
            resolvedRelation = relation;
        } else {
            Relation revertedRelation = new Relation.Builder()
                    .setFirst(relation.getSecond())
                    .setSecond(relation.getFirst())
                    .build();
            boolean containsReverted = relationsList.contains(revertedRelation);
            if(containsReverted) {
                resolvedRelation = relation;
            }
        }
        if(resolvedRelation != null) {
            boolean remove = relationsList.remove(resolvedRelation);
            if(!remove) {
                logger.error("Error in removing contact from cache");
                return false;
            }
            boolean removeContact = dao.removeContact(relation);
            if(!removeContact) {
                logger.error("Error in removing contact from database");
                relationsList.add(resolvedRelation);
                return false;
            }
        } else {
            logger.warn("Cannot find relation between User {} and User {}", relation.getFirst(), relation.getSecond());
            return false;
        }
        return true;
    }

    @Override
    public void refresh() throws RefreshFailedException {
        try {
            Future<List<Relation>> relationsFuture = executor.submit(() -> {
                logger.info("[START] Relations loading has been started");
                Collection<Relation> relations = dao.loadAllFriends();
                logger.info("[END] Relations loading has been ended");
                return new CopyOnWriteArrayList<>(relations);
            });

            Future<List<RelationRequest>> requestsFuture = executor.submit(() -> {
                logger.info("[START] Relations requests loading has been started");
                Collection<RelationRequest> relationRequests = dao.loadAllRequests();
                logger.info("[END] Relations requests loading has been ended");
                return new CopyOnWriteArrayList<>(relationRequests);
            });

            relationsList = relationsFuture.get();
            requestsList = requestsFuture.get();
            logger.info("Contact list storage has been built successfully");

        } catch (Exception ex) {
            throw new RefreshFailedException("Contact list storage refresh failed");
        }
    }

    public List<Relation> getRelationsList() {
        return relationsList;
    }

    public List<RelationRequest> getRequestsList() {
        return requestsList;
    }
}
