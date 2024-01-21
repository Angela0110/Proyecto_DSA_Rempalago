package edu.upc.dsa.exceptions;

public class MaximoException extends Throwable {
    private static final String errorMessage = "No se puede hacer la compra porque el jugador se pasaría del máximo (10)";
    public MaximoException() {
        super(errorMessage);
    }
}
