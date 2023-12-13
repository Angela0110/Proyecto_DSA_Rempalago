package edu.upc.dsa;

import edu.upc.dsa.exceptions.NoRecordsFoundException;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface Session<E> {
    void save(Object entity) throws SQLIntegrityConstraintViolationException;
    void close();
    Object get(Class theClass, String columna, String value) throws SQLException;
    public Object getAvatar(String player, String nombre) throws SQLException;

    void update(String columna, String user, String value) throws SQLIntegrityConstraintViolationException;
    public void updateAvatar(String columna, String user, String avatar, String value) throws SQLIntegrityConstraintViolationException;
    void delete(Class theClass, String columna, String username) throws NoRecordsFoundException;
    List<Object> findAll(Class theClass);
    public List<Object> findAllPartidas(Class theClass, String player);
    public int size(Class theClass);

    }
