package edu.upc.dsa.exceptions;

public class CapitalInsuficienteException extends Throwable{

    private static final String errorMessage = "Estas tieso hermano";

    public CapitalInsuficienteException() {super(errorMessage);}
}
