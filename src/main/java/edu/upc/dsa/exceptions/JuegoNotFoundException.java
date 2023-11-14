package edu.upc.dsa.exceptions;

public class JuegoNotFoundException extends Throwable {
    private static final String errorMessage = "El juego no existe";

    public JuegoNotFoundException() {
        super(errorMessage);
    }
}
