package edu.upc.dsa.exceptions;

public class WrongPasswordException extends Throwable {

    private static final String errorMessage = "La contraseña no es correcta";

    public WrongPasswordException() {
        super(errorMessage);
    }
}
