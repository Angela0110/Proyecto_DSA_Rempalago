package edu.upc.dsa.exceptions;

public class UserNoEnPartidaException extends Throwable {
    private static final String errorMessage = "El usuario no tiene una partida en curso";

    public UserNoEnPartidaException() {
        super(errorMessage);
    }
}
