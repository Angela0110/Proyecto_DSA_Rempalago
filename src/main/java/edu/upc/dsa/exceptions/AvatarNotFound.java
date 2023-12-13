package edu.upc.dsa.exceptions;

public class AvatarNotFound extends Throwable {
    private static final String errorMessage = "El jugador no tiene avatar";
    public AvatarNotFound() {
        super(errorMessage);
    }
}
