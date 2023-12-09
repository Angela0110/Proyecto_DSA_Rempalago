package edu.upc.dsa.util;

public class QueryHelper {

    public static String createQueryINSERT(Object entity) {

        StringBuffer sb = new StringBuffer("INSERT INTO ");
        sb.append(entity.getClass().getSimpleName()).append(" ");
        sb.append("(");

        String [] fields = ObjectHelper.getFields(entity);

    /*   sb.append("ID");

        for (String field: fields) {
            sb.append(", ").append(field);
        }
        */
        for (int i = 0; i < fields.length; i++) {
            sb.append(fields[i]);

            // Agrega una coma si no es el último campo
            if (i < fields.length - 1) {
                sb.append(", ");
            }
        }

        sb.append(") VALUES (");

        /*for (String field: fields) {
            sb.append(", ?");
        }*/
        for (int i = 0; i < fields.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append("?");
        }

        sb.append(")");

        return sb.toString();
    }

    public static String createQuerySELECT(Class theClass, String columna, String user) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ").append(theClass.getSimpleName());
        sb.append(" WHERE " + columna + " = ?");

        return sb.toString();
    }

    public static String createQuerySELECTall(Class theClass) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ").append(theClass.getSimpleName());

        return sb.toString();
    }


    public static String createQueryDELETE(String username) {
        StringBuffer sb = new StringBuffer();
        sb.append("DELETE FROM Jugador");
        sb.append(" WHERE USERNAME = ?");

        return sb.toString();
    }

    public static String createQueryUPDATEJugador(String columna, String user, String newvalue) {
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE Jugador ");
        sb.append("SET " + columna + " = ?");
        sb.append(" WHERE USERNAME = ?");

        return sb.toString();
    }
}
