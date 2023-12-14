package edu.upc.dsa;

import edu.upc.dsa.exceptions.NoRecordsFoundException;
import edu.upc.dsa.models.Avatar;
import edu.upc.dsa.models.Jugador;
import edu.upc.dsa.models.Tienda;
import edu.upc.dsa.util.ObjectHelper;
import edu.upc.dsa.util.QueryHelper;
import javassist.NotFoundException;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class SessionImpl implements Session {
    private final Connection conn;

    public SessionImpl(Connection conn) {
        this.conn = conn;
    }

    public void save(Object entity) throws SQLIntegrityConstraintViolationException {

        String insertQuery = QueryHelper.createQueryINSERT(entity);

        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(insertQuery);
            int i = 1;

            for (String field: ObjectHelper.getFields(entity)) {
                pstm.setObject(i++, ObjectHelper.getter(entity, field));
            }

            pstm.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            // Handle constraint violation exception (e.g., unique constraint violation)
            throw new SQLIntegrityConstraintViolationException();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void close() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Object get(Class theClass, String columna, String value) throws SQLException {

        String selectQuery  = QueryHelper.createQuerySELECT(theClass, columna, value);
        ResultSet rs;
        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(selectQuery);
            pstm.setObject(1, value); //son los ?
            rs = pstm.executeQuery();
            Object o = theClass.newInstance();

            if (!rs.next()) {
                // No records found
                o = null;
            } else{
                ResultSetMetaData rsmd = rs.getMetaData();
                int numberOfColumns = rsmd.getColumnCount();

                do {
                    for (int i = 1; i <= numberOfColumns; i++) {
                        String columnName = rsmd.getColumnName(i);
                        ObjectHelper.setter(o, columnName, rs.getObject(i));
                    }
                } while (rs.next());
            }

            return o;

        } catch (SQLException e) {
            throw new SQLException();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getAvatar(String player, String nombre) throws SQLException {

        String selectQuery  = QueryHelper.createQuerySELECTAvatar(player, nombre);
        ResultSet rs;
        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(selectQuery);
            pstm.setObject(1, player);
            pstm.setObject(2, nombre); //son los ?
            rs = pstm.executeQuery();
            Object o = Avatar.class.newInstance();

            if (!rs.next()) {
                // No records found
                o = null;
            } else{
                ResultSetMetaData rsmd = rs.getMetaData();
                int numberOfColumns = rsmd.getColumnCount();

                do {
                    for (int i = 1; i <= numberOfColumns; i++) {
                        String columnName = rsmd.getColumnName(i);
                        ObjectHelper.setter(o, columnName, rs.getObject(i));
                    }
                } while (rs.next());
            }

            return o;

        } catch (SQLException e) {
            throw new SQLException();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    public void update(String columna, String user, String value) throws SQLIntegrityConstraintViolationException {
        String updateQuery = QueryHelper.createQueryUPDATEJugador(columna, user, value);

        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(updateQuery);
            pstm.setObject(1, value);
            pstm.setObject(2, user);

            pstm.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            // Handle constraint violation exception (e.g., unique constraint violation)
            throw new SQLIntegrityConstraintViolationException();
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAvatar(String columna, String user, String avatar, String value) throws SQLIntegrityConstraintViolationException {
        String updateQuery = QueryHelper.createQueryUPDATEAvatar(columna, user, avatar, value);

        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(updateQuery);
            pstm.setObject(1, value);
            pstm.setObject(2, avatar);
            pstm.setObject(3, user);

            pstm.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            // Handle constraint violation exception (e.g., unique constraint violation)
            throw new SQLIntegrityConstraintViolationException();
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void delete(Class theClass, String columna, String username) throws NoRecordsFoundException {
        String deleteQuery = QueryHelper.createQueryDELETE(theClass, columna, username);

        try {
            PreparedStatement pstm = conn.prepareStatement(deleteQuery);
            pstm.setObject(1, username);

            int rowsAffected = pstm.executeUpdate();

            if (rowsAffected == 0) {
                throw new NoRecordsFoundException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object> findAll(Class theClass) {

        String query = QueryHelper.createQuerySELECTall(theClass);
        PreparedStatement pstm =null;
        ResultSet rs;
        List<Object> list = new LinkedList<>();
        try {
            pstm = conn.prepareStatement(query);
            pstm.executeQuery();
            rs = pstm.getResultSet();

            ResultSetMetaData metadata = rs.getMetaData();
            int numberOfColumns = metadata.getColumnCount();

            while (rs.next()){
                Object o = theClass.newInstance();
                for (int j=1; j<=numberOfColumns; j++){
                    String columnName = metadata.getColumnName(j);
                    ObjectHelper.setter(o, columnName, rs.getObject(j));
                }
                list.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object> findAllPartidas(Class theClass, String player) {

        String query = QueryHelper.createQuerySELECTallPartidas(theClass, player);
        PreparedStatement pstm =null;
        ResultSet rs;
        List<Object> list = new LinkedList<>();
        try {
            pstm = conn.prepareStatement(query);
            pstm.setObject(1, player);
            pstm.executeQuery();
            rs = pstm.getResultSet();

            ResultSetMetaData metadata = rs.getMetaData();
            int numberOfColumns = metadata.getColumnCount();

            while (rs.next()){
                Object o = theClass.newInstance();
                for (int j=1; j<=numberOfColumns; j++){
                    String columnName = metadata.getColumnName(j);
                    ObjectHelper.setter(o, columnName, rs.getObject(j));
                }
                list.add(o);

            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int size(Class theClass){
        String query = QueryHelper.createQuerySELECTall(theClass);
        PreparedStatement pstm =null;
        ResultSet rs;
        int numberOfRows = 0;
        try {
            pstm = conn.prepareStatement(query);
            rs = pstm.executeQuery();

            // Itera sobre el ResultSet para contar las filas
            while (rs.next()) {
                numberOfRows++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberOfRows;
    }
}
