package dao.core.impl;

import dao.core.Dao;
import model.contact.Relation;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Anton Tolkachev.
 * Since 03.04.17
 */

public class ContactListDao implements Dao<Relation> {
    @Override
    public Collection<Relation> load(Map<String, Object> params) {
        return null;
    }

    @Override
    public Collection<Relation> loadAll() {
        return null;
    }

    @Override
    public int store(Relation data) {
        return 0;
    }

    @Override
    public int storeAll(Collection<Relation> data) {
        return 0;
    }

    @Override
    public int update(Relation data) {
        return 0;
    }

    @Override
    public int updateAll(Collection<Relation> data) {
        return 0;
    }
}
