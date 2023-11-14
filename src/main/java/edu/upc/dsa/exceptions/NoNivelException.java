package edu.upc.dsa.exceptions;

public class NoNivelException extends Throwable {
    private static final String errorMessage = "El juego tiene que tener al menos un nivel";
    public NoNivelException() {
        super(errorMessage);
    }

}
